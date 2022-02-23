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

import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.bugpatterns.BugChecker.MethodInvocationTreeMatcher;
import com.google.errorprone.matchers.Description;
import com.google.errorprone.matchers.Matcher;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;

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

  @Override
  public Description matchMethodInvocation(MethodInvocationTree tree, VisitorState state) {
    if (DEPRECATED_ENTRYSET.matches(tree, state)) {
      return buildDescription(tree)
          .setMessage("Use type-specific entrySet method instead.")
          .build();
    }
    return Description.NO_MATCH;
  }
}
