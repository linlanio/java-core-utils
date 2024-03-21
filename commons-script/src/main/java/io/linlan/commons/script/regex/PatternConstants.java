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
package io.linlan.commons.script.regex;


import java.util.regex.Pattern;

/**
 * the constant of regex pattern
 * Filename:PatternConstants.java
 * Desc: the constant of regex pattern in Pattern class
 *
 * CreateTime:2020-07-11 10:57 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class PatternConstants extends RegexConstants {
    /**
     * the general character pattern include english letter, number, _
     */
    public final static Pattern GENERAL = Pattern.compile(GENERAL_REGEX);

    /**
     * the letter of number pattern
     */
    public final static Pattern NUMBERS = Pattern.compile(NUMBERS_REGEX);

    /**
     * The index of a capturing group in this matcher's pattern
     */
    public final static Pattern GROUP_VAR = Pattern.compile(GROUP_VAR_REGEX);

    /**
     * the money pattern */
    public final static Pattern MONEY = Pattern.compile(MONEY_REGEX);

    /**
     * the email address pattern
     */
    public final static Pattern EMAIL = Pattern.compile(EMAIL_REGEX);

    /**
     * the mobile phone number pattern
     */
    public final static Pattern MOBILE = Pattern.compile(MOBILE_REGEX);

    /**
     * the identity card no pattern
     */
    public final static Pattern IDCARD_NO = Pattern.compile(IDCARD_NO_REGEX);

    /**
     * the post code pattern
     */
    public final static Pattern POST_CODE = Pattern.compile(POST_CODE_REGEX);

    /**
     * the birthday pattern
     */
    public final static Pattern BIRTHDAY = Pattern.compile(BIRTHDAY_REGEX);

    /**
     * the URL path pattern
     */
    public final static Pattern URL = Pattern.compile(URL_REGEX);

    /**
     * the pattern include english letter, number, _ and chinese
     */
    public final static Pattern GENERAL_WITH_CHINESE = Pattern.compile(GENERAL_WITH_CHINESE_REGEX);

    /**
     * the single chinese word pattern
     */
    public final static Pattern CHINESE = Pattern.compile(CHINESE_REGEX);

    /**
     * the chinese words pattern
     */
    public final static Pattern MORE_CHINESE = Pattern.compile(MORE_CHINESE_REGEX);

    /**
     * the UUID pattern
     */
    public final static Pattern UUID = Pattern.compile(UUID_REGEX);

    /**
     * the UUID 32 without - pattern
     */
    public final static Pattern UUID32 = Pattern.compile(UUID32_REGEX);

    /**
     * the car plate number pattern of china
     */
    public final static Pattern PLATE_NO = Pattern.compile(PLATE_NO_REGEX);

    /**
     * the IP v4 pattern
     */
    public final static Pattern IPV4 = Pattern.compile(IPV4_REGEX);

    /**
     * the IP v6 pattern
     */
    public final static Pattern IPV6 = Pattern.compile(IPV6_REGEX);

    /**
     * the MAC address pattern
     */
    public final static Pattern MAC = Pattern.compile(MAC_REGEX, Pattern.CASE_INSENSITIVE);
}
