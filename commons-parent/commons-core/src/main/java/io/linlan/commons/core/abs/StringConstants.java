/**
 * Copyright 2015 the original author or Linlan authors.
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
 * the abstract class of String constants define
 * Filename:StringConstants.java
 * Desc:the constants of string, in this class to define constants
 * const for character, such as " \ .
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * Createtime 2017/6/23 15:45
 *
 * @version 1.0
 * @since 1.0
 *
 */
public abstract class StringConstants {
    /**
     * char format space | 空格
     */
    public static final char C_SPACE = ' ';

    /**
     * char format dot | 点
     */
    public static final char C_DOT = '.';

    /**
     * char format slash | 路径分隔符
     */
    public static final char C_SLASH = '/';

    /**
     * char format backslash | 回退键
     */
    public static final char C_BACKSLASH = '\\';

    /**
     * char format and | 连接符
     */
    public static final char C_AND = '&';
    /**
     * char format less than | 小于
     */
    public static final char C_LT = '<';
    /**
     * char format great than | 大于
     */
    public static final char C_GT = '>';
    /**
     * char format double quota | 双引号
     */
    public static final char C_DQ = '"';
    /**
     * char format CR
     */
    public static final char C_CR = '\r';
    /**
     * char format LF | 换行
     */
    public static final char C_LF = '\n';
    /**
     * char format UNDERLINE | 下划线
     */
    public static final char C_UNDERLINE = '_';
    /**
     * char format MINUS | 减号
     */
    public static final char C_MINUS = '-';
    /**
     * char format COMMA | 逗号
     */
    public static final char C_COMMA = ',';
    /**
     * char format EQUAL | 等于
     */
    public static final char C_EQUAL = '=';
    /**
     * char format DELIM_START | 左大括号
     */
    public static final char C_DELIM_START = '{';
    /**
     * char format DELIM_END | 右大括号
     */
    public static final char C_DELIM_END = '}';
    /**
     * char format BRACKET_START | 左中括号
     */
    public static final char C_BRACKET_START = '[';
    /**
     * char format BRACKET_END | 右中括号
     */
    public static final char C_BRACKET_END = ']';
    /**
     * char format COLON | 冒号
     */
    public static final char C_COLON = ':';

    /**
     * string format space
     */
    public static final String SPACE = " ";

    /**
     * tab of string
     */
    public static final String TAB = "    ";

    /**
     * dot of string
     */
    public static final String DOT = ".";
    public static final String DOUBLE_DOT = "..";

    /**
     * slash of string
     */
    public static final String SLASH = "/";

    /**
     * back slash of string
     */
    public static final String BACKSLASH = "\\";

    public static final String AND = "&";
    public static final String EMPTY = "";
    public static final String CR = "\r";
    public static final String LF = "\n";
    public static final String CRLF = "\r\n";
    public static final String UNDERLINE = "_";
    public static final String COMMA = ",";
    public static final String DELIM_START = "{";
    public static final String DELIM_END = "}";
    public static final String BRACKET_START = "[";
    public static final String BRACKET_END = "]";
    public static final String COLON = ":";

    public static final String HTML_NBSP = "&nbsp;";
    public static final String HTML_AMP = "&amp";
    public static final String HTML_QUOTE = "&quot;";
    public static final String HTML_LT = "&lt;";
    public static final String HTML_GT = "&gt;";
    public static final String HTML_BR = "<br/>";



    public static final String EMPTY_JSON = "{}";
    /**
     * the max split limit int, use 16
     */
    public static final int MAX_SPLIT_LIMIT = 16;
    public static final String CLASSPATH_PREFIX = "classpath:";

}
