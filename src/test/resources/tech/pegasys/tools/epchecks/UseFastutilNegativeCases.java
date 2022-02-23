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

import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.booleans.BooleanOpenHashSet;
import it.unimi.dsi.fastutil.booleans.BooleanSet;
import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.bytes.ByteList;
import it.unimi.dsi.fastutil.bytes.ByteOpenHashSet;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.chars.CharArrayList;
import it.unimi.dsi.fastutil.chars.CharList;
import it.unimi.dsi.fastutil.chars.CharOpenHashSet;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleOpenHashSet;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.floats.FloatOpenHashSet;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceList;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortList;
import it.unimi.dsi.fastutil.shorts.ShortOpenHashSet;
import it.unimi.dsi.fastutil.shorts.ShortSet;

import it.unimi.dsi.fastutil.booleans.BooleanStack;
import it.unimi.dsi.fastutil.bytes.ByteStack;
import it.unimi.dsi.fastutil.chars.CharStack;
import it.unimi.dsi.fastutil.doubles.DoubleStack;
import it.unimi.dsi.fastutil.floats.FloatStack;
import it.unimi.dsi.fastutil.ints.IntStack;
import it.unimi.dsi.fastutil.longs.LongStack;
import it.unimi.dsi.fastutil.shorts.ShortStack;

// There are no BooleanLinkedOpenHashSet type.
import it.unimi.dsi.fastutil.bytes.ByteLinkedOpenHashSet;
import it.unimi.dsi.fastutil.chars.CharLinkedOpenHashSet;
import it.unimi.dsi.fastutil.doubles.DoubleLinkedOpenHashSet;
import it.unimi.dsi.fastutil.floats.FloatLinkedOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import it.unimi.dsi.fastutil.shorts.ShortLinkedOpenHashSet;

// There are no Boolean2<>Map types.
import it.unimi.dsi.fastutil.bytes.Byte2BooleanMap;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2ByteMap;
import it.unimi.dsi.fastutil.bytes.Byte2ByteOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2CharMap;
import it.unimi.dsi.fastutil.bytes.Byte2CharOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2DoubleMap;
import it.unimi.dsi.fastutil.bytes.Byte2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2FloatMap;
import it.unimi.dsi.fastutil.bytes.Byte2FloatOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2IntMap;
import it.unimi.dsi.fastutil.bytes.Byte2IntOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2LongMap;
import it.unimi.dsi.fastutil.bytes.Byte2LongOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2ReferenceMap;
import it.unimi.dsi.fastutil.bytes.Byte2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.bytes.Byte2ShortMap;
import it.unimi.dsi.fastutil.bytes.Byte2ShortOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2BooleanMap;
import it.unimi.dsi.fastutil.chars.Char2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2ByteMap;
import it.unimi.dsi.fastutil.chars.Char2ByteOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2CharMap;
import it.unimi.dsi.fastutil.chars.Char2CharOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2DoubleMap;
import it.unimi.dsi.fastutil.chars.Char2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2FloatMap;
import it.unimi.dsi.fastutil.chars.Char2FloatOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2IntMap;
import it.unimi.dsi.fastutil.chars.Char2IntOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2LongMap;
import it.unimi.dsi.fastutil.chars.Char2LongOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2ReferenceMap;
import it.unimi.dsi.fastutil.chars.Char2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2ShortMap;
import it.unimi.dsi.fastutil.chars.Char2ShortOpenHashMap;
import it.unimi.dsi.fastutil.doubles.Double2BooleanMap;
import it.unimi.dsi.fastutil.doubles.Double2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.doubles.Double2ByteMap;
import it.unimi.dsi.fastutil.doubles.Double2ByteOpenHashMap;
import it.unimi.dsi.fastutil.doubles.Double2CharMap;
import it.unimi.dsi.fastutil.doubles.Double2CharOpenHashMap;
import it.unimi.dsi.fastutil.doubles.Double2DoubleMap;
import it.unimi.dsi.fastutil.doubles.Double2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.doubles.Double2FloatMap;
import it.unimi.dsi.fastutil.doubles.Double2FloatOpenHashMap;
import it.unimi.dsi.fastutil.doubles.Double2IntMap;
import it.unimi.dsi.fastutil.doubles.Double2IntOpenHashMap;
import it.unimi.dsi.fastutil.doubles.Double2LongMap;
import it.unimi.dsi.fastutil.doubles.Double2LongOpenHashMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.doubles.Double2ReferenceMap;
import it.unimi.dsi.fastutil.doubles.Double2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.doubles.Double2ShortMap;
import it.unimi.dsi.fastutil.doubles.Double2ShortOpenHashMap;
import it.unimi.dsi.fastutil.floats.Float2BooleanMap;
import it.unimi.dsi.fastutil.floats.Float2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.floats.Float2ByteMap;
import it.unimi.dsi.fastutil.floats.Float2ByteOpenHashMap;
import it.unimi.dsi.fastutil.floats.Float2CharMap;
import it.unimi.dsi.fastutil.floats.Float2CharOpenHashMap;
import it.unimi.dsi.fastutil.floats.Float2DoubleMap;
import it.unimi.dsi.fastutil.floats.Float2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.floats.Float2FloatMap;
import it.unimi.dsi.fastutil.floats.Float2FloatOpenHashMap;
import it.unimi.dsi.fastutil.floats.Float2IntMap;
import it.unimi.dsi.fastutil.floats.Float2IntOpenHashMap;
import it.unimi.dsi.fastutil.floats.Float2LongMap;
import it.unimi.dsi.fastutil.floats.Float2LongOpenHashMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.floats.Float2ReferenceMap;
import it.unimi.dsi.fastutil.floats.Float2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.floats.Float2ShortMap;
import it.unimi.dsi.fastutil.floats.Float2ShortOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ByteMap;
import it.unimi.dsi.fastutil.ints.Int2ByteOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2CharMap;
import it.unimi.dsi.fastutil.ints.Int2CharOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2LongMap;
import it.unimi.dsi.fastutil.ints.Int2LongOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ShortMap;
import it.unimi.dsi.fastutil.ints.Int2ShortOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2BooleanMap;
import it.unimi.dsi.fastutil.longs.Long2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2CharMap;
import it.unimi.dsi.fastutil.longs.Long2CharOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2DoubleMap;
import it.unimi.dsi.fastutil.longs.Long2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2FloatMap;
import it.unimi.dsi.fastutil.longs.Long2FloatOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ShortMap;
import it.unimi.dsi.fastutil.longs.Long2ShortOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ByteMap;
import it.unimi.dsi.fastutil.objects.Object2ByteOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2CharMap;
import it.unimi.dsi.fastutil.objects.Object2CharOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ReferenceMap;
import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ShortMap;
import it.unimi.dsi.fastutil.objects.Object2ShortOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2BooleanMap;
import it.unimi.dsi.fastutil.objects.Reference2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ByteMap;
import it.unimi.dsi.fastutil.objects.Reference2ByteOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2CharMap;
import it.unimi.dsi.fastutil.objects.Reference2CharOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2DoubleMap;
import it.unimi.dsi.fastutil.objects.Reference2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap;
import it.unimi.dsi.fastutil.objects.Reference2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2LongMap;
import it.unimi.dsi.fastutil.objects.Reference2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ShortMap;
import it.unimi.dsi.fastutil.objects.Reference2ShortOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ByteMap;
import it.unimi.dsi.fastutil.shorts.Short2ByteOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2CharMap;
import it.unimi.dsi.fastutil.shorts.Short2CharOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2DoubleMap;
import it.unimi.dsi.fastutil.shorts.Short2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2FloatMap;
import it.unimi.dsi.fastutil.shorts.Short2FloatOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2IntMap;
import it.unimi.dsi.fastutil.shorts.Short2IntOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2LongMap;
import it.unimi.dsi.fastutil.shorts.Short2LongOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ReferenceMap;
import it.unimi.dsi.fastutil.shorts.Short2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ShortMap;
import it.unimi.dsi.fastutil.shorts.Short2ShortOpenHashMap;

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

  static public void usesStack() {
    /* todo */
  }

  static public void usesTreeSet() {
    /* todo */
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
