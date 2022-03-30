/*
 * Copyright ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package tech.pegasys.tools.epchecks;

public class JavaCasePositiveCases {

  // BUG: Diagnostic contains: PARAM_NAME
  public static final int param_name = 1;

  // BUG: Diagnostic contains: PARAM_NAME
  public static final int paramName = 2;

  // BUG: Diagnostic contains: PARAM_NAME
  public static final int ParamName = 3;

  // BUG: Diagnostic contains: anotherParamName
  public static int another_param_name = 4;

  // BUG: Diagnostic contains: finalParamName
  public final int final_param_name = 5;

  // BUG: Diagnostic contains: upper
  private static Integer UPPER;

  public void declaresInvalidVariable() {
    // BUG: Diagnostic contains: paramName
    int param_name = 27;
  }

  // BUG: Diagnostic contains: invalidFuncName
  public void invalid_func_name() {}

  // BUG: Diagnostic contains: invalidFuncName
  public void Invalid_Func_Name() {}

  // BUG: Diagnostic contains: invalidFuncName
  public void INVALID_FUNC_NAME() {}

  // BUG: Diagnostic contains: paramName
  public void hasInvalidParam(int param_name) {}

  // BUG: Diagnostic contains: InvalidClassName
  private class invalidClassName {}

  // BUG: Diagnostic contains: InvalidClassName
  private class Invalid_Class_Name {}

  // BUG: Diagnostic contains: InvalidClassName
  private class invalid_class_name {}

  // BUG: Diagnostic contains: InvalidClassName
  private class INVALID_CLASS_NAME {}

  private interface TestInterface {
    // BUG: Diagnostic contains: NUMBER_ONE
    int numberOne = 1;

    // BUG: Diagnostic contains: NUMBER_TWO
    int NumberTwo = 2;

    // BUG: Diagnostic contains: NUMBER_THREE
    int number_three = 3;
  }
}
