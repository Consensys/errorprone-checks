/*
 * Copyright 2022 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package tech.pegasys.tools.epchecks;

import static com.google.errorprone.BugPattern.SeverityLevel.WARNING;
import static com.google.errorprone.matchers.Matchers.staticMethod;

import java.util.List;
import javax.lang.model.type.TypeKind;

import com.google.auto.service.AutoService;
import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.bugpatterns.BugChecker.MethodInvocationTreeMatcher;
import com.google.errorprone.matchers.Description;
import com.google.errorprone.matchers.Matcher;
import com.google.errorprone.util.ASTHelpers;
import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TypeCastTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
import com.sun.tools.javac.code.Type;

@AutoService(BugChecker.class)
@BugPattern(
    name = "MathTargetType",
    summary = "Neither of the Math function arguments are the same as the target type.",
    severity = WARNING,
    linkType = BugPattern.LinkType.NONE)
public class MathTargetType extends BugChecker implements MethodInvocationTreeMatcher {

  // These are the math methods with multiple overloaded versions.
  // There could be type confusion with these functions.
  private static final Matcher<ExpressionTree> POTENTIALLY_CONFUSED_MATH_FUNC =
      staticMethod()
          .onClass("java.lang.Math")
          .namedAnyOf(
              "min", "max", "addExact", "subtractExact", "multiplyExact", "floorDiv", "floorMod");

  private static final List<Tree.Kind> COMPARISONS =
      List.of(
          Tree.Kind.EQUAL_TO,
          Tree.Kind.NOT_EQUAL_TO,
          Tree.Kind.GREATER_THAN,
          Tree.Kind.GREATER_THAN_EQUAL,
          Tree.Kind.LESS_THAN,
          Tree.Kind.LESS_THAN_EQUAL);

  @Override
  public Description matchMethodInvocation(MethodInvocationTree tree, VisitorState state) {
    if (POTENTIALLY_CONFUSED_MATH_FUNC.matches(tree, state)) {
      Tree parent = state.getPath().getParentPath().getLeaf();
      Type type = getTargetType(tree, parent, state, state.getPath().getParentPath());
      if (type != null && !anyMethodArgIs(tree, state, type.getKind())) {
        String typeName = type.getKind().toString().toLowerCase();
        return buildDescription(tree)
            .setMessage(
                "Neither of the function arguments are "
                    + typeName
                    + " types but the result is treated as such.")
            .build();
      }
    }
    return Description.NO_MATCH;
  }

  private boolean anyMethodArgIs(MethodInvocationTree tree, VisitorState state, TypeKind type) {
    for (ExpressionTree t : tree.getArguments()) {
      if (ASTHelpers.getType(t).getKind() == type) {
        return true;
      }
    }
    return false;
  }

  private Type getTargetType(Tree prev, Tree tree, VisitorState state, TreePath path) {
    if (tree instanceof AssignmentTree) {
      AssignmentTree assignmentTree = (AssignmentTree) tree;
      return ASTHelpers.getType(assignmentTree.getVariable());
    }

    if (tree instanceof BinaryTree) {
      // In the case of comparisons, the target type will be the
      // other side of the operation.
      BinaryTree binaryTree = (BinaryTree) tree;
      if (COMPARISONS.contains(tree.getKind())) {
        if (binaryTree.getLeftOperand().equals(prev)) {
          return ASTHelpers.getType(binaryTree.getRightOperand());
        }
        return ASTHelpers.getType(binaryTree.getLeftOperand());
      }

      // Need more information. Call again on parent.
      TreePath parentPath = path.getParentPath();
      Tree parent = parentPath.getLeaf();
      return getTargetType(tree, parent, state, parentPath);
    }

    if (tree instanceof MethodInvocationTree) {
      MethodInvocationTree methodInvocationTree = (MethodInvocationTree) tree;
      int index = methodInvocationTree.getArguments().indexOf(prev);
      return ASTHelpers.getSymbol(methodInvocationTree).params.get(index).type;
    }

    if (tree instanceof ReturnTree) {
      Type type = null;
      for (Tree parent : state.getPath()) {
        switch (parent.getKind()) {
          case METHOD:
            MethodTree methodTree = (MethodTree) parent;
            return ASTHelpers.getType(methodTree.getReturnType());
          case LAMBDA_EXPRESSION:
            Type parentType = ASTHelpers.getType(parent);
            return state.getTypes().findDescriptorType(parentType).getReturnType();
        }
      }
      return null;
    }

    if (tree instanceof TypeCastTree) {
      TypeCastTree typeCastTree = (TypeCastTree) tree;
      return ASTHelpers.getType(typeCastTree);
    }

    if (tree instanceof VariableTree) {
      VariableTree variableTree = (VariableTree) tree;
      return ASTHelpers.getType(variableTree);
    }

    return null;
  }
}
