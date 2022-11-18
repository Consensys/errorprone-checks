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
import static com.sun.source.tree.Tree.Kind.EQUAL_TO;
import static com.sun.source.tree.Tree.Kind.IDENTIFIER;
import static com.sun.source.tree.Tree.Kind.NOT_EQUAL_TO;
import static com.sun.source.tree.Tree.Kind.NULL_LITERAL;

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
import com.sun.tools.javac.code.Type;

/**
 * This check is very similar to the <a
 * href=http://errorprone.info/bugpattern/ReferenceEquality>ReferenceEquality</a> check in Error
 * Prone. But this one identifies <code>Object</code> comparisons without an explicitly declared
 * <code>equals()</code> method.
 */
@AutoService(BugChecker.class)
@BugPattern(
    name = "ReferenceComparison",
    summary = "Reference comparison should be value comparison.",
    severity = SUGGESTION,
    linkType = BugPattern.LinkType.NONE)
public class ReferenceComparison extends BugChecker implements BinaryTreeMatcher {
  private static final Matcher<BinaryTree> EQUAL_OR_NOT_EQUAL =
      Matchers.anyOf(Matchers.kindIs(EQUAL_TO), Matchers.kindIs(NOT_EQUAL_TO));

  @Override
  public Description matchBinary(BinaryTree tree, VisitorState state) {
    // We're looking for reference comparisons (== and !=).
    if (!EQUAL_OR_NOT_EQUAL.matches(tree, state)) {
      return NO_MATCH;
    }

    final Tree left = tree.getLeftOperand();
    final Tree right = tree.getRightOperand();
    final Type leftType = ASTHelpers.getType(left);
    final Type rightType = ASTHelpers.getType(right);

    if (leftType == null || rightType == null) {
      return NO_MATCH;
    }

    // When a boxed type is compared to a primitive, it'll be unboxed.
    if (leftType.isPrimitive() || rightType.isPrimitive()) {
      return NO_MATCH;
    }

    // Ignore null comparisons.
    final Matcher<Tree> isNull = Matchers.kindIs(NULL_LITERAL);
    if (isNull.matches(left, state) || isNull.matches(right, state)) {
      return NO_MATCH;
    }

    // Ignore comparisons with "this" identifier.
    if (isThis(left) || isThis(right)) {
      return NO_MATCH;
    }

    // Ignore reference comparisons with enums. Those are a special case.
    final Matcher<Tree> isEnum = Matchers.isSubtypeOf("java.lang.Enum");
    if (isEnum.matches(left, state) || isEnum.matches(right, state)) {
      return NO_MATCH;
    }

    // Ignore class comparisons, those are generally fine.
    final Matcher<Tree> isClass = Matchers.isSubtypeOf("java.lang.Class");
    if (isClass.matches(left, state) || isClass.matches(right, state)) {
      return NO_MATCH;
    }

    return describeMatch(tree);
  }

  private static boolean isThis(Tree tree) {
    if (tree.getKind().equals(IDENTIFIER)) {
      final IdentifierTree identifier = (IdentifierTree) tree;
      return identifier.getName().contentEquals("this");
    }
    return false;
  }
}
