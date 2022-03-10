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

import it.unimi.dsi.fastutil.bytes.Byte2CharLinkedOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2CharMap;
import it.unimi.dsi.fastutil.bytes.Byte2CharSortedMap;
import it.unimi.dsi.fastutil.doubles.Double2ByteMap;
import it.unimi.dsi.fastutil.doubles.Double2ByteRBTreeMap;
import it.unimi.dsi.fastutil.ints.AbstractIntComparator;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntConsumer;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntPredicate;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;

public class DoNotUseDeprecatedFastutilMethodNegativeCases {

  private static final IntList INT_LIST = IntList.of(1, 2, 3);
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

  public void useDeprecatedGenerics() {
    INT_LIST.contains(1);
    INT_LIST.indexOf(1);
    INT_LIST.removeInt(1);
    INT_LIST.lastIndexOf(1);
    INT_LIST.getInt(1);
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
