/**
 * Copyright 2020-2023 the original author or Linlan authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.linlan.commons.core.abs;

/**
 * the abstract class of Hex constants define
 * Filename:HexConstants.java
 * Desc:the constants of hex, in this class to define constants
 *
 * CreateTime:2020-07-10 8:42 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class HexConstants {

    /**
     * Used to build output as Hex
     * the output is digits and lower letters
     */
    public static final char[] DIGITS_LOWER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Used to build output as Hex
     * the output is digits and upperletters
     */
    public static final char[] DIGITS_UPPER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

}
