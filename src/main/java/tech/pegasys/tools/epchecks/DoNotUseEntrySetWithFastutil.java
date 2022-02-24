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
import static com.google.errorprone.matchers.Matchers.instanceMethod;

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
    name = "DoNotUseEntrySetWithFastutil",
    summary = "Do not use entrySet() with fastutil maps.",
    severity = WARNING)
public class DoNotUseEntrySetWithFastutil extends BugChecker
    implements MethodInvocationTreeMatcher {

  // All of the map classes inherit this class.
  private static final String FASTUTIL_FUNCTION = "it.unimi.dsi.fastutil.Function";
  private static final Matcher<ExpressionTree> DEPRECATED_ENTRYSET =
      instanceMethod().onDescendantOfAny(FASTUTIL_FUNCTION).named("entrySet");

  private static final Map<String, String> typeNames =
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
  public Description matchMethodInvocation(MethodInvocationTree tree, VisitorState state) {
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
        typeNames.getOrDefault(left, "Object").toLowerCase()
            + "2"
            + typeNames.getOrDefault(right, "Object")
            + "EntrySet";
    return newFuncName;
  }
}
