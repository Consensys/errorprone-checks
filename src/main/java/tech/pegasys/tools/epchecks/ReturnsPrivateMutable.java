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
import static com.google.errorprone.matchers.Matchers.allOf;
import static com.google.errorprone.matchers.Matchers.anyOf;
import static com.google.errorprone.matchers.Matchers.hasModifier;
import static com.google.errorprone.matchers.Matchers.isInstanceField;
import static com.google.errorprone.matchers.Matchers.methodHasVisibility;
import static com.google.errorprone.matchers.Matchers.methodReturns;
import static com.sun.source.tree.Tree.Kind.METHOD;

import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;

import com.google.auto.service.AutoService;
import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.matchers.Description;
import com.google.errorprone.matchers.Matcher;
import com.google.errorprone.matchers.Matchers;
import com.google.errorprone.matchers.MethodVisibility;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.Tree;

@AutoService(BugChecker.class)
@BugPattern(
    name = "ReturnsPrivateMutable",
    summary = "Public method returns private field that can be modified.",
    severity = SUGGESTION,
    linkType = BugPattern.LinkType.NONE)
public class ReturnsPrivateMutable extends BugChecker implements BugChecker.ReturnTreeMatcher {

  // List of mutable types came from the WellKnownMutability bug pattern in error-prone. Removed
  // java.lang.Object because Matchers::isSameType does not match the exact type; I think it
  // checks if it is also a subtype.
  public static final List<String> MUTABLE_TYPES =
      List.of(
          "com.google.common.collect.BiMap",
          "com.google.common.collect.ListMultimap",
          "com.google.common.collect.Multimap",
          "com.google.common.collect.Multiset",
          "com.google.common.collect.RangeMap",
          "com.google.common.collect.RangeSet",
          "com.google.common.collect.SetMultimap",
          "com.google.common.collect.SortedMultiset",
          "com.google.common.collect.Table",
          "com.google.common.util.concurrent.AtomicDouble",
          "com.google.protobuf.util.FieldMaskUtil.MergeOptions",
          "java.lang.Iterable",
          "java.text.DateFormat",
          "java.util.ArrayList",
          "java.util.BitSet",
          "java.util.Calendar",
          "java.util.Collection",
          "java.util.EnumMap",
          "java.util.EnumSet",
          "java.util.HashMap",
          "java.util.HashSet",
          "java.util.List",
          "java.util.Map",
          "java.util.NavigableMap",
          "java.util.NavigableSet",
          "java.util.Random",
          "java.util.Set",
          "java.util.TreeMap",
          "java.util.TreeSet",
          "java.util.Vector",
          "java.util.concurrent.atomic.AtomicBoolean",
          "java.util.concurrent.atomic.AtomicLong",
          "java.util.concurrent.atomic.AtomicReference",
          "java.util.logging.Logger");

  private static final Matcher<Tree> MUTABLE_TYPE =
      anyOf(MUTABLE_TYPES.stream().map(Matchers::isSameType).collect(Collectors.toSet()));

  private static final Matcher<ExpressionTree> PRIVATE_MUTABLE_INSTANCE_FIELD =
      allOf(hasModifier(Modifier.PRIVATE), MUTABLE_TYPE, isInstanceField());

  private static final Matcher<MethodTree> PUBLIC_METHOD_RETURNS_MUTABLE =
      allOf(methodHasVisibility(MethodVisibility.Visibility.PUBLIC), methodReturns(MUTABLE_TYPE));

  @Override
  public Description matchReturn(ReturnTree tree, VisitorState state) {
    // Filter out public/immutable fields.
    ExpressionTree exp = tree.getExpression();
    if (!PRIVATE_MUTABLE_INSTANCE_FIELD.matches(exp, state)) {
      return NO_MATCH;
    }

    // Filter out non-public methods.
    Tree maybeMethod = state.getPath().getParentPath().getParentPath().getLeaf();
    if (maybeMethod.getKind() != METHOD) {
      return NO_MATCH;
    }
    MethodTree method = (MethodTree) maybeMethod;
    if (!PUBLIC_METHOD_RETURNS_MUTABLE.matches(method, state)) {
      return NO_MATCH;
    }

    return buildDescription(tree)
        .setMessage(
            String.format(
                "Public method (%s) returns mutable (%s) private field.",
                method.getName(), method.getReturnType()))
        .build();
  }
}
