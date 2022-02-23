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

import it.unimi.dsi.fastutil.booleans.*;
import it.unimi.dsi.fastutil.bytes.*;
import it.unimi.dsi.fastutil.chars.*;
import it.unimi.dsi.fastutil.doubles.*;
import it.unimi.dsi.fastutil.floats.*;
import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.longs.*;
import it.unimi.dsi.fastutil.objects.*;
import it.unimi.dsi.fastutil.shorts.*;

public class UseFastutilNegativeCases {

  /* LISTS */

  static public void usesOf() {
    BooleanList list_of_booleans = BooleanList.of();
    BooleanSet set_of_booleans = BooleanSet.of();
    ByteList list_of_bytes = ByteList.of();
    ByteSet set_of_bytes = ByteSet.of();
    CharList list_of_chars = CharList.of();
    CharSet set_of_chars = CharSet.of();
    DoubleList list_of_doubles = DoubleList.of();
    DoubleSet set_of_doubles = DoubleSet.of();
    FloatList list_of_floats = FloatList.of();
    FloatSet set_of_floats = FloatSet.of();
    IntList list_of_ints = IntList.of();
    IntSet set_of_ints = IntSet.of();
    LongList list_of_longs = LongList.of();
    LongSet set_of_longs = LongSet.of();
    ObjectList list_of_objects = ObjectList.of();
    ObjectSet set_of_objects = ObjectSet.of();
    ReferenceList list_of_references = ReferenceList.of();
    ReferenceSet set_of_references = ReferenceSet.of();
    ShortList list_of_shorts = ShortList.of();
    ShortSet set_of_shorts = ShortSet.of();
  }

  static public void usesArrayList() {
    BooleanList booleans = new BooleanArrayList();
    ByteList bytes = new ByteArrayList();
    CharList chars = new CharArrayList();
    DoubleList doubles = new DoubleArrayList();
    FloatList floats = new FloatArrayList();
    IntList ints = new IntArrayList();
    LongList longs = new LongArrayList();
    ObjectList objects = new ObjectArrayList();
    ReferenceList references = new ReferenceArrayList();
    ShortList shorts = new ShortArrayList();
  }

  static public void usesOpenHashSet() {
    BooleanSet booleans = new BooleanOpenHashSet();
    ByteSet bytes = new ByteOpenHashSet();
    CharSet chars = new CharOpenHashSet();
    DoubleSet doubles = new DoubleOpenHashSet();
    FloatSet floats = new FloatOpenHashSet();
    IntSet ints = new IntOpenHashSet();
    LongSet longs = new LongOpenHashSet();
    ObjectSet objects = new ObjectOpenHashSet();
    ReferenceSet references = new ReferenceOpenHashSet();
    ShortSet shorts = new ShortOpenHashSet();
  }

  static public void usesLinkedOpenHashSet() {
    ByteLinkedOpenHashSet bytes = new ByteLinkedOpenHashSet();
    CharLinkedOpenHashSet chars = new CharLinkedOpenHashSet();
    DoubleLinkedOpenHashSet doubles = new DoubleLinkedOpenHashSet();
    FloatLinkedOpenHashSet floats = new FloatLinkedOpenHashSet();
    IntLinkedOpenHashSet ints = new IntLinkedOpenHashSet();
    LongLinkedOpenHashSet longs = new LongLinkedOpenHashSet();
    ObjectLinkedOpenHashSet objects = new ObjectLinkedOpenHashSet();
    ReferenceLinkedOpenHashSet references = new ReferenceLinkedOpenHashSet();
    ShortLinkedOpenHashSet shorts = new ShortLinkedOpenHashSet();
  }

  static public void usesTreeSet() {
    // TreeSet uses RB trees: https://stackoverflow.com/a/23277366
    ByteRBTreeSet bytes = new ByteRBTreeSet();
    CharRBTreeSet chars = new CharRBTreeSet();
    DoubleRBTreeSet doubles = new DoubleRBTreeSet();
    FloatRBTreeSet floats = new FloatRBTreeSet();
    IntRBTreeSet ints = new IntRBTreeSet();
    LongRBTreeSet longs = new LongRBTreeSet();
    ObjectRBTreeSet objects = new ObjectRBTreeSet();
    ShortRBTreeSet shorts = new ShortRBTreeSet();
  }

  /* MAPS */

  static public void usesOpenHashMap() {
    Byte2BooleanMap byte_to_boolean = new Byte2BooleanOpenHashMap();
    Byte2ByteMap byte_to_byte = new Byte2ByteOpenHashMap();
    Byte2CharMap byte_to_char = new Byte2CharOpenHashMap();
    Byte2DoubleMap byte_to_double = new Byte2DoubleOpenHashMap();
    Byte2FloatMap byte_to_float = new Byte2FloatOpenHashMap();
    Byte2IntMap byte_to_int = new Byte2IntOpenHashMap();
    Byte2LongMap byte_to_long = new Byte2LongOpenHashMap();
    Byte2ObjectMap byte_to_object = new Byte2ObjectOpenHashMap<>();
    Byte2ReferenceMap byte_to_reference = new Byte2ReferenceOpenHashMap<>();
    Byte2ShortMap byte_to_short = new Byte2ShortOpenHashMap();
    Char2BooleanMap char_to_boolean = new Char2BooleanOpenHashMap();
    Char2ByteMap char_to_byte = new Char2ByteOpenHashMap();
    Char2CharMap char_to_char = new Char2CharOpenHashMap();
    Char2DoubleMap char_to_double = new Char2DoubleOpenHashMap();
    Char2FloatMap char_to_float = new Char2FloatOpenHashMap();
    Char2IntMap char_to_int = new Char2IntOpenHashMap();
    Char2LongMap char_to_long = new Char2LongOpenHashMap();
    Char2ObjectMap char_to_object = new Char2ObjectOpenHashMap<>();
    Char2ReferenceMap char_to_reference = new Char2ReferenceOpenHashMap<>();
    Char2ShortMap char_to_short = new Char2ShortOpenHashMap();
    Double2BooleanMap double_to_boolean = new Double2BooleanOpenHashMap();
    Double2ByteMap double_to_byte = new Double2ByteOpenHashMap();
    Double2CharMap double_to_char = new Double2CharOpenHashMap();
    Double2DoubleMap double_to_double = new Double2DoubleOpenHashMap();
    Double2FloatMap double_to_float = new Double2FloatOpenHashMap();
    Double2IntMap double_to_int = new Double2IntOpenHashMap();
    Double2LongMap double_to_long = new Double2LongOpenHashMap();
    Double2ObjectMap double_to_object = new Double2ObjectOpenHashMap<>();
    Double2ReferenceMap double_to_reference = new Double2ReferenceOpenHashMap<>();
    Double2ShortMap double_to_short = new Double2ShortOpenHashMap();
    Float2BooleanMap float_to_boolean = new Float2BooleanOpenHashMap();
    Float2ByteMap float_to_byte = new Float2ByteOpenHashMap();
    Float2CharMap float_to_char = new Float2CharOpenHashMap();
    Float2DoubleMap float_to_double = new Float2DoubleOpenHashMap();
    Float2FloatMap float_to_float = new Float2FloatOpenHashMap();
    Float2IntMap float_to_int = new Float2IntOpenHashMap();
    Float2LongMap float_to_long = new Float2LongOpenHashMap();
    Float2ObjectMap float_to_object = new Float2ObjectOpenHashMap<>();
    Float2ReferenceMap float_to_reference = new Float2ReferenceOpenHashMap<>();
    Float2ShortMap float_to_short = new Float2ShortOpenHashMap();
    Int2BooleanMap int_to_boolean = new Int2BooleanOpenHashMap();
    Int2ByteMap int_to_byte = new Int2ByteOpenHashMap();
    Int2CharMap int_to_char = new Int2CharOpenHashMap();
    Int2DoubleMap int_to_double = new Int2DoubleOpenHashMap();
    Int2FloatMap int_to_float = new Int2FloatOpenHashMap();
    Int2IntMap int_to_int = new Int2IntOpenHashMap();
    Int2LongMap int_to_long = new Int2LongOpenHashMap();
    Int2ObjectMap int_to_object = new Int2ObjectOpenHashMap<>();
    Int2ReferenceMap int_to_reference = new Int2ReferenceOpenHashMap<>();
    Int2ShortMap int_to_short = new Int2ShortOpenHashMap();
    Long2BooleanMap long_to_boolean = new Long2BooleanOpenHashMap();
    Long2ByteMap long_to_byte = new Long2ByteOpenHashMap();
    Long2CharMap long_to_char = new Long2CharOpenHashMap();
    Long2DoubleMap long_to_double = new Long2DoubleOpenHashMap();
    Long2FloatMap long_to_float = new Long2FloatOpenHashMap();
    Long2IntMap long_to_int = new Long2IntOpenHashMap();
    Long2LongMap long_to_long = new Long2LongOpenHashMap();
    Long2ObjectMap long_to_object = new Long2ObjectOpenHashMap<>();
    Long2ReferenceMap long_to_reference = new Long2ReferenceOpenHashMap<>();
    Long2ShortMap long_to_short = new Long2ShortOpenHashMap();
    Object2BooleanMap object_to_boolean = new Object2BooleanOpenHashMap();
    Object2ByteMap object_to_byte = new Object2ByteOpenHashMap();
    Object2CharMap object_to_char = new Object2CharOpenHashMap();
    Object2DoubleMap object_to_double = new Object2DoubleOpenHashMap();
    Object2FloatMap object_to_float = new Object2FloatOpenHashMap();
    Object2IntMap object_to_int = new Object2IntOpenHashMap();
    Object2LongMap object_to_long = new Object2LongOpenHashMap();
    Object2ObjectMap object_to_object = new Object2ObjectOpenHashMap<>();
    Object2ReferenceMap object_to_reference = new Object2ReferenceOpenHashMap<>();
    Object2ShortMap object_to_short = new Object2ShortOpenHashMap();
    Reference2BooleanMap reference_to_boolean = new Reference2BooleanOpenHashMap();
    Reference2ByteMap reference_to_byte = new Reference2ByteOpenHashMap();
    Reference2CharMap reference_to_char = new Reference2CharOpenHashMap();
    Reference2DoubleMap reference_to_double = new Reference2DoubleOpenHashMap();
    Reference2FloatMap reference_to_float = new Reference2FloatOpenHashMap();
    Reference2IntMap reference_to_int = new Reference2IntOpenHashMap();
    Reference2LongMap reference_to_long = new Reference2LongOpenHashMap();
    Reference2ObjectMap reference_to_object = new Reference2ObjectOpenHashMap<>();
    Reference2ReferenceMap reference_to_reference = new Reference2ReferenceOpenHashMap<>();
    Reference2ShortMap reference_to_short = new Reference2ShortOpenHashMap();
    Short2BooleanMap short_to_boolean = new Short2BooleanOpenHashMap();
    Short2ByteMap short_to_byte = new Short2ByteOpenHashMap();
    Short2CharMap short_to_char = new Short2CharOpenHashMap();
    Short2DoubleMap short_to_double = new Short2DoubleOpenHashMap();
    Short2FloatMap short_to_float = new Short2FloatOpenHashMap();
    Short2IntMap short_to_int = new Short2IntOpenHashMap();
    Short2LongMap short_to_long = new Short2LongOpenHashMap();
    Short2ObjectMap short_to_object = new Short2ObjectOpenHashMap<>();
    Short2ReferenceMap short_to_reference = new Short2ReferenceOpenHashMap<>();
    Short2ShortMap short_to_short = new Short2ShortOpenHashMap();
  }
}
