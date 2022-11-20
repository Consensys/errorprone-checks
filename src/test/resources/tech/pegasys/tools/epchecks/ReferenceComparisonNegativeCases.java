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

import com.google.common.primitives.Bytes;
import org.junit.Test;

public class ReferenceComparisonNegativeCases {
  public boolean comparisonWithEquals(final Bytes a, final Bytes b) {
    return a.equals(b);
  }

  public boolean comparisonWithEnums() {
    return TestEnum.A == TestEnum.B;
  }

  public boolean comparisonWithThis() {
    Object o = null;
    return this == o;
  }

  public boolean comparisonWithNull(final Bytes a) {
    return a == null;
  }

  public boolean comparisonBoxedPrimitiveAndPrimitive(final Integer a) {
    return a == 27;
  }

  public boolean comparisonWithGetClass(final Bytes a, final Bytes b) {
    return a.getClass() == b.getClass();
  }

  public boolean comparisonWithEnumVariable(final TestEnum a) {
    return a == TestEnum.B;
  }

  public boolean comparisonWithEnumVariables(final TestEnum a, final TestEnum b) {
    return a == b;
  }

  private enum TestEnum {
    A,
    B,
    C;
  }
}
