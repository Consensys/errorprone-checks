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

import java.util.Optional;

public class MathTargetTypeNegativeCases {

    public void expectedInt() {
        int a = Math.min(0, 1);
    }

    public void expectedLongOnlyOne() {
        long a = Math.min(0L, 1);
        long b = Math.min(0, 1L);
    }

    public void expectedLongBoth() {
        long a = Math.min(0L, 1L);
    }

    public void expectedLongAssignment() {
        long a;
        a = Math.min(0L, 1L);
    }

    public void expectedIntAllFuncs() {
        int a = Math.min(0, 1);
        int b = Math.max(0, 1);
        int c = Math.addExact(0, 1);
        int e = Math.subtractExact(0, 1);
        int d = Math.multiplyExact(0, 1);
        int f = Math.floorDiv(0, 1);
        int h = Math.floorMod(0, 1);
    }

    public void binaryOperations() {
        int a = Math.min(0, 1) + 1;
        int b = Math.min(0, 1) - 1;
        int c = Math.min(0, 1) / 1;
        int d = Math.min(0, 1) * 1;
    }

    public void resultIsTypevar() {
        Optional<Integer> a = Optional.of(Math.min(0, 1));
    }
}