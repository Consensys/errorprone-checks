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

import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.bugpatterns.BugChecker.MethodInvocationTreeMatcher;
import com.google.errorprone.fixes.SuggestedFixes;
import com.google.errorprone.matchers.Description;
import com.google.errorprone.matchers.Matcher;
import com.google.errorprone.util.ASTHelpers;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.tools.javac.code.Type;

@BugPattern(
    name = "DoNotUseDeprecatedFastutilMethod",
    summary = "Use the type-specific fastutil method instead.",
    severity = WARNING)
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
              .withParameters("java.lang.Object"),
          instanceMethod()
              .onDescendantOfAny(FASTUTIL_ITERABLE)
              .named("indexOf")
              .withParameters("java.lang.Object"),
          instanceMethod()
              .onDescendantOfAny(FASTUTIL_ITERABLE)
              .named("remove")
              .withParameters("java.lang.Object"),
          instanceMethod()
              .onDescendantOfAny(FASTUTIL_ITERABLE)
              .named("lastIndexOf")
              .withParameters("java.lang.Object"),
          instanceMethod()
              .onDescendantOfAny(FASTUTIL_ITERABLE)
              .named("get")
              .withParameters("java.lang.Object"));

  private static final Matcher<ExpressionTree> DEPRECATED_ADD =
      anyOf(
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bools.BooleanIterable")
              .named("add")
              .withParameters("java.lang.Boolean"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bools.BooleanIterable")
              .named("add")
              .withParameters("int", "java.lang.Boolean"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bytes.ByteIterable")
              .named("add")
              .withParameters("java.lang.Byte"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bytes.ByteIterable")
              .named("add")
              .withParameters("int", "java.lang.Byte"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.chars.CharIterable")
              .named("add")
              .withParameters("java.lang.Character"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.chars.CharIterable")
              .named("add")
              .withParameters("int", "java.lang.Character"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.doubles.DoubleIterable")
              .named("add")
              .withParameters("java.lang.Double"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.doubles.DoubleIterable")
              .named("add")
              .withParameters("int", "java.lang.Double"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.floats.FloatIterable")
              .named("add")
              .withParameters("java.lang.Float"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.floats.FloatIterable")
              .named("add")
              .withParameters("int", "java.lang.Float"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.ints.IntIterable")
              .named("add")
              .withParameters("java.lang.Integer"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.ints.IntIterable")
              .named("add")
              .withParameters("int", "java.lang.Integer"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.longs.LongIterable")
              .named("add")
              .withParameters("java.lang.Long"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.longs.LongIterable")
              .named("add")
              .withParameters("int", "java.lang.Long"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.shorts.ShortIterable")
              .named("add")
              .withParameters("java.lang.Short"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.shorts.ShortIterable")
              .named("add")
              .withParameters("int", "java.lang.Short"));

  private static final Matcher<ExpressionTree> DEPRECATED_FOR_EACH =
      anyOf(
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bools.BooleanIterable")
              .named("forEach")
              .withParameters("java.util.function.Consumer<? super java.lang.Boolean>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bytes.ByteIterable")
              .named("forEach")
              .withParameters("java.util.function.Consumer<? super java.lang.Byte>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.chars.CharIterable")
              .named("forEach")
              .withParameters("java.util.function.Consumer<? super java.lang.Character>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.doubles.DoubleIterable")
              .named("forEach")
              .withParameters("java.util.function.Consumer<? super java.lang.Double>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.floats.FloatIterable")
              .named("forEach")
              .withParameters("java.util.function.Consumer<? super java.lang.Float>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.ints.IntIterable")
              .named("forEach")
              .withParameters("java.util.function.Consumer<? super java.lang.Integer>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.longs.LongIterable")
              .named("forEach")
              .withParameters("java.util.function.Consumer<? super java.lang.Long>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.shorts.ShortIterable")
              .named("forEach")
              .withParameters("java.util.function.Consumer<? super java.lang.Short>"));

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
              .withParameters("java.util.function.Predicate<? super java.lang.Boolean>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bytes.ByteCollection")
              .named("removeIf")
              .withParameters("java.util.function.Predicate<? super java.lang.Byte>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.chars.CharCollection")
              .named("removeIf")
              .withParameters("java.util.function.Predicate<? super java.lang.Character>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.doubles.DoubleCollection")
              .named("removeIf")
              .withParameters("java.util.function.Predicate<? super java.lang.Double>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.floats.FloatCollection")
              .named("removeIf")
              .withParameters("java.util.function.Predicate<? super java.lang.Float>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.ints.IntCollection")
              .named("removeIf")
              .withParameters("java.util.function.Predicate<? super java.lang.Integer>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.longs.LongCollection")
              .named("removeIf")
              .withParameters("java.util.function.Predicate<? super java.lang.Long>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.shorts.ShortCollection")
              .named("removeIf")
              .withParameters("java.util.function.Predicate<? super java.lang.Short>"));

  private static final Matcher<ExpressionTree> DEPRECATED_REPLACE_ALL =
      anyOf(
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bools.BooleanIterable")
              .named("replaceAll")
              .withParameters("java.util.function.UnaryOperator<java.lang.Boolean>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bytes.ByteIterable")
              .named("replaceAll")
              .withParameters("java.util.function.UnaryOperator<java.lang.Byte>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.chars.CharIterable")
              .named("replaceAll")
              .withParameters("java.util.function.UnaryOperator<java.lang.Character>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.doubles.DoubleIterable")
              .named("replaceAll")
              .withParameters("java.util.function.UnaryOperator<java.lang.Double>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.floats.FloatIterable")
              .named("replaceAll")
              .withParameters("java.util.function.UnaryOperator<java.lang.Float>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.ints.IntIterable")
              .named("replaceAll")
              .withParameters("java.util.function.UnaryOperator<java.lang.Integer>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.longs.LongIterable")
              .named("replaceAll")
              .withParameters("java.util.function.UnaryOperator<java.lang.Long>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.shorts.ShortIterable")
              .named("replaceAll")
              .withParameters("java.util.function.UnaryOperator<java.lang.Short>"));

  private static final Matcher<ExpressionTree> DEPRECATED_SET =
      anyOf(
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bools.BooleanList")
              .named("set")
              .withParameters("java.lang.Boolean"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bytes.ByteList")
              .named("set")
              .withParameters("int", "java.lang.Byte"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.chars.CharList")
              .named("set")
              .withParameters("int", "java.lang.Character"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.doubles.DoubleList")
              .named("set")
              .withParameters("int", "java.lang.Double"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.floats.FloatList")
              .named("set")
              .withParameters("int", "java.lang.Float"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.ints.IntList")
              .named("set")
              .withParameters("int", "java.lang.Integer"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.longs.LongList")
              .named("set")
              .withParameters("int", "java.lang.Long"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.shorts.ShortList")
              .named("set")
              .withParameters("int", "java.lang.Short"));

  private static final Matcher<ExpressionTree> DEPRECATED_SORT =
      anyOf(
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bools.BooleanIterable")
              .namedAnyOf("sort", "unstableSort")
              .withParameters("java.util.Comparator<? super java.lang.Boolean>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bytes.ByteIterable")
              .namedAnyOf("sort", "unstableSort")
              .withParameters("java.util.Comparator<? super java.lang.Byte>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.chars.CharIterable")
              .namedAnyOf("sort", "unstableSort")
              .withParameters("java.util.Comparator<? super java.lang.Character>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.doubles.DoubleIterable")
              .namedAnyOf("sort", "unstableSort")
              .withParameters("java.util.Comparator<? super java.lang.Double>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.floats.FloatIterable")
              .namedAnyOf("sort", "unstableSort")
              .withParameters("java.util.Comparator<? super java.lang.Float>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.ints.IntIterable")
              .namedAnyOf("sort", "unstableSort")
              .withParameters("java.util.Comparator<? super java.lang.Integer>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.longs.LongIterable")
              .namedAnyOf("sort", "unstableSort")
              .withParameters("java.util.Comparator<? super java.lang.Long>"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.shorts.ShortIterable")
              .namedAnyOf("sort", "unstableSort")
              .withParameters("java.util.Comparator<? super java.lang.Short>"));

  private static final Matcher<ExpressionTree> DEPRECATED_STREAM =
      instanceMethod().onDescendantOfAny(FASTUTIL_COLLECTION).named("stream");

  private static final Matcher<ExpressionTree> DEPRECATED_TO_ARRAY =
      anyOf(
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bools.BooleanCollection")
              .named("toBooleanArray")
              .withParameters("boolean[]"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.bytes.ByteCollection")
              .named("toByteArray")
              .withParameters("byte[]"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.chars.CharCollection")
              .named("toCharArray")
              .withParameters("char[]"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.doubles.DoubleCollection")
              .named("toDoubleArray")
              .withParameters("double[]"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.floats.FloatCollection")
              .named("toFloatArray")
              .withParameters("float[]"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.ints.IntCollection")
              .named("toIntArray")
              .withParameters("int[]"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.longs.LongCollection")
              .named("toLongArray")
              .withParameters("long[]"),
          instanceMethod()
              .onDescendantOfAny("it.unimi.dsi.fastutil.shorts.ShortCollection")
              .named("toShortArray")
              .withParameters("short[]"));

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
      return buildDescription(tree)
          .setMessage("Use type-specific method instead.")
          .setLinkUrl("https://github.com/ConsenSys/errorprone-checks")
          .build();
    }
    if (DEPRECATED_ENTRYSET.matches(tree, state)) {
      return buildDescription(tree)
          .setMessage("Use type-specific entrySet method instead.")
          .addFix(
              SuggestedFixes.renameMethodInvocation(
                  tree, getTypeSpecificEntrySetFuncName(tree, state), state))
          .setLinkUrl("https://github.com/ConsenSys/errorprone-checks")
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
