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
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.VariableTree;

@AutoService(BugChecker.class)
@BugPattern(
    name = "JavaCase",
    summary = "Use the appropriate case.",
    severity = SUGGESTION,
    linkType = BugPattern.LinkType.NONE)
public class JavaCase extends BugChecker
    implements MethodTreeMatcher, ClassTreeMatcher, VariableTreeMatcher {

  private static Pattern CAMEL_CASE_PATTERN_LOWER =
      Pattern.compile("^[a-z]+[A-Z0-9][a-z0-9]+[A-Za-z0-9]*$");
  private static Pattern CAMEL_CASE_PATTERN_UPPER =
      Pattern.compile("^[A-Z][a-z0-9]*[A-Z0-9][a-z0-9]+[A-Za-z0-9]*$");
  private static Pattern UNDERSCORE_PATTERN_LOWER = Pattern.compile("^[A-Za-z0-9_]*$");
  private static Pattern UNDERSCORE_PATTERN_UPPER = Pattern.compile("^[A-Z0-9_]*$");

  @Override
  public Description matchVariable(VariableTree tree, VisitorState state) {
    String name = tree.getName().toString();
    ModifiersTree modifiers = tree.getModifiers();
    if (modifiers.getFlags().contains(Modifier.FINAL)) {
      if (!isUpperUnderscoreCase(name)) {
        return buildDescription(tree)
            .addFix(SuggestedFixes.renameVariable(tree, toUpperUnderscore(name), state))
            .build();
      }
    } else if (!isLowerCamelCase(name)) {
      return buildDescription(tree)
          .addFix(SuggestedFixes.renameVariable(tree, toCamelCaseLower(name), state))
          .build();
    }
    return Description.NO_MATCH;
  }

  @Override
  public Description matchMethod(MethodTree tree, VisitorState state) {
    String name = tree.getName().toString();
    if (!isLowerCamelCase(name)) {
      return buildDescription(tree)
          .addFix(SuggestedFixes.renameMethod(tree, toCamelCaseLower(name), state))
          .build();
    }
    return Description.NO_MATCH;
  }

  @Override
  public Description matchClass(ClassTree tree, VisitorState state) {
    String name = tree.getSimpleName().toString();
    if (!isUpperCamelCase(name)) {
      String update = tree.toString().replace(name, toCamelCaseUpper(name));
      update = update.split("\\r?\\n")[1];
      return buildDescription(tree)
          .addFix(SuggestedFix.builder().replace(tree, update).build())
          .build();
    }
    return Description.NO_MATCH;
  }

  private static boolean isLowerCamelCase(String name) {
    return CAMEL_CASE_PATTERN_LOWER.matcher(name).matches();
  }

  private static boolean isUpperCamelCase(String name) {
    return CAMEL_CASE_PATTERN_UPPER.matcher(name).matches();
  }

  private static boolean isLowerUnderscoreCase(String name) {
    return UNDERSCORE_PATTERN_LOWER.matcher(name).matches();
  }

  private static boolean isUpperUnderscoreCase(String name) {
    return UNDERSCORE_PATTERN_UPPER.matcher(name).matches();
  }

  private static String toCamelCaseLower(String name) {
    CaseFormat format = CaseFormat.LOWER_UNDERSCORE;
    if (isUpperCamelCase(name)) format = CaseFormat.UPPER_CAMEL;
    else if (isUpperUnderscoreCase(name)) format = CaseFormat.UPPER_UNDERSCORE;
    return format.to(CaseFormat.LOWER_CAMEL, name);
  }

  private static String toCamelCaseUpper(String name) {
    CaseFormat format = CaseFormat.LOWER_UNDERSCORE;
    if (isLowerCamelCase(name)) format = CaseFormat.LOWER_CAMEL;
    else if (isUpperUnderscoreCase(name)) format = CaseFormat.UPPER_UNDERSCORE;
    return format.to(CaseFormat.UPPER_CAMEL, name);
  }

  private static String toUpperUnderscore(String name) {
    CaseFormat format = CaseFormat.LOWER_UNDERSCORE;
    if (isLowerCamelCase(name)) format = CaseFormat.LOWER_CAMEL;
    else if (isUpperCamelCase(name)) format = CaseFormat.UPPER_CAMEL;
    return format.to(CaseFormat.UPPER_UNDERSCORE, name);
  }
}
