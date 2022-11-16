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

import static com.google.errorprone.BugPattern.SeverityLevel.SUGGESTION;

import com.google.auto.service.AutoService;
import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.bugpatterns.BugChecker.BinaryTreeMatcher;
import com.google.errorprone.matchers.Description;
import com.google.errorprone.matchers.Matcher;
import com.google.errorprone.matchers.Matchers;
import com.google.errorprone.util.ASTHelpers;
import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;

@AutoService(BugChecker.class)
@BugPattern(
    name = "ReferenceComparison",
    summary = "Reference comparison should be value comparison",
    severity = SUGGESTION,
    linkType = BugPattern.LinkType.NONE)
public class ReferenceComparison extends BugChecker implements BinaryTreeMatcher {
  private static final Matcher<BinaryTree> EQUAL_OR_NOT_EQUAL =
      Matchers.anyOf(Matchers.kindIs(Tree.Kind.EQUAL_TO), Matchers.kindIs(Tree.Kind.NOT_EQUAL_TO));
  private static final Matcher<Tree> IS_ENUM = Matchers.isSubtypeOf("java.lang.Enum");

  @Override
  public Description matchBinary(BinaryTree tree, VisitorState state) {
    if (!EQUAL_OR_NOT_EQUAL.matches(tree, state)) {
      return Description.NO_MATCH;
    }

    Tree left = tree.getLeftOperand();
    Tree right = tree.getRightOperand();
    Type leftType = ASTHelpers.getType(left);
    Type rightType = ASTHelpers.getType(right);

    if (leftType == null || rightType == null) {
      return Description.NO_MATCH;
    }

    // When a boxed type is compared to a primitive, it'll be unboxed.
    if (leftType.isPrimitive() || rightType.isPrimitive()) {
      return Description.NO_MATCH;
    }

    // Ignore null comparisons.
    Matcher<Tree> isNull = Matchers.kindIs(Tree.Kind.NULL_LITERAL);
    if (isNull.matches(left, state) || isNull.matches(right, state)) {
      return Description.NO_MATCH;
    }

    // Ignore comparisons with "this" variables.
    if (isThis(left) || isThis(right)) {
      return Description.NO_MATCH;
    }

    // Ignore reference comparisons with enums. Those are a special case.
    if (isEnum(left, state) || isEnum(right, state)) {
      return Description.NO_MATCH;
    }

    // Ignore class comparisons, those are generally fine.
    Matcher<Tree> isClass = Matchers.isSubtypeOf("java.lang.Class");
    if (isClass.matches(left, state) || isClass.matches(right, state)) {
      return Description.NO_MATCH;
    }

    return describeMatch(tree);
  }

  private static boolean isEnum(Tree tree, VisitorState state) {
    Symbol symbol = ASTHelpers.getSymbol(tree);
    if (symbol != null) {
      if (symbol.isEnum()) {
        return true;
      }
    }
    return IS_ENUM.matches(tree, state);
  }

  private static boolean isThis(Tree tree) {
    if (tree.getKind().equals(Tree.Kind.IDENTIFIER)) {
      IdentifierTree identifier = (IdentifierTree) tree;
      return identifier.getName().contentEquals("this");
    }
    return false;
  }
}
