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
import static com.google.errorprone.matchers.Matchers.anyOf;
import static com.google.errorprone.matchers.Matchers.instanceMethod;
import static com.google.errorprone.matchers.Matchers.not;

import java.util.List;
import java.util.Map;

import com.google.auto.service.AutoService;
import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.bugpatterns.BugChecker.MethodInvocationTreeMatcher;
import com.google.errorprone.fixes.SuggestedFixes;
import com.google.errorprone.matchers.Description;
import com.google.errorprone.matchers.Matcher;
import com.google.errorprone.suppliers.Suppliers;
import com.google.errorprone.util.ASTHelpers;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.tools.javac.code.Type;

@AutoService(BugChecker.class)
@BugPattern(
    name = "DoNotUseDeprecatedFastutilMethod",
    summary = "Use type-specific fastutil method instead.",
    severity = SUGGESTION,
    linkType = BugPattern.LinkType.NONE)
public class DoNotUseDeprecatedFastutilMethod extends BugChecker
    implements MethodInvocationTreeMatcher {

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

  private static final List<String> FASTUTIL_ITERABLE =
      List.of(
          "it.unimi.dsi.fastutil.booleans.BooleanIterable",
          "it.unimi.dsi.fastutil.bytes.ByteIterable",
          "it.unimi.dsi.fastutil.chars.CharIterable",
          "it.unimi.dsi.fastutil.doubles.DoubleIterable",
          "it.unimi.dsi.fastutil.floats.FloatIterable",
          "it.unimi.dsi.fastutil.ints.IntIterable",
          "it.unimi.dsi.fastutil.longs.LongIterable",
          "it.unimi.dsi.fastutil.shorts.ShortIterable");

  private static final List<String> FASTUTIL_COLLECTION =
      List.of(
          "it.unimi.dsi.fastutil.booleans.BooleanCollection",
          "it.unimi.dsi.fastutil.bytes.ByteCollection",
          "it.unimi.dsi.fastutil.chars.CharCollection",
          "it.unimi.dsi.fastutil.doubles.DoubleCollection",
          "it.unimi.dsi.fastutil.floats.FloatCollection",
          "it.unimi.dsi.fastutil.ints.IntCollection",
          "it.unimi.dsi.fastutil.longs.LongCollection",
          "it.unimi.dsi.fastutil.shorts.ShortCollection");

  private static final List<String> FASTUTIL_LIST =
      List.of(
          "it.unimi.dsi.fastutil.booleans.BooleanList",
          "it.unimi.dsi.fastutil.bytes.ByteList",
          "it.unimi.dsi.fastutil.chars.CharList",
          "it.unimi.dsi.fastutil.doubles.DoubleList",
          "it.unimi.dsi.fastutil.floats.FloatList",
          "it.unimi.dsi.fastutil.ints.IntList",
          "it.unimi.dsi.fastutil.longs.LongList",
          "it.unimi.dsi.fastutil.shorts.ShortList");

  private static final List<String> FASTUTIL_SET =
      List.of(
          "it.unimi.dsi.fastutil.booleans.BooleanSet",
          "it.unimi.dsi.fastutil.bytes.ByteSet",
          "it.unimi.dsi.fastutil.chars.CharSet",
          "it.unimi.dsi.fastutil.doubles.DoubleSet",
          "it.unimi.dsi.fastutil.floats.FloatSet",
          "it.unimi.dsi.fastutil.ints.IntSet",
          "it.unimi.dsi.fastutil.longs.LongSet",
          "it.unimi.dsi.fastutil.shorts.ShortSet");

  private static final Matcher<ExpressionTree> DEPRECATED_GENERIC =
      anyOf(
          instanceMethod()
              .onDescendantOfAny(FASTUTIL_ITERABLE)
              .namedAnyOf("contains", "indexOf", "remove", "lastIndexOf")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.lang.Object"))),
          instanceMethod().onDescendantOfAny(FASTUTIL_ITERABLE).named("get"));

  private static final Matcher<ExpressionTree> DEPRECATED_ADD =
      anyOf(
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.booleans.BooleanIterable")
              .named("add")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.lang.Boolean"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.booleans.BooleanIterable")
              .named("add")
              .withParametersOfType(Suppliers.fromStrings(List.of("int", "java.lang.Boolean"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bytes.ByteIterable")
              .named("add")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.lang.Byte"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bytes.ByteIterable")
              .named("add")
              .withParametersOfType(Suppliers.fromStrings(List.of("int", "java.lang.Byte"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.chars.CharIterable")
              .named("add")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.lang.Character"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.chars.CharIterable")
              .named("add")
              .withParametersOfType(Suppliers.fromStrings(List.of("int", "java.lang.Character"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.doubles.DoubleIterable")
              .named("add")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.lang.Double"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.doubles.DoubleIterable")
              .named("add")
              .withParametersOfType(Suppliers.fromStrings(List.of("int", "java.lang.Double"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.floats.FloatIterable")
              .named("add")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.lang.Float"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.floats.FloatIterable")
              .named("add")
              .withParametersOfType(Suppliers.fromStrings(List.of("int", "java.lang.Float"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.ints.IntIterable")
              .named("add")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.lang.Integer"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.ints.IntIterable")
              .named("add")
              .withParametersOfType(Suppliers.fromStrings(List.of("int", "java.lang.Integer"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.longs.LongIterable")
              .named("add")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.lang.Long"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.longs.LongIterable")
              .named("add")
              .withParametersOfType(Suppliers.fromStrings(List.of("int", "java.lang.Long"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.shorts.ShortIterable")
              .named("add")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.lang.Short"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.shorts.ShortIterable")
              .named("add")
              .withParametersOfType(Suppliers.fromStrings(List.of("int", "java.lang.Short"))));

  private static final Matcher<ExpressionTree> DEPRECATED_FOR_EACH =
      anyOf(
          instanceMethod()
              .onDescendantOfAny(FASTUTIL_ITERABLE)
              .named("forEach")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.function.Consumer"))));

  private static final Matcher<ExpressionTree> DEPRECATED_PARALLEL_STREAM =
      anyOf(instanceMethod().onDescendantOfAny(FASTUTIL_COLLECTION).named("parallelStream"));

  private static final Matcher<ExpressionTree> DEPRECATED_REMOVE =
      anyOf(
          instanceMethod().onDescendantOfAny(FASTUTIL_LIST).named("remove"),
          instanceMethod().onDescendantOfAny(FASTUTIL_SET).named("rem"));

  private static final Matcher<ExpressionTree> DEPRECATED_REMOVE_IF =
      anyOf(
          instanceMethod()
              .onDescendantOfAny(FASTUTIL_COLLECTION)
              .named("removeIf")
              .withParametersOfType(
                  Suppliers.fromStrings(List.of("java.util.function.Predicate"))));

  private static final Matcher<ExpressionTree> DEPRECATED_REPLACE_ALL =
      anyOf(
          instanceMethod()
              .onDescendantOfAny(FASTUTIL_ITERABLE)
              .named("replaceAll")
              .withParametersOfType(
                  Suppliers.fromStrings(List.of("java.util.function.UnaryOperator"))));

  private static final Matcher<ExpressionTree> DEPRECATED_SET =
      anyOf(
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.booleans.BooleanList")
              .named("set")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.lang.Boolean"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bytes.ByteList")
              .named("set")
              .withParametersOfType(Suppliers.fromStrings(List.of("int", "java.lang.Byte"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.chars.CharList")
              .named("set")
              .withParametersOfType(Suppliers.fromStrings(List.of("int", "java.lang.Character"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.doubles.DoubleList")
              .named("set")
              .withParametersOfType(Suppliers.fromStrings(List.of("int", "java.lang.Double"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.floats.FloatList")
              .named("set")
              .withParametersOfType(Suppliers.fromStrings(List.of("int", "java.lang.Float"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.ints.IntList")
              .named("set")
              .withParametersOfType(Suppliers.fromStrings(List.of("int", "java.lang.Integer"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.longs.LongList")
              .named("set")
              .withParametersOfType(Suppliers.fromStrings(List.of("int", "java.lang.Long"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.shorts.ShortList")
              .named("set")
              .withParametersOfType(Suppliers.fromStrings(List.of("int", "java.lang.Short"))));

  private static final Matcher<ExpressionTree> DEPRECATED_SORT =
      anyOf(
          instanceMethod()
              .onDescendantOfAny(FASTUTIL_ITERABLE)
              .namedAnyOf("sort", "unstableSort")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.Comparator"))));

  private static final Matcher<ExpressionTree> DEPRECATED_STREAM =
      instanceMethod().onDescendantOfAny(FASTUTIL_COLLECTION).named("stream");

  private static final Matcher<ExpressionTree> DEPRECATED_TO_ARRAY =
      anyOf(
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.booleans.BooleanCollection")
              .named("toBooleanArray")
              .withParametersOfType(
                  List.of(Suppliers.arrayOf(Suppliers.typeFromString("boolean")))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bytes.ByteCollection")
              .named("toByteArray")
              .withParametersOfType(List.of(Suppliers.arrayOf(Suppliers.typeFromString("byte")))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.chars.CharCollection")
              .named("toCharArray")
              .withParametersOfType(List.of(Suppliers.arrayOf(Suppliers.typeFromString("char")))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.doubles.DoubleCollection")
              .named("toDoubleArray")
              .withParametersOfType(List.of(Suppliers.arrayOf(Suppliers.typeFromString("double")))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.floats.FloatCollection")
              .named("toFloatArray")
              .withParametersOfType(List.of(Suppliers.arrayOf(Suppliers.typeFromString("float")))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.ints.IntCollection")
              .named("toIntArray")
              .withParametersOfType(List.of(Suppliers.arrayOf(Suppliers.typeFromString("int")))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.longs.LongCollection")
              .named("toLongArray")
              .withParametersOfType(List.of(Suppliers.arrayOf(Suppliers.typeFromString("long")))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.shorts.ShortCollection")
              .named("toShortArray")
              .withParametersOfType(List.of(Suppliers.arrayOf(Suppliers.typeFromString("short")))));

  private static final Matcher<ExpressionTree> DEPRECATED_METHOD =
      anyOf(
          DEPRECATED_ADD,
          DEPRECATED_FOR_EACH,
          DEPRECATED_GENERIC,
          DEPRECATED_PARALLEL_STREAM,
          DEPRECATED_REMOVE,
          DEPRECATED_REMOVE_IF,
          DEPRECATED_REPLACE_ALL,
          DEPRECATED_SET,
          DEPRECATED_SORT,
          DEPRECATED_STREAM,
          DEPRECATED_TO_ARRAY);

  // All of the map classes inherit this class.
  private static final String FASTUTIL_FUNCTION = "it.unimi.dsi.fastutil.Function";
  private static final Matcher<ExpressionTree> DEPRECATED_ENTRYSET =
      instanceMethod().onDescendantOfAny(FASTUTIL_FUNCTION).named("entrySet");

  // Object2ObjectMap.entrySet() is not deprecated.
  private static final Matcher<ExpressionTree> NOT_OBJECT_2_OBJECT_MAP =
      not(
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.objects.Object2ObjectFunction"));

  @Override
  public Description matchMethodInvocation(MethodInvocationTree tree, VisitorState state) {
    if (DEPRECATED_METHOD.matches(tree, state)) {
      return describeMatch(tree);
    }

    // We want to match all fastutil maps except for Object2Object maps.
    if (DEPRECATED_ENTRYSET.matches(tree, state) && NOT_OBJECT_2_OBJECT_MAP.matches(tree, state)) {
      return buildDescription(tree)
          .setMessage("Use type-specific fastutil entrySet method instead.")
          .addFix(
              SuggestedFixes.renameMethodInvocation(
                  tree, getTypeSpecificEntrySetFuncName(tree, state), state))
          .build();
    }
    return Description.NO_MATCH;
  }

  private static String getTypeSpecificEntrySetFuncName(
      MethodInvocationTree tree, VisitorState state) {
    Type objectSet = ASTHelpers.getType(tree);
    Type mapEntry = objectSet.allparams().get(0);
    String left = mapEntry.allparams().get(0).toString();
    String right = mapEntry.allparams().get(1).toString();
    String newFuncName =
        TYPE_ABBR.getOrDefault(left, "Object").toLowerCase()
            + "2"
            + TYPE_ABBR.getOrDefault(right, "Object")
            + "EntrySet";
    return newFuncName;
  }
}
