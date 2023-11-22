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

package io.linlan.commons.script.html;

import io.linlan.commons.core.StringUtils;
import io.linlan.commons.script.abs.UrlConstants;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * html class to provide utils for use
 * Filename:HtmlUtils.java
 * Desc:Html utils include handleUrl, txt2Html, textCut etc.
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * Createtime 2017/6/23 8:26 PM
 * 
 * @version 1.0
 * @since 1.0
 * 
 */
public final class HtmlUtils extends UrlConstants {
    /**
     * constructor of self
     */
    private HtmlUtils(){

    }

    /**
     * trans txt content to html content
     *
     * @param source source txt content
     * @return String
     */
    public static String txt2html(String source) {
        if (StringUtils.isBlank(source)) {
            return source;
        }
        //init sb larger to performance
        StringBuilder sb = new StringBuilder((int) (source.length() * 1.2));
        char c;
        boolean d = false;
        for (int i = 0; i < source.length(); i++) {
            c = source.charAt(i);
            if (c == StringUtils.C_SPACE) {
                if (d) {
                    sb.append(StringUtils.C_SPACE);
                    d = false;
                } else {
                    sb.append(StringUtils.HTML_NBSP);
                    d = true;
                }
            } else {
                d = false;
                switch (c) {
                    case StringUtils.C_AND:
                        sb.append(StringUtils.HTML_AMP);
                        break;
                    case StringUtils.C_LT:
                        sb.append(StringUtils.HTML_LT);
                        break;
                    case StringUtils.C_GT:
                        sb.append(StringUtils.HTML_GT);
                        break;
                    case StringUtils.C_DQ:
                        sb.append(StringUtils.HTML_QUOTE);
                        break;
                    case StringUtils.C_LF:
                        sb.append(StringUtils.HTML_BR);
                        break;
                    default:
                        sb.append(c);
                        break;
                }
            }
        }
        return sb.toString();
    }

    /**
     * cut the text content to implicit show in append in the end
     * the append can use "...", "***", etc.
     *
     * @param source the source text content
     * @param len when less than 256, use one char, greater than 256, use two char
     * @param append the append like ...
     * @return String
     */
    public static String textCut(String source, int len, String append) {
        if (source == null) {
            return null;
        }
        int length = source.length();
        if (length <= len) {
            return source;
        }
        // if the source is all english character the len is double
        int maxCount = len * 2;
        int count = 0;
        int i = 0;
        for (; count < maxCount && i < length; i++) {
            if (source.codePointAt(i) < 256) {
                count++;
            } else {
                count += 2;
            }
        }
        if (i < length) {
            if (count > maxCount) {
                i--;
            }
            if (!StringUtils.isBlank(append)) {
                if (source.codePointAt(i - 1) < 256) {
                    i -= 2;
                } else {
                    i--;
                }
                return source.substring(0, i) + append;
            } else {
                return source.substring(0, i);
            }
        } else {
            return source;
        }
    }

    /**
     * cut source string to html format
     *
     * @param source the source html string to be cut
     * @param len when less than 256, use one char, greater than 256, use two char
     * @param append the append string like ...
     * @return String
     */
    public static String htmlCut(String source, int len, String append) {
//        String text = html2Text(source, len * 2);
        String text = source;
        return textCut(text, len, append);
    }

    /**
     * html trans to text content
     *
     * @param source the source html string
     * @param len
     *            编码小于256的作为一个字符，大于256的作为两个字符。
     * @return the string
     */
//    public static String html2Text(String source, int len) {
//        try {
//            Lexer lexer = new Lexer(source);
//            Node node;
//            StringBuilder sb = new StringBuilder(source.length());
//            while ((node = lexer.nextNode()) != null) {
//                if (node instanceof TextNode) {
//                    sb.append(node.toHtml());
//                }
//                if (sb.length() > len) {
//                    break;
//                }
//            }
//            return sb.toString();
//        } catch (ParserException e) {
//            throw new RuntimeException(e);
//        }
//    }


    /**
     * Removes the，删除或移除 html tag p.p换行
     *
     * @param inputString
     *            the input string
     * @return the string
     */
    public static String removeHtmlTagP(String inputString) {
        if (inputString == null)
            return null;
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        java.util.regex.Matcher m_script;
        Pattern p_style;
        java.util.regex.Matcher m_style;
        Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            htmlStr.replace("</p>", "\n");
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textStr;// 返回文本字符串
    }

    public static String removeHtmlTag(String inputString) {
        if (inputString == null)
            return null;
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        java.util.regex.Matcher m_script;
        Pattern p_style;
        java.util.regex.Matcher m_style;
        Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textStr;// 返回文本字符串
    }

    /**
     * contains 检查字符串中是否包含被搜索的字符串。被搜索的字符串可以使用通配符'*'。.
     *
     * @param str
     *            the getString
     * @param search
     *            the search
     * @return true, if successful
     */
    public static boolean contains(String str, String search) {
        if (StringUtils.isBlank(str) || StringUtils.isBlank(search)) {
            return false;
        }
        String reg = StringUtils.replace(search, "*", ".*");
        Pattern p = Pattern.compile(reg);
        return p.matcher(str).matches();
    }

    /**
     * Contains key string.
     *
     * @param str
     *            the getString
     * @return true, if successful
     */
    public static boolean containsKeyString(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        if (str.contains("'") || str.contains("\"") || str.contains("\r")
                || str.contains("\n") || str.contains("\t")
                || str.contains("\b") || str.contains("\f")) {
            return true;
        }
        return false;
    }

    public static String addCharForString(String str, int strLength,char c,int position) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                if(position==1){
                    //右補充字符c
                    sb.append(c).append(str);
                }else{
                    //左補充字符c
                    sb.append(str).append(c);
                }
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }
    // 将""和'转义
    /**
     * Replace key string.
     *
     * @param str
     *            the getString
     * @return the string
     */
    public static String replaceKeyString(String str) {
        if (containsKeyString(str)) {
            return str.replace("'", "\\'").replace("\"", "\\\"").replace("\r",
                    "\\r").replace("\n", "\\n").replace("\t", "\\t").replace(
                    "\b", "\\b").replace("\f", "\\f");
        } else {
            return str;
        }
    }

    public static String replaceString(String str) {
        if (containsKeyString(str)) {
            return str.replace("'", "\"").replace("\"", "\\\"").replace("\r",
                    "\\r").replace("\n", "\\n").replace("\t", "\\t").replace(
                    "\b", "\\b").replace("\f", "\\f");
        } else {
            return str;
        }
    }
    /**
     * Gets the suffix.
     *
     * @param str
     *            the getString
     * @return the suffix
     */
    public static String getSuffix(String str) {
        int splitIndex = str.lastIndexOf(".");
        return str.substring(splitIndex + 1);
    }

    /**
     * 是否有中文字符.
     *
     * @param s
     *            the s
     * @return true, if successful
     */
    public static boolean hasCn(String s) {
        if (s == null) {
            return false;
        }
        return countCn(s) > s.length();
    }

    /**
     * 获得字符。符合中文习惯。.
     *
     * @param s
     *            the s
     * @param len
     *            the len
     * @return the cn
     */
    public static String getCn(String s, int len) {
        if (s == null) {
            return s;
        }
        int sl = s.length();
        if (sl <= len) {
            return s;
        }
        // 留出一个位置用于…
        len -= 1;
        int maxCount = len * 2;
        int count = 0;
        int i = 0;
        while (count < maxCount && i < sl) {
            if (s.codePointAt(i) < 256) {
                count++;
            } else {
                count += 2;
            }
            i++;
        }
        if (count > maxCount) {
            i--;
        }
        return s.substring(0, i) + "…";
    }

    /**
     * 计算GBK编码的字符串的字节数.
     *
     * @param s
     *            the s
     * @return the int
     */
    public static int countCn(String s) {
        if (s == null) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.codePointAt(i) < 256) {
                count++;
            } else {
                count += 2;
            }
        }
        return count;
    }


    /**
     * html转文本.
     *
     * @param htm
     *            the htm
     * @return the string
     */
    public static String htm2txt(String htm) {
        if (htm == null) {
            return htm;
        }
        htm = htm.replace("&amp;", "&");
        htm = htm.replace("&lt;", "<");
        htm = htm.replace("&gt;", ">");
        htm = htm.replace("&quot;", "\"");
        htm = htm.replace("&nbsp;", " ");
        htm = htm.replace("<br/>", "\n");
        return htm;
    }

    /**
     * 替换字符串.
     *
     * @param sb
     *            the sb
     * @param what
     *            the what
     * @param with
     *            the with
     * @return the string builder
     */
    public static StringBuilder replace(StringBuilder sb, String what,
            String with) {
        int pos = sb.indexOf(what);
        while (pos > -1) {
            sb.replace(pos, pos + what.length(), with);
            pos = sb.indexOf(what);
        }
        return sb;
    }

    /**
     * 替换字符串.
     *
     * @param s
     *            the s
     * @param what
     *            the what
     * @param with
     *            the with
     * @return the string
     */
    public static String replace(String s, String what, String with) {
        return replace(new StringBuilder(s), what, with).toString();
    }


    /**
     * Convenience method to return a Collection as a CSV String. E.g. useful
     * for toString() implementations.
     *
     * @param coll
     *            Collection to display
     */
    public static String collectionToDelimitedString(Collection<?> coll,
            String delim) {
        return collectionToDelimitedString(coll, delim, "", "");
    }

    /**
     * Convenience method to return a Collection as a delimited (e.g. CSV)
     * String. E.g. useful for toString() implementations.
     *
     * @param coll
     *            Collection to display
     * @param delim
     *            delimiter to use (probably a ",")
     * @param prefix
     *            string to start each element with
     * @param suffix
     *            string to end each element with
     */
    public static String collectionToDelimitedString(Collection<?> coll,
            String delim, String prefix, String suffix) {
        if (coll == null)
            return "";

        StringBuffer sb = new StringBuffer();
        Iterator<?> it = coll.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (i > 0)
                sb.append(delim);
            sb.append(prefix).append(it.next()).append(suffix);
            i++;
        }
        return sb.toString();
    }

    /**
     * 全角-->半角.
     *
     * @param qjStr
     *            the qj getString
     * @return the string
     */
    public String Q2B(String qjStr) {
        String outStr = "";
        String Tstr = "";
        byte[] b = null;
        for (int i = 0; i < qjStr.length(); i++) {
            try {
                Tstr = qjStr.substring(i, i + 1);
                b = Tstr.getBytes("unicode");
            } catch (java.io.UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (b[3] == -1) {
                b[2] = (byte) (b[2] + 32);
                b[3] = 0;
                try {
                    outStr = outStr + new String(b, "unicode");
                } catch (java.io.UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else
                outStr = outStr + Tstr;
        }
        return outStr;
    }



    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(String args[]) {
        System.out.println(replaceKeyString("&nbsp;\r" + "</p>"));
    }
}
