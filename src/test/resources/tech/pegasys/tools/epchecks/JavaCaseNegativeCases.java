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

import java.util.Map;
import java.util.LinkedHashMap;
import org.junit.jupiter.api.Test;

public class JavaCaseNegativeCases {

  public static final int PARAM_NAME = 1;
  public static int anotherParamName = 2;
  public final int finalButNotStatic = 3;
  public final int myAST = 4;

  public void declaresValidVariable() {
    int paramName = 27;
  }

  public void lower() {
    int paramName = 28;
  }

  public void hasValidParam(int paramName) {}

  @Test
  public void test_OneIsTwo() {}

  private class ValidClassName {}

  private class Bytes8 {}

  private class ValidClass {
    int x;

    public ValidClass() {
      x = 5;
    }
  }

  private class LRUCache {}

  private class BLS {}

  final class HasAnonymousClass<K, V> {
    private final int maxSize = 10;
    private <K, V> Map<K, V> createLimitedMap(final int maxSize) {
      // Returns a new class
      return new LinkedHashMap<>(16, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
          return size() > maxSize;
        }
      };
    }
  }
}
