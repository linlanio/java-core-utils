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


import io.linlan.commons.core.CollectionUtils;

import java.util.Set;

/**
 * the abstract class of Regex constants define
 * Filename:RegexConstants.java
 * Desc:the constants of Regex, in this class to define constants
 * the regex string constants
 *
 * CreateTime:2020-07-16 10:21 AM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public abstract class RegexConstants {

    /**
     * the general character pattern include english letter, number, _
     */
    public final static String GENERAL_REGEX = "^\\w+$";

    /**
     * the letter of number pattern
     */
    public final static String NUMBERS_REGEX = "\\d+";

    /**
     * The index of a capturing group in this matcher's pattern
     */
    public final static String GROUP_VAR_REGEX = "\\$(\\d+)";

    /**
     * the money pattern */
    public final static String MONEY_REGEX = "^(\\d+(?:\\.\\d+)?)$";

    /**
     * the email address pattern
     */
    public final static String EMAIL_REGEX = "(\\w|.)+@\\w+(\\.\\w+){1,2}";

    /**
     * the mobile phone number pattern
     */
    public final static String MOBILE_REGEX = "1\\d{10}";

    /**
     * the identity card no pattern
     */
    public final static String IDCARD_NO_REGEX = "[1-9]\\d{5}[1-2]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}(\\d|X|x)";

    /**
     * the post code pattern
     */
    public final static String POST_CODE_REGEX = "\\d{6}";

    /**
     * the birthday pattern
     */
    public final static String BIRTHDAY_REGEX = "^(\\d{2,4})([/\\-\\.年]?)(\\d{1,2})([/\\-\\.月]?)(\\d{1,2})日?$";

    /**
     * the URL path pattern
     */
    public final static String URL_REGEX = "(https://|http://|rtmp://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * the pattern include english letter, number, _ and chinese
     */
    public final static String GENERAL_WITH_CHINESE_REGEX = "^[\u4E00-\u9FFF\\w]+$";

    /**
     * the single chinese word pattern
     */
    public final static String SINGLE_CHINESE_REGEX = "[\u4E00-\u9FFF]";

    /**
     * the chinese words pattern
     */
    public final static String MORE_CHINESE_REGEX = SINGLE_CHINESE_REGEX + "+";

    public final static String CHINESE_REGEX = "^" + SINGLE_CHINESE_REGEX + "+$";

    /**
     * the UUID pattern
     */
    public final static String UUID_REGEX = "^[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}$";

    /**
     * the UUID 32 without - pattern
     */
    public final static String UUID32_REGEX = "^[0-9a-z]{32}$";

    /**
     * the car plate number pattern of china
     */
    public final static String PLATE_NO_REGEX = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";

    /**
     * the IP v4 pattern
     */
    public final static String IPV4_REGEX = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";

    /**
     * the IP v6 pattern
     */
    public final static String IPV6_REGEX = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";

    /**
     * the MAC address pattern
     */
    public final static String MAC_REGEX = "((?:[A-F0-9]{1,2}[:-]){5}[A-F0-9]{1,2})|(?:0x)(\\d{12})(?:.+ETHER)";

    /**
     * the set of escape character
     */
    public final static Set<Character> RE_KEYS = CollectionUtils.newHashSet(new Character[]{'$', '(', ')', '*', '+', '.', '[', ']', '?', '\\', '^', '{', '}', '|'});

}
