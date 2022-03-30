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

public class MathTargetTypePositiveCases {

    public void takesLong(int a, long b, int c) {}

    public long expectedLong() {
        // BUG: Diagnostic contains: Neither of the function arguments are long types
        long a = Math.min(0, 1);
        // BUG: Diagnostic contains: Neither of the function arguments are long types
        a = (long) Math.min(0, 1);
        // BUG: Diagnostic contains: Neither of the function arguments are long types
        takesLong(0, Math.min(0, 1), 0);
        // BUG: Diagnostic contains: Neither of the function arguments are long types
        return Math.min(0, 1);
    }

    public void takesDouble(int a, double b, int c) {}

    public double expectedDouble() {
        // BUG: Diagnostic contains: Neither of the function arguments are double types
        double a = Math.min(0, 1);
        // BUG: Diagnostic contains: Neither of the function arguments are double types
        a = (double) Math.min(0, 1);
        // BUG: Diagnostic contains: Neither of the function arguments are double types
        takesDouble(0, Math.min(0, 1), 0);
        // BUG: Diagnostic contains: Neither of the function arguments are double types
        return Math.min(0, 1);
    }

    public void takesFloat(int a, float b, int c) {}

    public float expectedFloat() {
        // BUG: Diagnostic contains: Neither of the function arguments are float types
        float a = Math.min(0, 1);
        // BUG: Diagnostic contains: Neither of the function arguments are float types
        a = (float) Math.min(0, 1);
        // BUG: Diagnostic contains: Neither of the function arguments are float types
        takesFloat(0, Math.min(0, 1), 0);
        // BUG: Diagnostic contains: Neither of the function arguments are float types
        return Math.min(0, 1);
    }

    public void expectedLongAllFuncs() {
        // BUG: Diagnostic contains: Neither of the function arguments are long types
        long a = Math.min(0, 1);
        // BUG: Diagnostic contains: Neither of the function arguments are long types
        long b = Math.max(0, 1);
        // BUG: Diagnostic contains: Neither of the function arguments are long types
        long c = Math.addExact(0, 1);
        // BUG: Diagnostic contains: Neither of the function arguments are long types
        long e = Math.subtractExact(0, 1);
        // BUG: Diagnostic contains: Neither of the function arguments are long types
        long d = Math.multiplyExact(0, 1);
        // BUG: Diagnostic contains: Neither of the function arguments are long types
        long f = Math.floorDiv(0, 1);
        // BUG: Diagnostic contains: Neither of the function arguments are long types
        long h = Math.floorMod(0, 1);
        // BUG: Diagnostic contains: Neither of the function arguments are long types
        long i = Math.min(0, 1) + 1;
        // BUG: Diagnostic contains: Neither of the function arguments are long types
        long j = Math.min(0, 1) - 1;
        // BUG: Diagnostic contains: Neither of the function arguments are long types
        long k = Math.min(0, 1) / 1;
        // BUG: Diagnostic contains: Neither of the function arguments are long types
        long l = Math.min(0, 1) * 1;
    }

    public void expectedLongVars() {
        int a = 0;
        int b = 1;
        // BUG: Diagnostic contains: Neither of the function arguments are long types
        long c = Math.min(a, b);
    }

    public void expectedLongArgs(int a, int b) {
        // BUG: Diagnostic contains: Neither of the function arguments are long types
        long c = Math.min(a, b);
    }
}