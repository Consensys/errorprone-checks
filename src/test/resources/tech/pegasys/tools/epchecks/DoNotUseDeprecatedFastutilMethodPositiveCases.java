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

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.Iterator;
import java.util.Map;

import it.unimi.dsi.fastutil.bytes.Byte2CharLinkedOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2CharSortedMap;
import it.unimi.dsi.fastutil.bytes.ByteList;
import it.unimi.dsi.fastutil.chars.CharList;
import it.unimi.dsi.fastutil.doubles.Double2ByteRBTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntSet;

public class DoNotUseDeprecatedFastutilMethodPositiveCases {

  private static final IntList INT_LIST = IntList.of(1, 2, 3);
  private static final IntSet INT_SET = IntSet.of(1, 2, 3);

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

  public void useDeprecatedGenerics() {
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.contains(Integer.valueOf(1));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.indexOf(Integer.valueOf(1));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.remove(Integer.valueOf(1));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.lastIndexOf(Integer.valueOf(1));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.get(Integer.valueOf(1));
  }

  public void useDeprecatedAdd() {
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.add(Integer.valueOf(1));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.add(1, Integer.valueOf(1));
  }

  public void useDeprecatedForEach() {
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer num) {
                System.out.println(num);
            }
        });
  }

  public void useDeprecatedParallelStream() {
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.parallelStream();
  }

  public void useDeprecatedRemove() {
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.remove(2);
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_SET.rem(2);
  }

  public void useDeprecatedRemoveIf() {
    Predicate<Integer> pr = a -> (a == 27);
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.removeIf(pr);
  }

  class MyOperator<T> implements UnaryOperator<T>{
    T varc1;
    public T apply(T varc){
      return varc1;
    }
  }

  public void useDeprecatedReplaceAll() {
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.replaceAll(new MyOperator<Integer>());
  }

  public void useDeprecatedSet() {
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.set(0, Integer.valueOf(4));
  }

  public void useDeprecatedSort() {
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.sort(Comparator.reverseOrder());
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.unstableSort(Comparator.reverseOrder());
  }

  public void useDeprecatedStream() {
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.stream();
  }

  public void useDeprecatedToArray() {
    int[] arr = new int[3];
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.toIntArray(arr);
  }
}
