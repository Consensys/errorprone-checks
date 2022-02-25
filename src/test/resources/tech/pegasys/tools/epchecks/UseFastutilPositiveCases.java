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

import static java.util.Map.entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class UseFastutilPositiveCases {

  /* LISTS */

  static public void usesOf() {
    // BUG: Diagnostic contains: IntList list_of_ints = IntList.of(1, 2, 3)
    List<Integer> list_of_ints = List.of(1, 2, 3);
    // BUG: Diagnostic contains: IntSet set_of_ints = IntSet.of(1, 2, 3)
    Set<Integer> set_of_ints = Set.of(1, 2, 3);
  }

  static public void usesArrayList() {
    // BUG: Diagnostic contains: BooleanList bools = new BooleanArrayList()
    List<Boolean> bools = new ArrayList<>();
    // BUG: Diagnostic contains: ByteList bytes = new ByteArrayList()
    List<Byte> bytes = new ArrayList<>();
    // BUG: Diagnostic contains: CharList chars = new CharArrayList()
    List<Character> chars = new ArrayList<>();
    // BUG: Diagnostic contains: DoubleList doubles = new DoubleArrayList()
    List<Double> doubles = new ArrayList<>();
    // BUG: Diagnostic contains: FloatList floats = new FloatArrayList()
    List<Float> floats = new ArrayList<>();
    // BUG: Diagnostic contains: IntList ints = new IntArrayList()
    List<Integer> ints = new ArrayList<>();
    // BUG: Diagnostic contains: LongList longs = new LongArrayList()
    List<Long> longs = new ArrayList<>();
    // BUG: Diagnostic contains: ShortList shorts = new ShortArrayList()
    List<Short> shorts = new ArrayList<>();
  }

  static public void usesHashSet() {
    // BUG: Diagnostic contains: BooleanSet bools = new BooleanOpenHashSet()
    Set<Boolean> bools = new HashSet<>();
    // BUG: Diagnostic contains: ByteSet bytes = new ByteOpenHashSet()
    Set<Byte> bytes = new HashSet<>();
    // BUG: Diagnostic contains: CharSet chars = new CharOpenHashSet()
    Set<Character> chars = new HashSet<>();
    // BUG: Diagnostic contains: DoubleSet doubles = new DoubleOpenHashSet()
    Set<Double> doubles = new HashSet<>();
    // BUG: Diagnostic contains: FloatSet floats = new FloatOpenHashSet()
    Set<Float> floats = new HashSet<>();
    // BUG: Diagnostic contains: IntSet ints = new IntOpenHashSet()
    Set<Integer> ints = new HashSet<>();
    // BUG: Diagnostic contains: LongSet longs = new LongOpenHashSet()
    Set<Long> longs = new HashSet<>();
    // BUG: Diagnostic contains: ShortSet shorts = new ShortOpenHashSet()
    Set<Short> shorts = new HashSet<>();
  }

  static public void usesLinkedHashSet() {
    // BUG: Diagnostic contains: BooleanLinkedOpenHashSet bools = new BooleanLinkedOpenHashSet()
    LinkedHashSet<Boolean> bools = new LinkedHashSet<>();
    // BUG: Diagnostic contains: ByteLinkedOpenHashSet bytes = new ByteLinkedOpenHashSet()
    LinkedHashSet<Byte> bytes = new LinkedHashSet<>();
    // BUG: Diagnostic contains: CharLinkedOpenHashSet chars = new CharLinkedOpenHashSet()
    LinkedHashSet<Character> chars = new LinkedHashSet<>();
    // BUG: Diagnostic contains: DoubleLinkedOpenHashSet doubles = new DoubleLinkedOpenHashSet()
    LinkedHashSet<Double> doubles = new LinkedHashSet<>();
    // BUG: Diagnostic contains: FloatLinkedOpenHashSet floats = new FloatLinkedOpenHashSet()
    LinkedHashSet<Float> floats = new LinkedHashSet<>();
    // BUG: Diagnostic contains: IntLinkedOpenHashSet ints = new IntLinkedOpenHashSet()
    LinkedHashSet<Integer> ints = new LinkedHashSet<>();
    // BUG: Diagnostic contains: LongLinkedOpenHashSet longs = new LongLinkedOpenHashSet()
    LinkedHashSet<Long> longs = new LinkedHashSet<>();
    // BUG: Diagnostic contains: ShortLinkedOpenHashSet shorts = new ShortLinkedOpenHashSet()
    LinkedHashSet<Short> shorts = new LinkedHashSet<>();
  }

  static public void usesTreeSet() {
    // BUG: Diagnostic contains: BooleanRBTreeSet bools = new BooleanRBTreeSet()
    TreeSet<Boolean> bools = new TreeSet<>();
    // BUG: Diagnostic contains: ByteRBTreeSet bytes = new ByteRBTreeSet()
    TreeSet<Byte> bytes = new TreeSet<>();
    // BUG: Diagnostic contains: CharRBTreeSet chars = new CharRBTreeSet()
    TreeSet<Character> chars = new TreeSet<>();
    // BUG: Diagnostic contains: DoubleRBTreeSet doubles = new DoubleRBTreeSet()
    TreeSet<Double> doubles = new TreeSet<>();
    // BUG: Diagnostic contains: FloatRBTreeSet floats = new FloatRBTreeSet()
    TreeSet<Float> floats = new TreeSet<>();
    // BUG: Diagnostic contains: IntRBTreeSet ints = new IntRBTreeSet()
    TreeSet<Integer> ints = new TreeSet<>();
    // BUG: Diagnostic contains: LongRBTreeSet longs = new LongRBTreeSet()
    TreeSet<Long> longs = new TreeSet<>();
    // BUG: Diagnostic contains: ShortRBTreeSet shorts = new ShortRBTreeSet()
    TreeSet<Short> shorts = new TreeSet<>();
  }

  /* MAPS */

  static public void usesMap() {
    // BUG: Diagnostic contains: Boolean2ObjectMap<String> bool_to_str = new Boolean2ObjectOpenHashMap<String>()
    Map<Boolean, String> bool_to_str = new HashMap<>();
    // BUG: Diagnostic contains: Object2BooleanMap<String> str_to_bool = new Object2BooleanOpenHashMap<String>()
    Map<String, Boolean> str_to_bool = new HashMap<>();
    // BUG: Diagnostic contains: Byte2ObjectMap<String> byte_to_str = new Byte2ObjectOpenHashMap<String>()
    Map<Byte, String> byte_to_str = new HashMap<>();
    // BUG: Diagnostic contains: Object2ByteMap<String> str_to_byte = new Object2ByteOpenHashMap<String>()
    Map<String, Byte> str_to_byte = new HashMap<>();
    // BUG: Diagnostic contains: Char2ObjectMap<String> char_to_str = new Char2ObjectOpenHashMap<String>()
    Map<Character, String> char_to_str = new HashMap<>();
    // BUG: Diagnostic contains: Object2CharMap<String> str_to_char = new Object2CharOpenHashMap<String>()
    Map<String, Character> str_to_char = new HashMap<>();
    // BUG: Diagnostic contains: Double2ObjectMap<String> double_to_str = new Double2ObjectOpenHashMap<String>()
    Map<Double, String> double_to_str = new HashMap<>();
    // BUG: Diagnostic contains: Object2DoubleMap<String> str_to_double = new Object2DoubleOpenHashMap<String>()
    Map<String, Double> str_to_double = new HashMap<>();
    // BUG: Diagnostic contains: Float2ObjectMap<String> float_to_str = new Float2ObjectOpenHashMap<String>()
    Map<Float, String> float_to_str = new HashMap<>();
    // BUG: Diagnostic contains: Object2FloatMap<String> str_to_float = new Object2FloatOpenHashMap<String>()
    Map<String, Float> str_to_float = new HashMap<>();
    // BUG: Diagnostic contains: Int2ObjectMap<String> int_to_str = new Int2ObjectOpenHashMap<String>()
    Map<Integer, String> int_to_str = new HashMap<>();
    // BUG: Diagnostic contains: Object2IntMap<String> str_to_int = new Object2IntOpenHashMap<String>()
    Map<String, Integer> str_to_int = new HashMap<>();
    // BUG: Diagnostic contains: Long2ObjectMap<String> long_to_str = new Long2ObjectOpenHashMap<String>()
    Map<Long, String> long_to_str = new HashMap<>();
    // BUG: Diagnostic contains: Object2LongMap<String> str_to_long = new Object2LongOpenHashMap<String>()
    Map<String, Long> str_to_long = new HashMap<>();
    // BUG: Diagnostic contains: Short2ObjectMap<String> short_to_str = new Short2ObjectOpenHashMap<String>()
    Map<Short, String> short_to_str = new HashMap<>();
    // BUG: Diagnostic contains: Object2ShortMap<String> str_to_short = new Object2ShortOpenHashMap<String>()
    Map<String, Short> str_to_short = new HashMap<>();
  }

  static public void usesSimpleMap() {
    // BUG: Diagnostic contains: Int2IntMap int_to_int = new Int2IntOpenHashMap()
    Map<Integer, Integer> int_to_int = new HashMap<>();
    // BUG: Diagnostic contains: Int2CharMap int_to_char = new Int2CharOpenHashMap()
    Map<Integer, Character> int_to_char = new HashMap<>();
    // BUG: Diagnostic contains: Char2IntMap char_to_int = new Char2IntOpenHashMap()
    Map<Character, Integer> char_to_int = new HashMap<>();
  }

  static public void usesComplexMap() {
    // BUG: Diagnostic contains: Int2ObjectMap<Map<String, String>> int_to_map = new Int2ObjectOpenHashMap<Map<String, String>>()
    Map<Integer, Map<String, String>> int_to_map = new HashMap<>();
  }

  static public void usesComplexMapNoSpaces() {
    // BUG: Diagnostic contains: Int2ObjectMap<Map<String, String>> int_to_map = new Int2ObjectOpenHashMap<Map<String, String>>()
    Map<Integer,Map<String,String>> int_to_map=new HashMap<>();
  }
}
