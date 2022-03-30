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

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.Iterator;
import java.util.Map;

import it.unimi.dsi.fastutil.booleans.*;
import it.unimi.dsi.fastutil.bytes.*;
import it.unimi.dsi.fastutil.chars.*;
import it.unimi.dsi.fastutil.doubles.*;
import it.unimi.dsi.fastutil.floats.*;
import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.longs.*;
import it.unimi.dsi.fastutil.objects.*;
import it.unimi.dsi.fastutil.shorts.*;

public class DoNotUseDeprecatedFastutilMethodPositiveCases {

  private static final BooleanList BOOLEAN_LIST = BooleanList.of(true, false, true);
  private static final ByteList BYTE_LIST = ByteList.of((byte)1, (byte)2, (byte)3);
  private static final CharList CHAR_LIST = CharList.of('a', 'b', 'c');
  private static final DoubleList DOUBLE_LIST = DoubleList.of(1.0, 2.0, 3.0);
  private static final FloatList FLOAT_LIST = FloatList.of(1.0F, 2.0F, 3.0F);
  private static final IntList INT_LIST = IntList.of(1, 2, 3);
  private static final LongList LONG_LIST = LongList.of(1L, 2L, 3L);
  private static final ShortList SHORT_LIST = ShortList.of((short)1, (short)2, (short)3);

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
    BOOLEAN_LIST.contains(Boolean.valueOf(true));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    BOOLEAN_LIST.indexOf(Boolean.valueOf(true));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    BOOLEAN_LIST.remove(Integer.valueOf(1));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    BOOLEAN_LIST.lastIndexOf(Boolean.valueOf(true));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    BOOLEAN_LIST.get(1);

    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    BYTE_LIST.contains(Byte.valueOf("1"));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    BYTE_LIST.indexOf(Byte.valueOf("1"));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    BYTE_LIST.remove(Integer.valueOf(1));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    BYTE_LIST.lastIndexOf(Byte.valueOf("1"));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    BYTE_LIST.get(1);

    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    CHAR_LIST.contains(Character.valueOf('a'));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    CHAR_LIST.indexOf(Character.valueOf('a'));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    CHAR_LIST.remove(Integer.valueOf(1));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    CHAR_LIST.lastIndexOf(Character.valueOf('a'));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    CHAR_LIST.get(1);

    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    DOUBLE_LIST.contains(Double.valueOf(1.0));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    DOUBLE_LIST.indexOf(Double.valueOf(1.0));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    DOUBLE_LIST.remove(Integer.valueOf(1));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    DOUBLE_LIST.lastIndexOf(Double.valueOf(1.0));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    DOUBLE_LIST.get(1);

    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    FLOAT_LIST.contains(Float.valueOf(1.0F));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    FLOAT_LIST.indexOf(Float.valueOf(1.0F));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    FLOAT_LIST.remove(Integer.valueOf(1));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    FLOAT_LIST.lastIndexOf(Float.valueOf(1.0F));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    FLOAT_LIST.get(1);

    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.contains(Integer.valueOf(1));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.indexOf(Integer.valueOf(1));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.remove(Integer.valueOf(1));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.lastIndexOf(Integer.valueOf(1));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    INT_LIST.get(1);

    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    LONG_LIST.contains(Long.valueOf(1L));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    LONG_LIST.indexOf(Long.valueOf(1L));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    LONG_LIST.remove(Integer.valueOf(1));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    LONG_LIST.lastIndexOf(Long.valueOf(1L));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    LONG_LIST.get(1);

    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    SHORT_LIST.contains(Short.valueOf("1"));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    SHORT_LIST.indexOf(Short.valueOf("1"));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    SHORT_LIST.remove(Integer.valueOf(1));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    SHORT_LIST.lastIndexOf(Short.valueOf("1"));
    // BUG: Diagnostic contains: Use type-specific fastutil method instead
    SHORT_LIST.get(1);
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
