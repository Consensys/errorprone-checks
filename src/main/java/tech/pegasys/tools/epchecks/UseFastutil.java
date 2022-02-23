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

import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.bugpatterns.BugChecker.MethodInvocationTreeMatcher;
import com.google.errorprone.bugpatterns.BugChecker.NewClassTreeMatcher;
import com.google.errorprone.matchers.Description;
import com.google.errorprone.matchers.Matcher;
import com.google.errorprone.util.ASTHelpers;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.NewClassTree;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.code.TypeTag;

@BugPattern(
    name = "UseFastutil",
    summary = "Use the fastutil equivalent for better performance.",
    severity = SUGGESTION)
public class UseFastutil extends BugChecker
    implements NewClassTreeMatcher, MethodInvocationTreeMatcher {

  // According to the docs: fastutil specializes the most useful HashSet,
  // HashMap, LinkedHashSet, LinkedHashMap, TreeSet, TreeMap, IdentityHashMap,
  // ArrayList and Stack classes to versions that accept a specific kind of key
  // or value (e.g., integers).
  private static final Matcher<ExpressionTree> NEW_LIST =
      anyOf(
          constructor().forClass("java.util.ArrayList"),
          constructor().forClass("java.util.HashSet"),
          constructor().forClass("java.util.LinkedHashSet"),
          constructor().forClass("java.util.LinkedList"),
          constructor().forClass("java.util.Stack"),
          constructor().forClass("java.util.TreeSet"));

  private static final Matcher<ExpressionTree> NEW_MAP =
      anyOf(
          constructor().forClass("java.util.HashMap"),
          constructor().forClass("java.util.IdentityHashMap"),
          constructor().forClass("java.util.LinkedHashMap"),
          constructor().forClass("java.util.TreeMap"));

  private static final Matcher<MethodInvocationTree> LIST_OF =
      anyOf(
          staticMethod().onClass("java.util.List").named("of"),
          staticMethod().onClass("java.util.Set").named("of"));

  private static final Matcher<MethodInvocationTree> MAP_OF =
      anyOf(
          staticMethod().onClass("java.util.Map").named("of"),
          staticMethod().onClass("java.util.Map").named("ofEntries"));

  @Override
  public Description matchNewClass(NewClassTree tree, VisitorState state) {
    if (NEW_LIST.matches(tree, state)) {
      return checkList(tree, state);
    } else if (NEW_MAP.matches(tree, state)) {
      return checkMap(tree, state);
    }
    return NO_MATCH;
  }

  @Override
  public Description matchMethodInvocation(MethodInvocationTree tree, VisitorState state) {
    if (LIST_OF.matches(tree, state)) {
      return checkList(tree, state);
    } else if (MAP_OF.matches(tree, state)) {
      return checkMap(tree, state);
    }
    return NO_MATCH;
  }

  private Description checkList(ExpressionTree tree, VisitorState state) {
    List<Type> argumentTypes = ASTHelpers.getResultType(tree).getTypeArguments();
    if (argumentTypes.size() == 1) {
      if (isPrimitive(argumentTypes.get(0), state)) {
        return describeMatch(tree);
      }
    }
    return Description.NO_MATCH;
  }

  private Description checkMap(ExpressionTree tree, VisitorState state) {
    List<Type> argumentTypes = ASTHelpers.getResultType(tree).getTypeArguments();
    if (argumentTypes.size() == 2) {
      for (int i = 0; i < 2; i++) {
        if (isPrimitive(argumentTypes.get(i), state)) {
          return describeMatch(tree);
        }
      }
    }
    return Description.NO_MATCH;
  }

  private boolean isPrimitive(Type type, VisitorState state) {
    Type unboxedType = state.getTypes().unboxedType(type);
    return unboxedType != null && unboxedType.getTag() != TypeTag.NONE;
  }
}
