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

import com.google.errorprone.CompilationTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UseFastutilTest {

  private CompilationTestHelper compilationHelper;

  @BeforeEach
  public void setup() {
    compilationHelper = CompilationTestHelper.newInstance(UseFastutil.class, getClass());
  }

  @Test
  public void bannedMethodsPositiveCases() {
    compilationHelper.addSourceFile("UseFastutilPositiveCases.java").doTest();
  }

  @Test
  public void bannedMethodsNegativeCases() {
    compilationHelper.addSourceFile("UseFastutilNegativeCases.java").doTest();
  }
}
