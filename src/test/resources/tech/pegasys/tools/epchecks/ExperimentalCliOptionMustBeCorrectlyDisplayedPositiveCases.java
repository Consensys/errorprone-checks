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

import picocli.CommandLine;

public class ExperimentalCliOptionMustBeCorrectlyDisplayedPositiveCases {

  // BUG: Diagnostic contains:  Experimental options must be hidden and not present in the
  // BesuCommand class.
  @CommandLine.Option(
      hidden = false,
      names = {"--Xexperimental"})
  private String experimental = "";

  // BUG: Diagnostic contains:  Experimental options must be hidden and not present in the
  // BesuCommand class.
  @CommandLine.Option(names = {"--Xexperimental2"})
  private String experimental2 = "";

  private class BesuCommand {

    // BUG: Diagnostic contains:  Experimental options must be hidden and not present in the
    // BesuCommand class.
    @CommandLine.Option(names = {"--XexperimentalInBesuCommandClass"})
    private String experimentalInBesuCommandClass = "";
  }
}