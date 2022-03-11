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
import static com.google.errorprone.matchers.Description.NO_MATCH;
import static com.google.errorprone.matchers.Matchers.anyOf;
import static com.google.errorprone.matchers.Matchers.staticMethod;
import static com.google.errorprone.matchers.method.MethodMatchers.constructor;

import java.util.List;
import java.util.Map;

import com.google.auto.service.AutoService;
import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.bugpatterns.BugChecker.MethodInvocationTreeMatcher;
import com.google.errorprone.bugpatterns.BugChecker.NewClassTreeMatcher;
import com.google.errorprone.fixes.SuggestedFix;
import com.google.errorprone.fixes.SuggestedFixes;
import com.google.errorprone.matchers.Description;
import com.google.errorprone.matchers.Matcher;
import com.google.errorprone.util.ASTHelpers;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.code.TypeTag;

@AutoService(BugChecker.class)
@BugPattern(
    name = "UseFastutil",
    summary = "Use the fastutil equivalent for better performance.",
    severity = SUGGESTION,
    linkType = BugPattern.LinkType.NONE)
public class UseFastutil extends BugChecker
    implements NewClassTreeMatcher, MethodInvocationTreeMatcher {

  // According to [the docs](https://fastutil.di.unimi.it/docs/index.html):
  // fastutil specializes the most useful HashSet, HashMap, LinkedHashSet,
  // LinkedHashMap, TreeSet, TreeMap, IdentityHashMap, ArrayList and Stack
  // classes to versions that accept a specific kind of key or value (e.g.,
  // integers).
  //
  // Note: I cannot figure out how to instantiate fastutil Stack classes and I
  // do not see IdentifyHashMap in fastutil, so I have removed those from this
  // check.
  private static final Matcher<ExpressionTree> NEW_ITERABLE =
      anyOf(
          constructor().forClass("java.util.ArrayList"),
          constructor().forClass("java.util.HashSet"),
          constructor().forClass("java.util.LinkedHashSet"),
          constructor().forClass("java.util.TreeSet"));

  private static final Matcher<ExpressionTree> NEW_MAP =
      constructor().forClass("java.util.HashMap");

  private static final Matcher<MethodInvocationTree> ITERABLE_OF =
      anyOf(
          staticMethod().onClass("java.util.List").named("of"),
          staticMethod().onClass("java.util.Set").named("of"));

  private static final Map<String, String> JAVA_TO_FASTUTIL =
      Map.of(
          "ArrayList", "ArrayList",
          "HashMap", "OpenHashMap",
          "HashSet", "OpenHashSet",
          "LinkedHashSet", "LinkedOpenHashSet",
          "List", "List",
          "Map", "Map",
          "Set", "Set",
          "TreeSet", "RBTreeSet");

  private static final Map<String, String> TYPE_ABBR =
      Map.of(
          "java.lang.Boolean", "Boolean",
          "java.lang.Byte", "Byte",
          "java.lang.Character", "Char",
          "java.lang.Double", "Double",
          "java.lang.Float", "Float",
          "java.lang.Integer", "Int",
          "java.lang.Long", "Long",
          "java.lang.Reference", "Reference",
          "java.lang.Short", "Short");

  @Override
  public Description matchNewClass(NewClassTree tree, VisitorState state) {
    if (NEW_ITERABLE.matches(tree, state)) {
      return checkIterable(tree, state);
    } else if (NEW_MAP.matches(tree, state)) {
      return checkMap(tree, state);
    }
    return NO_MATCH;
  }

  @Override
  public Description matchMethodInvocation(MethodInvocationTree tree, VisitorState state) {
    if (ITERABLE_OF.matches(tree, state)) {
      return checkIterable(tree, state);
    }
    return NO_MATCH;
  }

  private Description checkIterable(ExpressionTree tree, VisitorState state) {
    List<Type> argumentTypes = ASTHelpers.getResultType(tree).getTypeArguments();
    if (argumentTypes.size() == 1) {
      if (isPrimitive(argumentTypes.get(0), state)) {
        return getDescriptionWithFix(tree, state);
      }
    }
    return NO_MATCH;
  }

  private Description checkMap(ExpressionTree tree, VisitorState state) {
    List<Type> argumentTypes = ASTHelpers.getResultType(tree).getTypeArguments();
    if (argumentTypes.size() == 2) {
      for (int i = 0; i < 2; i++) {
        if (isPrimitive(argumentTypes.get(i), state)) {
          return getDescriptionWithFix(tree, state);
        }
      }
    }
    return NO_MATCH;
  }

  private boolean isPrimitive(Type type, VisitorState state) {
    Type unboxedType = state.getTypes().unboxedType(type);
    return unboxedType != null && unboxedType.getTag() != TypeTag.NONE;
  }

  private Description getDescriptionWithFix(ExpressionTree tree, VisitorState state) {
    Tree node = state.getPath().getParentPath().getLeaf();
    String update = node.toString();

    // Update the left side of the assigment.
    if (node.getKind() == Tree.Kind.VARIABLE) {
      VariableTree varTreeNode = (VariableTree) node;
      String left = varTreeNode.getType().toString();
      update = update.replace(left, toFastutil(tree, state, noArrowBrackets(left)));
    }

    // Update the right side of the assignment.
    if (tree.getKind() == Tree.Kind.NEW_CLASS) {
      NewClassTree newClassNode = (NewClassTree) tree;
      String right = newClassNode.getIdentifier().toString();
      update = update.replace(right, toFastutil(tree, state, noArrowBrackets(right)));
    } else if (tree.getKind() == Tree.Kind.METHOD_INVOCATION) {
      MethodInvocationTree methodInvocationNode = (MethodInvocationTree) tree;
      String classAndFunc = methodInvocationNode.getMethodSelect().toString();
      String className = classAndFunc.substring(0, classAndFunc.indexOf("."));
      String fastutilClassName = getTypeSpecificClassName(tree, state, "") + classAndFunc;
      update = update.replace(classAndFunc, fastutilClassName);
    }

    return buildDescription(node)
        .addFix(SuggestedFix.builder().replace(node, update).build())
        .build();
  }

  private String noArrowBrackets(String classWithBrackets) {
    return classWithBrackets.substring(0, classWithBrackets.indexOf("<"));
  }

  private String toFastutil(Tree tree, VisitorState state, String className) {
    return getTypeSpecificClassName(tree, state, JAVA_TO_FASTUTIL.get(className));
  }

  private String getTypeSpecificClassName(Tree tree, VisitorState state, String className) {
    Type objectSet = ASTHelpers.getType(tree);
    if (objectSet.allparams().size() == 1) {
      Type left = objectSet.allparams().get(0);
      return TYPE_ABBR.getOrDefault(left.toString(), "Object") + className;
    } else if (objectSet.allparams().size() == 2) {
      Type left = objectSet.allparams().get(0);
      Type right = objectSet.allparams().get(1);
      String a2b =
          TYPE_ABBR.getOrDefault(left.toString(), "Object")
              + "2"
              + TYPE_ABBR.getOrDefault(right.toString(), "Object")
              + className;
      if (!className.isBlank()) {
        if (!TYPE_ABBR.containsKey(left.toString())) {
          a2b = a2b + "<" + SuggestedFixes.prettyType(left, state) + ">";
        } else if (!TYPE_ABBR.containsKey(right.toString())) {
          a2b = a2b + "<" + SuggestedFixes.prettyType(right, state) + ">";
        }
      }
      return a2b;
    }
    return "";
  }
}
