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

import it.unimi.dsi.fastutil.booleans.*;
import it.unimi.dsi.fastutil.bytes.*;
import it.unimi.dsi.fastutil.chars.*;
import it.unimi.dsi.fastutil.doubles.*;
import it.unimi.dsi.fastutil.floats.*;
import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.longs.*;
import it.unimi.dsi.fastutil.objects.*;
import it.unimi.dsi.fastutil.shorts.*;

public class DoNotUseDeprecatedFastutilMethodNegativeCases {

  private static final BooleanList BOOLEAN_LIST = BooleanList.of(true, false, true);
  private static final ByteList BYTE_LIST = ByteList.of((byte)1, (byte)2, (byte)3);
  private static final CharList CHAR_LIST = CharList.of('a', 'b', 'c');
  private static final DoubleList DOUBLE_LIST = DoubleList.of(1.0, 2.0, 3.0);
  private static final FloatList FLOAT_LIST = FloatList.of(1.0F, 2.0F, 3.0F);
  private static final IntList INT_LIST = IntList.of(1, 2, 3);
  private static final LongList LONG_LIST = LongList.of(1L, 2L, 3L);
  private static final ShortList SHORT_LIST = ShortList.of((short)1, (short)2, (short)3);

  private static final IntSet INT_SET = IntSet.of(1, 2, 3);

  public void typeSpecificInt2ObjectMapForEach() {
    Int2ObjectMap<String> map = new Int2ObjectOpenHashMap<>();
    for (Int2ObjectMap.Entry<String> m : map.int2ObjectEntrySet()) {
      System.out.println(m.getValue());
    }
  }

  public void typeSpecificInt2ObjectMapIterator() {
    Int2ObjectMap<String> map = new Int2ObjectOpenHashMap<>();
    ObjectIterator<Int2ObjectMap.Entry<String>> iterator = map.int2ObjectEntrySet().iterator();
    while (iterator.hasNext()) {
      Int2ObjectMap.Entry<String> entry = iterator.next();
      System.out.println(entry.getKey() + ":" + entry.getValue());
    }
  }

  public void typeSpecificByte2CharSortedMap() {
    Byte2CharSortedMap map = new Byte2CharLinkedOpenHashMap();
    for (Byte2CharMap.Entry m : map.byte2CharEntrySet()) {
      System.out.println(m.getValue());
    }
  }

  public void typeSpecificDouble2ByteRBTreeMap() {
    Double2ByteRBTreeMap map = new Double2ByteRBTreeMap();
    for (Double2ByteMap.Entry m : map.double2ByteEntrySet()) {
      System.out.println(m.getValue());
    }
  }

  public void object2ObjectMapEntrySet() {
    // entrySet() for object2object is not deprecated.
    Object2ObjectMap<String, String> map = new Object2ObjectOpenHashMap<>();
    for (Map.Entry<String, String> m : map.entrySet()) {
      System.out.println(m.getValue());
    }
  }

  public void object2ObjectMapTypeSpecificEntrySet() {
    Object2ObjectMap<String, String> map = new Object2ObjectOpenHashMap<>();
    for (Object2ObjectMap.Entry<String, String> m : map.object2ObjectEntrySet()) {
      System.out.println(m.getValue());
    }
  }

  public void useDeprecatedGenerics() {

    BOOLEAN_LIST.contains(true);
    BOOLEAN_LIST.indexOf(true);
    BOOLEAN_LIST.removeBoolean(1);
    BOOLEAN_LIST.lastIndexOf(true);
    BOOLEAN_LIST.getBoolean(1);

    BYTE_LIST.contains((byte)1);
    BYTE_LIST.indexOf((byte)1);
    BYTE_LIST.removeByte(1);
    BYTE_LIST.lastIndexOf((byte)1);
    BYTE_LIST.getByte(1);

    CHAR_LIST.contains('a');
    CHAR_LIST.indexOf('a');
    CHAR_LIST.removeChar('a');
    CHAR_LIST.lastIndexOf('a');
    CHAR_LIST.getChar(1);

    DOUBLE_LIST.contains(1.0);
    DOUBLE_LIST.indexOf(1.0);
    DOUBLE_LIST.removeDouble(1);
    DOUBLE_LIST.lastIndexOf(1.0);
    DOUBLE_LIST.getDouble(1);

    FLOAT_LIST.contains(1.0F);
    FLOAT_LIST.indexOf(1.0F);
    FLOAT_LIST.removeFloat(1);
    FLOAT_LIST.lastIndexOf(1.0F);
    FLOAT_LIST.getFloat(1);

    INT_LIST.contains(1);
    INT_LIST.indexOf(1);
    INT_LIST.removeInt(1);
    INT_LIST.lastIndexOf(1);
    INT_LIST.getInt(1);

    LONG_LIST.contains(1L);
    LONG_LIST.indexOf(1L);
    LONG_LIST.removeLong(1);
    LONG_LIST.lastIndexOf(1L);
    LONG_LIST.getLong(1);

    SHORT_LIST.contains((short)1);
    SHORT_LIST.indexOf((short)1);
    SHORT_LIST.removeShort(1);
    SHORT_LIST.lastIndexOf((short)1);
    SHORT_LIST.getShort(1);
  }

  public void useDeprecatedAdd() {
    INT_LIST.add(1);
    INT_LIST.add(1, 1);
  }

  public void useDeprecatedForEach() {
    INT_LIST.forEach(new IntConsumer() {
            @Override
            public void accept(int num) {
                System.out.println(num);
            }
        });
  }

  public void useDeprecatedParallelStream() {
    INT_LIST.intParallelStream();
  }

  public void useDeprecatedRemove() {
    INT_LIST.removeInt(2);
    INT_SET.remove(2);
  }

  public void useDeprecatedRemoveIf() {
    IntPredicate pr = a -> (a == 27);
    INT_LIST.removeIf(pr);
  }

  public void useDeprecatedReplaceAll() {
    INT_LIST.replaceAll(a -> a * 2);
  }

  public void useDeprecatedSet() {
    INT_LIST.set(0, 4);
  }

  public void useDeprecatedSort() {
    INT_LIST.sort(new AbstractIntComparator() {
        @Override
        public int compare(int left, int right)
        {
            return 0;
        }
    });
    INT_LIST.unstableSort(new AbstractIntComparator() {
        @Override
        public int compare(int left, int right)
        {
            return 0;
        }
    });
  }

  public void useDeprecatedStream() {
    INT_LIST.intStream();
  }
}
