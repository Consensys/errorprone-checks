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
  public void comparisonWithEquals(final Bytes a, final Bytes b) {
    if (a.equals(b)) {
      System.out.println("they're the same");
    }
  }

  public void comparisonWithEnums() {
    if (TestEnum.A == TestEnum.B) {
      System.out.println("they're the same");
    }
  }

  public void comparisonWithEnumVariable(final TestEnum a) {
    if (a == TestEnum.B) {
      System.out.println("they're the same");
    }
  }

  public void comparisonWithThis() {
    Object o = null;
    if (this == o) {
      System.out.println("they're the same");
    }
  }

  public void comparisonBoxedPrimitiveAndPrimitive(Integer a) {
    if (a == 0) {
      System.out.println("equals zero");
    }
    if (0 == a) {
      System.out.println("equals zero");
    }
  }

  public void comparisonWithEnumVariables(final TestEnum a, final TestEnum b) {
    if (a == b) {
      System.out.println("they're the same");
    }
  }

  public void comparisonWithNull(final Bytes a) {
    if (a == null) {
      System.out.println("it's null");
    }
  }

  public enum TestEnum {
    A,
    B,
    C;
  }
}
