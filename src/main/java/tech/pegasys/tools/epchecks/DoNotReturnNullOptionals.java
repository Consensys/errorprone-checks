/*
 * Copyright 2021 ConsenSys AG.
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

import com.google.auto.service.AutoService;
import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.bugpatterns.BugChecker.ReturnTreeMatcher;
import com.google.errorprone.dataflow.nullnesspropagation.Nullness;
import com.google.errorprone.dataflow.nullnesspropagation.TrustingNullnessAnalysis;
import com.google.errorprone.matchers.Description;
import com.google.errorprone.util.ASTHelpers;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreePath;
import com.sun.tools.javac.code.Type;

@AutoService(BugChecker.class)
@BugPattern(
    name = "DoNotReturnNullOptionals",
    summary = "Do not return null optionals.",
    severity = SUGGESTION,
    linkType = BugPattern.LinkType.NONE)
public class DoNotReturnNullOptionals extends BugChecker implements ReturnTreeMatcher {

  @Override
  public Description matchReturn(final ReturnTree tree, final VisitorState state) {
    final Type returnType = getReturnType(state);
    if (returnType == null || !isOptional(returnType)) {
      return NO_MATCH;
    }

    /*
     * This block of code checks if the return value is null/nullable in some data flow. We use the
     * "trusting" nullness analysis here because the other one (NullnessAnalysis) will incorrectly
     * report some values as nullable, such as Optional.of(2L).
     *
     * According to the documentation for TrustingNullAnalysis:
     *
     * This variant "trusts" Nullable annotations, similar to how a modular nullness checker like
     * the checkerframework's would, meaning method parameters, fields, and method returns are
     * assumed Nullness.NULLABLE only if annotated so.
     */
    final TrustingNullnessAnalysis analysis = TrustingNullnessAnalysis.instance(state.context);
    final TreePath exprPath = new TreePath(state.getPath(), tree.getExpression());
    final Nullness nullness = analysis.getNullness(exprPath, state.context);
    if (nullness.equals(Nullness.NONNULL)) {
      return NO_MATCH;
    }

    return describeMatch(tree);
  }

  // This logic comes from the IntLongMath check:
  // https://github.com/google/error-prone/blob/5391186274d64031b5536a3e95fc1750711fb4b2/core/src/main/java/com/google/errorprone/bugpatterns/IntLongMath.java#L50-L72
  private static Type getReturnType(final VisitorState state) {
    Type type = null;
    outer:
    for (Tree parent : state.getPath()) {
      switch (parent.getKind()) {
        case METHOD:
          type = ASTHelpers.getType(((MethodTree) parent).getReturnType());
          break outer;
        case LAMBDA_EXPRESSION:
          type = state.getTypes().findDescriptorType(ASTHelpers.getType(parent)).getReturnType();
          break outer;
        default:
          // Fall out.
      }
    }
    return type;
  }

  private static boolean isOptional(final Type type) {
    return type.toString().startsWith("java.util.Optional<");
  }
}
