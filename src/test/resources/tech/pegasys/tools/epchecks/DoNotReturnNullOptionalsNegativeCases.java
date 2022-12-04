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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class DoNotReturnNullOptionalsNegativeCases {

  public interface allInterfacesAreValid {
    public Optional<Long> ExpectToBeOverridden();
  }

  public DoNotReturnNullOptionalsNegativeCases() {}

  public Optional<Long> doesNotReturnNull() {
    return Optional.of(3L);
  }

  @Nullable
  public Optional<Long> returnsNullButAnnotatedWithNullable() {
    return Optional.empty();
  }

  public Optional<List<Integer>> anonFuncReturnsNull() {
    final List<Integer> list = List.of(1, 2, 3);
    return Optional.of(
        list.stream().map(n -> {
          if (n == 2) {
            return null;
          }
          return n;
        }).collect(Collectors.toList()));
  }

  public Optional<Long> returnsVarInConditional(final boolean flag) {
    Optional<Long> var = flag ? Optional.of(2L) : Optional.of(3L);
    return var;
  }
}
