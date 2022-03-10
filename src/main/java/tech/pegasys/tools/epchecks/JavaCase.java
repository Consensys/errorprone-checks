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
import static com.google.errorprone.bugpatterns.BugChecker.ClassTreeMatcher;
import static com.google.errorprone.bugpatterns.BugChecker.MethodTreeMatcher;
import static com.google.errorprone.bugpatterns.BugChecker.VariableTreeMatcher;

import java.util.Set;
import java.util.regex.Pattern;
import javax.lang.model.element.Modifier;

import com.google.auto.service.AutoService;
import com.google.common.base.CaseFormat;
import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.fixes.SuggestedFix;
import com.google.errorprone.fixes.SuggestedFixes;
import com.google.errorprone.matchers.Description;
import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

@AutoService(BugChecker.class)
@BugPattern(
    name = "JavaCase",
    summary = "Use the appropriate case.",
    severity = SUGGESTION,
    linkType = BugPattern.LinkType.NONE)
public class JavaCase extends BugChecker
    implements MethodTreeMatcher, ClassTreeMatcher, VariableTreeMatcher {

  private static final Pattern PATTERN_HAS_LOWER = Pattern.compile("^.*[a-z].*$");
  private static final Pattern PATTERN_HAS_UPPER = Pattern.compile("^.*[A-Z].*$");
  private static final Pattern PATTERN_STARTS_WITH_LOWER = Pattern.compile("^[a-z].*$");
  private static final Pattern PATTERN_STARTS_WITH_UPPER = Pattern.compile("^[A-Z].*$");
  private static final Pattern PATTERN_UNDERSCORES = Pattern.compile("^_+$");

  @Override
  public Description matchVariable(VariableTree tree, VisitorState state) {
    String name = tree.getName().toString();

    // Constants should be all uppercase.
    // All variables are implicitly constant in interfaces.
    if (isConstant(tree) || isInterface(state)) {
      if (!isUpperUnderscore(name)) {
        return buildDescription(tree)
            .addFix(SuggestedFixes.renameVariable(tree, toUpperUnderscore(name), state))
            .build();
      }
      // Lambdas often use _ or __ for var names. This is fine.
    } else if (!isOneOrMoreUnderscores(name) && !isLowerCamel(name)) {
      return buildDescription(tree)
          .addFix(SuggestedFixes.renameVariable(tree, toLowerCamel(name), state))
          .build();
    }
    return Description.NO_MATCH;
  }

  @Override
  public Description matchMethod(MethodTree tree, VisitorState state) {
    String name = tree.getName().toString();

    // Class constructors have the <init> name.
    if (name.equals("<init>")) {
      return Description.NO_MATCH;
    }

    // Ignore test methods often have underscores mixed in.
    if (!isLowerCamel(name) && !isTestMethod(tree)) {
      return buildDescription(tree)
          .addFix(SuggestedFixes.renameMethod(tree, toLowerCamel(name), state))
          .build();
    }
    return Description.NO_MATCH;
  }

  @Override
  public Description matchClass(ClassTree tree, VisitorState state) {
    String name = tree.getSimpleName().toString();

    // Anonymous classes have no name.
    if (name.isEmpty()) {
      return Description.NO_MATCH;
    }

    if (!isAllUpper(name) && !isUpperCamel(name)) {
      String update = tree.toString().replace(name, toUpperCamel(name));
      update = update.split("\\r?\\n")[1];
      return buildDescription(tree)
          .addFix(SuggestedFix.builder().replace(tree, update).build())
          .build();
    }
    return Description.NO_MATCH;
  }

  private static boolean isConstant(VariableTree tree) {
    Set<Modifier> flags = tree.getModifiers().getFlags();
    return flags.contains(Modifier.STATIC) && flags.contains(Modifier.FINAL);
  }

  private static boolean isInterface(VisitorState state) {
    Tree node = state.getPath().getParentPath().getLeaf();
    return node.getKind() == Tree.Kind.INTERFACE;
  }

  private static boolean isOneOrMoreUnderscores(String name) {
    return PATTERN_UNDERSCORES.matcher(name).matches();
  }

  private static boolean isLowerCamel(String name) {
    return !name.contains("_") && PATTERN_STARTS_WITH_LOWER.matcher(name).matches();
  }

  private static boolean isUpperCamel(String name) {
    return !name.contains("_")
        && PATTERN_STARTS_WITH_UPPER.matcher(name).matches()
        && PATTERN_HAS_LOWER.matcher(name).matches();
  }

  private static boolean isUpperUnderscore(String name) {
    return !PATTERN_HAS_LOWER.matcher(name).matches();
  }

  private static boolean isTestMethod(MethodTree tree) {
    for (AnnotationTree t : tree.getModifiers().getAnnotations()) {
      if (t.getAnnotationType().toString().contains("Test")) {
        return true;
      }
    }
    return false;
  }

  private static boolean isAllUpper(String name) {
    return !name.contains("_") && !PATTERN_HAS_LOWER.matcher(name).matches();
  }

  private static String toLowerCamel(String name) {
    CaseFormat format = CaseFormat.LOWER_UNDERSCORE;
    if (isUpperCamel(name)) {
      format = CaseFormat.UPPER_CAMEL;
    } else if (isUpperUnderscore(name)) {
      format = CaseFormat.UPPER_UNDERSCORE;
    }
    return format.to(CaseFormat.LOWER_CAMEL, name);
  }

  private static String toUpperCamel(String name) {
    CaseFormat format = CaseFormat.LOWER_UNDERSCORE;
    if (isLowerCamel(name)) {
      format = CaseFormat.LOWER_CAMEL;
    } else if (isUpperUnderscore(name)) {
      format = CaseFormat.UPPER_UNDERSCORE;
    }
    return format.to(CaseFormat.UPPER_CAMEL, name);
  }

  private static String toUpperUnderscore(String name) {
    CaseFormat format = CaseFormat.LOWER_UNDERSCORE;
    if (isLowerCamel(name)) {
      format = CaseFormat.LOWER_CAMEL;
    } else if (isUpperCamel(name)) {
      format = CaseFormat.UPPER_CAMEL;
    }
    return format.to(CaseFormat.UPPER_UNDERSCORE, name);
  }
}
