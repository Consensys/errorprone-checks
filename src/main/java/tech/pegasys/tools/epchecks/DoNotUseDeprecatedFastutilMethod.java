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
import static com.google.errorprone.matchers.Matchers.anyOf;
import static com.google.errorprone.matchers.Matchers.instanceMethod;

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
    severity = WARNING,
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
          "it.unimi.dsi.fastutil.bools.BooleanIterable",
          "it.unimi.dsi.fastutil.bytes.ByteIterable",
          "it.unimi.dsi.fastutil.chars.CharIterable",
          "it.unimi.dsi.fastutil.doubles.DoubleIterable",
          "it.unimi.dsi.fastutil.floats.FloatIterable",
          "it.unimi.dsi.fastutil.ints.IntIterable",
          "it.unimi.dsi.fastutil.longs.LongIterable",
          "it.unimi.dsi.fastutil.shorts.ShortIterable");

  private static final List<String> FASTUTIL_COLLECTION =
      List.of(
          "it.unimi.dsi.fastutil.bools.BooleanCollection",
          "it.unimi.dsi.fastutil.bytes.ByteCollection",
          "it.unimi.dsi.fastutil.chars.CharCollection",
          "it.unimi.dsi.fastutil.doubles.DoubleCollection",
          "it.unimi.dsi.fastutil.floats.FloatCollection",
          "it.unimi.dsi.fastutil.ints.IntCollection",
          "it.unimi.dsi.fastutil.longs.LongCollection",
          "it.unimi.dsi.fastutil.shorts.ShortCollection");

  private static final List<String> FASTUTIL_LIST =
      List.of(
          "it.unimi.dsi.fastutil.bools.BooleanList",
          "it.unimi.dsi.fastutil.bytes.ByteList",
          "it.unimi.dsi.fastutil.chars.CharList",
          "it.unimi.dsi.fastutil.doubles.DoubleList",
          "it.unimi.dsi.fastutil.floats.FloatList",
          "it.unimi.dsi.fastutil.ints.IntList",
          "it.unimi.dsi.fastutil.longs.LongList",
          "it.unimi.dsi.fastutil.shorts.ShortList");

  private static final List<String> FASTUTIL_SET =
      List.of(
          "it.unimi.dsi.fastutil.bools.BooleanSet",
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
              .named("contains")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.lang.Object"))),
          instanceMethod()
              .onDescendantOfAny(FASTUTIL_ITERABLE)
              .named("indexOf")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.lang.Object"))),
          instanceMethod()
              .onDescendantOfAny(FASTUTIL_ITERABLE)
              .named("remove")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.lang.Object"))),
          instanceMethod()
              .onDescendantOfAny(FASTUTIL_ITERABLE)
              .named("lastIndexOf")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.lang.Object"))),
          instanceMethod()
              .onDescendantOfAny(FASTUTIL_ITERABLE)
              .named("get")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.lang.Object"))));

  private static final Matcher<ExpressionTree> DEPRECATED_ADD =
      anyOf(
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bools.BooleanIterable")
              .named("add")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.lang.Boolean"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bools.BooleanIterable")
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
              .onDescendantOfAny("it.unimi.dsi.fastutil.bools.BooleanIterable")
              .named("forEach")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.function.Consumer"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bytes.ByteIterable")
              .named("forEach")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.function.Consumer"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.chars.CharIterable")
              .named("forEach")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.function.Consumer"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.doubles.DoubleIterable")
              .named("forEach")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.function.Consumer"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.floats.FloatIterable")
              .named("forEach")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.function.Consumer"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.ints.IntIterable")
              .named("forEach")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.function.Consumer"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.longs.LongIterable")
              .named("forEach")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.function.Consumer"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.shorts.ShortIterable")
              .named("forEach")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.function.Consumer"))));

  private static final Matcher<ExpressionTree> DEPRECATED_PARALLEL_STREAM =
      anyOf(
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bools.BooleanCollection")
              .named("parallelStream"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bytes.ByteCollection")
              .named("parallelStream"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.chars.CharCollection")
              .named("parallelStream"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.doubles.DoubleCollection")
              .named("parallelStream"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.floats.FloatCollection")
              .named("parallelStream"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.ints.IntCollection")
              .named("parallelStream"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.longs.LongCollection")
              .named("parallelStream"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.shorts.ShortCollection")
              .named("parallelStream"));

  private static final Matcher<ExpressionTree> DEPRECATED_REMOVE =
      anyOf(
          instanceMethod().onDescendantOfAny(FASTUTIL_LIST).named("remove"),
          instanceMethod().onDescendantOfAny(FASTUTIL_SET).named("rem"));

  private static final Matcher<ExpressionTree> DEPRECATED_REMOVE_IF =
      anyOf(
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bools.BooleanCollection")
              .named("removeIf")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.function.Predicate"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bytes.ByteCollection")
              .named("removeIf")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.function.Predicate"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.chars.CharCollection")
              .named("removeIf")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.function.Predicate"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.doubles.DoubleCollection")
              .named("removeIf")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.function.Predicate"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.floats.FloatCollection")
              .named("removeIf")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.function.Predicate"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.ints.IntCollection")
              .named("removeIf")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.function.Predicate"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.longs.LongCollection")
              .named("removeIf")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.function.Predicate"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.shorts.ShortCollection")
              .named("removeIf")
              .withParametersOfType(
                  Suppliers.fromStrings(List.of("java.util.function.Predicate"))));

  private static final Matcher<ExpressionTree> DEPRECATED_REPLACE_ALL =
      anyOf(
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bools.BooleanIterable")
              .named("replaceAll")
              .withParametersOfType(
                  Suppliers.fromStrings(
                      List.of("java.util.function.UnaryOperator<java.lang.Boolean>"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bytes.ByteIterable")
              .named("replaceAll")
              .withParametersOfType(
                  Suppliers.fromStrings(
                      List.of("java.util.function.UnaryOperator<java.lang.Byte>"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.chars.CharIterable")
              .named("replaceAll")
              .withParametersOfType(
                  Suppliers.fromStrings(
                      List.of("java.util.function.UnaryOperator<java.lang.Character>"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.doubles.DoubleIterable")
              .named("replaceAll")
              .withParametersOfType(
                  Suppliers.fromStrings(
                      List.of("java.util.function.UnaryOperator<java.lang.Double>"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.floats.FloatIterable")
              .named("replaceAll")
              .withParametersOfType(
                  Suppliers.fromStrings(
                      List.of("java.util.function.UnaryOperator<java.lang.Float>"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.ints.IntIterable")
              .named("replaceAll")
              .withParametersOfType(
                  Suppliers.fromStrings(
                      List.of("java.util.function.UnaryOperator<java.lang.Integer>"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.longs.LongIterable")
              .named("replaceAll")
              .withParametersOfType(
                  Suppliers.fromStrings(
                      List.of("java.util.function.UnaryOperator<java.lang.Long>"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.shorts.ShortIterable")
              .named("replaceAll")
              .withParametersOfType(
                  Suppliers.fromStrings(
                      List.of("java.util.function.UnaryOperator<java.lang.Short>"))));

  private static final Matcher<ExpressionTree> DEPRECATED_SET =
      anyOf(
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bools.BooleanList")
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
              .onDescendantOfAny("it.unimi.dsi.fastutil.bools.BooleanIterable")
              .namedAnyOf("sort", "unstableSort")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.Comparator"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bytes.ByteIterable")
              .namedAnyOf("sort", "unstableSort")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.Comparator"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.chars.CharIterable")
              .namedAnyOf("sort", "unstableSort")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.Comparator"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.doubles.DoubleIterable")
              .namedAnyOf("sort", "unstableSort")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.Comparator"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.floats.FloatIterable")
              .namedAnyOf("sort", "unstableSort")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.Comparator"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.ints.IntIterable")
              .namedAnyOf("sort", "unstableSort")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.Comparator"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.longs.LongIterable")
              .namedAnyOf("sort", "unstableSort")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.Comparator"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.shorts.ShortIterable")
              .namedAnyOf("sort", "unstableSort")
              .withParametersOfType(Suppliers.fromStrings(List.of("java.util.Comparator"))));

  private static final Matcher<ExpressionTree> DEPRECATED_STREAM =
      instanceMethod().onDescendantOfAny(FASTUTIL_COLLECTION).named("stream");

  private static final Matcher<ExpressionTree> DEPRECATED_TO_ARRAY =
      anyOf(
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bools.BooleanCollection")
              .named("toBooleanArray")
              .withParametersOfType(Suppliers.fromStrings(List.of("boolean[]"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bytes.ByteCollection")
              .named("toByteArray")
              .withParametersOfType(Suppliers.fromStrings(List.of("byte[]"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.chars.CharCollection")
              .named("toCharArray")
              .withParametersOfType(Suppliers.fromStrings(List.of("char[]"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.doubles.DoubleCollection")
              .named("toDoubleArray")
              .withParametersOfType(Suppliers.fromStrings(List.of("double[]"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.floats.FloatCollection")
              .named("toFloatArray")
              .withParametersOfType(Suppliers.fromStrings(List.of("float[]"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.ints.IntCollection")
              .named("toIntArray")
              .withParametersOfType(Suppliers.fromStrings(List.of("int[]"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.longs.LongCollection")
              .named("toLongArray")
              .withParametersOfType(Suppliers.fromStrings(List.of("long[]"))),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.shorts.ShortCollection")
              .named("toShortArray")
              .withParametersOfType(Suppliers.fromStrings(List.of("short[]"))));

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

  @Override
  public Description matchMethodInvocation(MethodInvocationTree tree, VisitorState state) {
    if (DEPRECATED_METHOD.matches(tree, state)) {
      return describeMatch(tree);
    }
    if (DEPRECATED_ENTRYSET.matches(tree, state)) {
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
