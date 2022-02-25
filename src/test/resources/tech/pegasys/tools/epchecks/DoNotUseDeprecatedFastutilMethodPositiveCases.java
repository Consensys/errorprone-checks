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
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package tech.pegasys.tools.epchecks;

import java.util.Map;
import java.util.Iterator;

import it.unimi.dsi.fastutil.bytes.Byte2CharLinkedOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2CharSortedMap;
import it.unimi.dsi.fastutil.chars.CharList;
import it.unimi.dsi.fastutil.doubles.Double2ByteRBTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntList;

public class DoNotUseDeprecatedFastutilMethodPositiveCases {

  public void int2ObjectMapForEach() {
    Int2ObjectMap<String> map = new Int2ObjectOpenHashMap<>();
    // BUG: Diagnostic contains: int2ObjectEntrySet
    for (Map.Entry<Integer, String> m : map.entrySet()) {
      System.out.println(m.getValue());
    }
  }

  public void int2ObjectMapIterator() {
    Int2ObjectMap<String> map = new Int2ObjectOpenHashMap<>();
    // BUG: Diagnostic contains: int2ObjectEntrySet
    Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<Integer, String> entry = iterator.next();
      System.out.println(entry.getKey() + ":" + entry.getValue());
    }
  }

  public void byte2CharSortedMap() {
    Byte2CharSortedMap map = new Byte2CharLinkedOpenHashMap();
    // BUG: Diagnostic contains: byte2CharEntrySet
    for (Map.Entry<Byte, Character> m : map.entrySet()) {
      System.out.println(m.getValue());
    }
  }

  public void double2ByteRBTreeMap() {
    Double2ByteRBTreeMap map = new Double2ByteRBTreeMap();
    // BUG: Diagnostic contains: double2ByteEntrySet
    for (Map.Entry<Double, Byte> m : map.entrySet()) {
      System.out.println(m.getValue());
    }
  }

  public void useDeprecatedIntMethods() {
    IntList int_list = IntList.of(1, 2, 3);

    // BUG: Diagnostic contains: Use type-specific method instead
    int_list.contains(Integer.valueOf(1));
    // BUG: Diagnostic contains: Use type-specific method instead
    int_list.indexOf(Integer.valueOf(1));
    // BUG: Diagnostic contains: Use type-specific method instead
    int_list.add(Integer.valueOf(1));
    // BUG: Diagnostic contains: Use type-specific method instead
    int_list.add(2, Integer.valueOf(1));
  }

  public void useDeprecatedCharMethods() {
    CharList char_list = CharList.of('a', 'b', 'c');

    // BUG: Diagnostic contains: Use type-specific method instead
    char_list.contains(Character.valueOf('a'));
    // BUG: Diagnostic contains: Use type-specific method instead
    char_list.indexOf(Character.valueOf('a'));
    // BUG: Diagnostic contains: Use type-specific method instead
    char_list.add(Character.valueOf('a'));
    // BUG: Diagnostic contains: Use type-specific method instead
    char_list.add(2, Character.valueOf('a'));
  }
}
