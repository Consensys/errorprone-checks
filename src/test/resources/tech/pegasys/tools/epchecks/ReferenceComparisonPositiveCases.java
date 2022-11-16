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

import java.util.List;

public class ReferenceComparisonPositiveCases {
    public void comparisonWithIdentifiers(final List<Integer> a, final List<Integer> b) {
    // BUG: Diagnostic contains: Reference comparison should be value comparison
    if (a == b) {
      System.out.println("they're the same");
    }
  }

  public void comparisonWithIdentifierAndMethodInvocation(final List<Integer> a) {
    // BUG: Diagnostic contains: Reference comparison should be value comparison
    if (a == List.of(1, 2, 3)) {
      System.out.println("they're the same");
    }
  }

  public void comparisonWithMethodInvocationAndIdentifier(final List<Integer> b) {
    // BUG: Diagnostic contains: Reference comparison should be value comparison
    if (List.of(1, 2, 3) == b) {
      System.out.println("they're the same");
    }
  }

  public void comparisonWithMethodInvocations(final List<Integer> b) {
    // BUG: Diagnostic contains: Reference comparison should be value comparison
    if (List.of(1, 2, 3) == List.of(1, 2, 3, 4)) {
      System.out.println("they're the same");
    }
  }
}