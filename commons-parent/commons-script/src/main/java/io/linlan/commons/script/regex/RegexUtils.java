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

import io.linlan.commons.core.ObjectUtils;
import io.linlan.commons.core.StringUtils;
import io.linlan.commons.script.ScriptException;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.ws.Holder;


/**
 * Regex class to provide utils for use
 * Filename:RegexUtils.java
 * Desc:Regex utils include get, extract, remove, valid methods
 * the defined regex expression
 *
 * CreateTime:2020-07-11 10:56 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */

public final class RegexUtils extends RegexConstants {

    /**
     * the sequence of char values to be replaced in group
     */
    public static final String GROUP_PRE = "$";

    /**
     * constructor of self
     */
    private RegexUtils() {

    }

    /**
     * get the match string of source content with input regex in group index
     *
     * @param regex the regex to match
     * @param content the matched source content
     * @param groupIndex the group index
     * @return String the string of match, if not match, return null
     */
    public static String get(String regex, String content, int groupIndex) {
        if(StringUtils.isBlank(content) || StringUtils.isBlank(regex)){
            return null;
        }

        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        return get(pattern, content, groupIndex);
    }

    /**
     * get the match string of source content with input regex in group index
     *
     * @param pattern the Pattern to match {@link Pattern}
     * @param content the matched source content
     * @param groupIndex the group index
     * @return String the string of match, if not match, return null
     */
    public static String get(Pattern pattern, String content, int groupIndex) {
        if(StringUtils.isBlank(content) || null == pattern){
            return null;
        }

        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(groupIndex);
        }
        return null;
    }

    /**
     * get the match string of source content
     * with input regex by input template, $1 means group1
     * Example:<br>
     * regex:(.*?)年(.*?)月
     * template:$1-$2
     *
     * @param regex the regex to match
     * @param content the matched source content
     * @param template the template of match string,
     *                 $1 means group1's content, and so on
     * @return String the string of match, if not match, return null
     */
    public static String getInTpl(String regex, String content, String template) {
        if(StringUtils.isBlank(content) || StringUtils.isBlank(regex) || StringUtils.isBlank(template)){
            return null;
        }

        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        return getInTpl(pattern, content, template);
    }

    /**
     * get the match string of source content
     * with input pattern by input template, $1 means group1
     * Example:<br>
     * pattern:the Pattern (.*?)年(.*?)月
     * template:$1-$2
     *
     * @param pattern the Pattern to match {@link Pattern}
     * @param content the matched source content
     * @param template the template of match string,
     *                 $1 means group1's content, and so on
     * @return String the string of match, if not match, return null
     */
    public static String getInTpl(Pattern pattern, String content, String template) {
        if(StringUtils.isBlank(content) || null == pattern || StringUtils.isBlank(template)){
            return null;
        }

        HashSet<String> varNums = findAll(PatternConstants.GROUP_VAR, template, 1, new HashSet<>());

        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            for (String var : varNums) {
                int group = Integer.parseInt(var);
                template = template.replace(GROUP_PRE + var, matcher.group(group));
            }
            return template;
        }
        return null;
    }


    /**
     * get the match string of source content
     * with input regex by input template, $1 means group1
     *
     * @param regex the regex to match
     * @param content the matched source content
     * @return String the string of match, if not match, return null
     */
    public static String getLatter(String regex, String content) {
        if(StringUtils.isBlank(content) || StringUtils.isBlank(regex)){
            return content;
        }

        Matcher matcher = Pattern.compile(regex, Pattern.DOTALL).matcher(content);
        if (matcher.find()) {
            return StringUtils.substring(content, matcher.end(), content.length());
        }
        return content;
    }

    /**
     * get the match string of source content
     * with input regex by input template, $1 means group1
     * Example:<br>
     * pattern:(.*?)年(.*?)月
     * template:$1-$2
     *
     * @param regex the regex to match
     * @param contentHolder the matched Holder, the source will match by template
     * @param template the template of match string,
     *                 $1 means group1's content, and so on
     * @return String the string of match, if not match, return null
     */
    public static String getLatter(String regex, Holder<String> contentHolder, String template) {
        if(null == contentHolder || StringUtils.isBlank(regex) || StringUtils.isBlank(template)){
            return null;
        }

        final Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        return getLatter(pattern, contentHolder, template);
    }

    /**
     * get the match string of source content with input regex by input template
     * with latter part without the pre string, $1 means group1
     * Example:<br>
     * pattern:(.*?)年(.*?)月
     * template:$1-$2
     *
     * @param pattern the Pattern to match {@link Pattern}
     * @param contentHolder the matched Holder, the source will match by template
     * @param template the template of match string,
     *                 $1 means group1's content, and so on
     * @return String the string of match, without the prefix part
     *                if match, return the latter part, if not match, return null
     */
    public static String getLatter(Pattern pattern, Holder<String> contentHolder, String template) {
        if(null == contentHolder || null == pattern || StringUtils.isBlank(template)){
            return null;
        }

        HashSet<String> varNums = findAll(PatternConstants.GROUP_VAR, template, 1, new HashSet<>());

        final String content = contentHolder.value;
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            for (String var : varNums) {
                int group = Integer.parseInt(var);
                template = template.replace(GROUP_PRE + var, matcher.group(group));
            }
            contentHolder.value = StringUtils.substring(content, matcher.end(), content.length());
            return template;
        }
        return null;
    }



    /**
     * replace the first match with "" of content with input regex
     *
     * @param regex the regex to match
     * @param content the matched source content
     * @return String the remain string
     */
    public static String replaceFirst(String regex, String content) {
        if(StringUtils.hasBlank(regex, content)){
            return content;
        }

        return replaceFirst(Pattern.compile(regex, Pattern.DOTALL), content);
    }

    /**
     * replace the first match with "" of content with input pattern
     *
     * @param pattern the Pattern to match {@link Pattern}
     * @param content the matched source content
     * @return String the remain string
     */
    public static String replaceFirst(Pattern pattern, String content) {
        if(null == pattern || StringUtils.isBlank(content)){
            return content;
        }

        return pattern.matcher(content).replaceFirst(StringUtils.EMPTY);
    }

    /**
     * replace the all match with "" of content with input regex
     *
     * @param regex the regex to match
     * @param content the matched source content
     * @return String the remain string
     */
    public static String replaceAll(String regex, String content) {
        if(StringUtils.hasBlank(regex, content)){
            return content;
        }

        return replaceAll(Pattern.compile(regex, Pattern.DOTALL), content);
    }

    /**
     * replace the all match with "" of content with input pattern
     *
     * @param pattern the Pattern to match {@link Pattern}
     * @param content the matched source content
     * @return String the remain string
     */
    public static String replaceAll(Pattern pattern, String content) {
        if(null == pattern || StringUtils.isBlank(content)){
            return content;
        }

        return pattern.matcher(content).replaceAll(StringUtils.EMPTY);
    }


    /**
     * replace the all match with "" of content with input pattern by template
     *
     * @param regex the regex to match
     * @param content the matched source content
     * @param template the template of match string,
     *                 $1 means group1's content, and so on
     * @return String the remain string
     */
    public static String replaceAll(String regex, String content, String template) {
        final Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        return replaceAll(pattern, content, template);
    }

    /**
     * replace the all match with "" of content with input pattern by template
     *
     * @param pattern the Pattern to match {@link Pattern}
     * @param content the matched source content
     * @param template the template of match string,
     *                 $1 means group1's content, and so on
     * @return String the remain string
     */
    public static String replaceAll(Pattern pattern, String content, String template) {
        if(StringUtils.isEmpty(content)){
            return content;
        }

        final Matcher matcher = pattern.matcher(content);
        boolean result = matcher.find();
        if (result) {
            final Set<String> varNums = findAll(PatternConstants.GROUP_VAR, template, 1, new HashSet<>());
            final StringBuffer sb = new StringBuffer();
            do {
                String replacement = template;
                for (String var : varNums) {
                    int group = Integer.parseInt(var);
                    replacement = replacement.replace(GROUP_PRE + var, matcher.group(group));
                }
                matcher.appendReplacement(sb, escape(replacement));
                result = matcher.find();
            } while (result);
            matcher.appendTail(sb);
            return sb.toString();
        }
        return content;
    }


    /**
     * find all the match string of source content with input regex in group index
     *
     * @param regex the regex to match
     * @param content the matched source content
     * @param groupIndex the group index
     * @return List<String> the list string of match, if not match, return null
     */
    public static List<String> findAll(String regex, String content, int groupIndex) {
        return findAll(regex, content, groupIndex, new ArrayList<>());
    }

    /**
     * find all the match string of source content with input regex in group index
     * <p> If the match was successful but the group specified failed to match
     * any part of the input sequence, then <tt>null</tt> is returned. Note
     * that some groups, for example <tt>(a*)</tt>, match the empty string.
     * This method will return the empty string when such a group successfully
     * matches the empty string in the input.  </p>
     *
     * @param regex the regex to match
     * @param content the matched source content
     * @param groupIndex the group index
     * @param <T> the type of collection
     * @return List<String> the list string of match, if not match, return null
     */
    public static <T extends Collection<String>> T findAll(String regex, String content, int groupIndex, T collection) {
        if(StringUtils.isBlank(regex)){
            return null;
        }

        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        return findAll(pattern, content, groupIndex, collection);
    }

    /**
     * find all the match string of source content with input regex in group index
     * <p> If the match was successful but the group specified failed to match
     * any part of the input sequence, then <tt>null</tt> is returned. Note
     * that some groups, for example <tt>(a*)</tt>, match the empty string.
     * This method will return the empty string when such a group successfully
     * matches the empty string in the input.  </p>
     *
     * @param pattern the Pattern to match {@link Pattern}
     * @param content the matched source content
     * @param groupIndex the group index
     * @return List<String> the list string of match, if not match, return null
     */
    public static List<String> findAll(Pattern pattern, String content, int groupIndex) {
        return findAll(pattern, content, groupIndex, new ArrayList<>());
    }

    /**
     * find all the match string of source content with input regex in group index
     * <p> If the match was successful but the group specified failed to match
     * any part of the input sequence, then <tt>null</tt> is returned. Note
     * that some groups, for example <tt>(a*)</tt>, match the empty string.
     * This method will return the empty string when such a group successfully
     * matches the empty string in the input.  </p>
     *
     * @param pattern the Pattern to match {@link Pattern}
     * @param content the matched source content
     * @param groupIndex the group index
     * @param <T> the type of collection
     * @return List<String> the list string of match, if not match, return null
     */
    public static <T extends Collection<String>> T findAll(Pattern pattern, String content, int groupIndex, T collection) {
        if(null == pattern || null == content){
            return null;
        }

        if(null == collection){
            throw new NullPointerException("Null collection param provided!");
        }

        Matcher matcher = pattern.matcher(content);
        while(matcher.find()){
            collection.add(matcher.group(groupIndex));
        }
        return collection;
    }

    /**
     * count the match string of source content with input regex
     *
     * @param regex the regex to match
     * @param content the matched source content
     * @return the count of match
     */
    public static int count(String regex, String content){
        if(StringUtils.isBlank(regex)){
            return 0;
        }

        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        return count(pattern, content);
    }

    /**
     * count the match string of source content with input regex
     *
     * @param pattern the pattern to match
     * @param content the matched source content
     * @return the count of match
     */
    public static int count(Pattern pattern, String content){
        if(null == pattern || null == content){
            return 0;
        }

        int count = 0;
        Matcher matcher = pattern.matcher(content);
        while(matcher.find()){
            count++;
        }

        return count;
    }


    /**
     * if it is matched of source content with input regex
     *
     * @param regex the regex to match
     * @param content the matched source content
     * @return if matched true, else false, if content null, then false
     */
    public static boolean isMatch(String regex, String content) {
        if(content == null) {
            //if the content null
            return false;
        }

        if(StringUtils.isEmpty(regex)) {
            //if regex is empty
            return true;
        }

        return Pattern.matches(regex, content);
    }


    /**
     * if it is matched of source content with input regex
     *
     * @param pattern the pattern to match
     * @param content the matched source content
     * @return if matched true, else false, if content null, then false
     */
    public static boolean isMatch(Pattern pattern, String content) {
        if(content == null || pattern == null) {
            //if the content null or pattern null
            return false;
        }
        return pattern.matcher(content).matches();
    }

    /**
     *
     * valid if it is matched of source content with input regex<br>
     * if not match throw error message
     *
     * @param regex the regex to match
     * @param content the matched source content
     * @param error the error message
     * @throws ScriptException 验证异常
     */
    public static void validateMatchRegex(String regex, String content, String error) throws ScriptException {
        if (false == isMatch(regex, content)) {
            throw new ScriptException(error);
        }
    }

    /**
     * escape the source content with character set
     *
     * @param content the matched source content
     * @return String after escape with character
     */
    public static String escape(String content) {
        if(StringUtils.isBlank(content)){
            return content;
        }

        final StringBuilder builder = new StringBuilder();
        char current;
        for(int i = 0; i < content.length(); i++) {
            current = content.charAt(i);
            if(RE_KEYS.contains(current)) {
                builder.append('\\');
            }
            builder.append(current);
        }
        return builder.toString();
    }


    /**
     * valid the object source is empty<br>
     * @see ObjectUtils#isEmpty(Object)
     *
     * @param source the source object
     * @return if empty true, else false
     */
    public static boolean isEmpty(Object source) {
        return ObjectUtils.isEmpty(source);
    }

    /**
     * valid the object source is not empty<br>
     * @see ObjectUtils#isEmpty(Object)
     *
     * @param source the source object
     * @return if not empty true, else false
     */
    public static boolean isNotEmpty(Object source) {
        return false == isEmpty(source);
    }


    /**
     * valid the source object if it is empty
     * if the source object is empty, show error message<br>
     *
     * @param source the source object
     * @param error the error message
     * @throws ScriptException
     */
    public static void validateNotEmpty(Object source, String error) throws ScriptException {
        if (isEmpty(source)) {
            throw new ScriptException(error);
        }
    }

    /**
     * valid the source object1 equal to object2<br>
     * if object1 is null, object2 is null, then true
     *
     * @param o1 object1
     * @param o2 object2
     * @return if equal true, else false
     *         if obj1 is null, obj2 is null, then true
     */
    public static boolean equal(Object o1, Object o2) {
        return ObjectUtils.nullSafeEquals(o1, o2);
    }

    
    /**
     * valid the source object1 equal to object2
     * if the source object1 is equal to object2, show error message<br>
     *
     * @param o1 object1
     * @param o2 object2
     * @param error the error message
     * @throws ScriptException
     */
    public static void validateNotEqual(Object o1, Object o2, String error) throws ScriptException {
        if (equal(o1, o2)) {
            throw new ScriptException(error);
        }
    }


    /**
     * valid the source, if the source is general true, else false
     *
     * @param source the source string of regex
     * @return if the source is {@link PatternConstants#GENERAL} true, else false
     */
    public static boolean isGeneral(String source) {
        return isMatch(PatternConstants.GENERAL, source);
    }

    /**
     * valid the source string is general
     * if the source string is not general, show error message<br>
     *
     * @param source the source string of regex
     * @param error the error message
     * @throws ScriptException
     */
    public static void validateGeneral(String source, String error) throws ScriptException {
        if(false == isGeneral(source)){
            throw new ScriptException(error);
        }
    }

    /**
     * valid the source, if the source is general true, else false
     *
     * @param source the source string of regex
     * @param min the min length, if <0 then use 0
     * @param max the max length, if <=0 then not limit
     * @return if the source is general true, else false
     */
    public static boolean isGeneral(String source, int min, int max) {
        String reg = "^\\w{" + min + "," + max + "}$";
        if (min < 0) {
            min = 0;
        }
        if (max <= 0) {
            reg = "^\\w{" + min + ",}$";
        }
        return isMatch(reg, source);
    }

    /**
     * valid the source string is general
     * if the source string is not general, show error message<br>
     *
     * @param source the source string of regex
     * @param min the min length, if <0 then use 0
     * @param max the max length, if <=0 then not limit
     * @param error the error message
     * @throws ScriptException
     */
    public static void validateGeneral(String source, int min, int max, String error) throws ScriptException {
        if(false == isGeneral(source, min, max)){
            throw new ScriptException(error);
        }
    }


    /**
     * valid the source, if the source is number true, else false
     *
     * @param source the source string of regex
     * @return if the source is {@link PatternConstants#NUMBERS} true, else false
     */
    public static boolean isNumber(String source) {
        if (StringUtils.isBlank(source)) {
            return false;
        }
        return isMatch(PatternConstants.NUMBERS, source);
    }

    /**
     * valid the source string is number
     * if the source string is not number, show error message<br>
     *
     * @param source the source string of regex
     * @param error the error message
     * @throws ScriptException
     */
    public static void validateNumbers(String source, String error) throws ScriptException {
        if(false == isNumber(source)){
            throw new ScriptException(error);
        }
    }

    /**
     * valid the source, if the source is money true, else false
     *
     * @param source the source string of regex
     * @return if the source is {@link PatternConstants#MONEY} true, else false
     */
    public static boolean isMoney(String source) {
        return isMatch(PatternConstants.MONEY, source);
    }


    /**
     * valid the source string is money
     * if the source string is not money, show error message<br>
     *
     * @param source the source string of regex
     * @param error the error message
     * @throws ScriptException
     */
    public static void validateMoney(String source, String error) throws ScriptException {
        if(false == isMoney(source)){
            throw new ScriptException(error);
        }
    }

    /**
     * valid the source, if the source is postcode true, else false
     *
     * @param source the source string of regex
     * @return if the source is {@link PatternConstants#POST_CODE} true, else false
     */
    public static boolean isPostCode(String source) {
        return isMatch(PatternConstants.POST_CODE, source);
    }

    /**
     * valid the source string is postcode
     * if the source string is not postcode, show error message<br>
     *
     * @param source the source string of regex
     * @param error the error message
     * @throws ScriptException
     */
    public static void validatePostCode(String source, String error) throws ScriptException {
        if(false == isPostCode(source)){
            throw new ScriptException(error);
        }
    }

    /**
     * valid the source, if the source is email true, else false
     *
     * @param source the source string of regex
     * @return if the source is {@link PatternConstants#EMAIL} true, else false
     */
    public static boolean isEmail(String source) {
        return isMatch(PatternConstants.EMAIL, source);
    }


    /**
     * valid the source string is email
     * if the source string is not email, show error message<br>
     *
     * @param source the source string of regex
     * @param error the error message
     * @throws ScriptException
     */
    public static void validateEmail(String source, String error) throws ScriptException {
        if(false == isEmail(source)){
            throw new ScriptException(error);
        }
    }

    /**
     * valid the source, if the source is mobile true, else false
     *
     * @param source the source string of regex
     * @return if the source is {@link PatternConstants#MOBILE} true, else false
     */
    public static boolean isMobile(String source) {
        return isMatch(PatternConstants.MOBILE, source);
    }

    /**
     * valid the source string is mobile
     * if the source string is not mobile, show error message<br>
     *
     * @param source the source string of regex
     * @param error the error message
     * @throws ScriptException
     */
    public static void validateMobile(String source, String error) throws ScriptException {
        if(false == isMobile(source)){
            throw new ScriptException(error);
        }
    }

    /**
     * valid the source, if the source is idcardNo true, else false
     *
     * @param source the source string of regex
     * @return if the source is {@link PatternConstants#IDCARD_NO} true, else false
     */
    public static boolean isIdcardNo(String source) {
        return isMatch(PatternConstants.IDCARD_NO, source);
    }

    /**
     * valid the source string is idcard no
     * if the source string is not idcard no, show error message<br>
     *
     * @param source the source string of regex
     * @param error the error message
     * @throws ScriptException
     */
    public static void validateIdcardNo(String source, String error) throws ScriptException {
        if(false == isIdcardNo(source)){
            throw new ScriptException(error);
        }
    }



    /**
     * valid the source, if the source is ipv4 true, else false
     *
     * @param source the source string of regex
     * @return if the source is {@link PatternConstants#IPV4} true, else false
     */
    public static boolean isIpv4(String source) {
        return isMatch(PatternConstants.IPV4, source);
    }

    /**
     * valid the source string is ipv4
     * if the source string is not ipv4, show error message<br>
     *
     * @param source the source string of regex
     * @param error the error message
     * @throws ScriptException
     */
    public static void validateIpv4(String source, String error) throws ScriptException {
        if(false == isIpv4(source)){
            throw new ScriptException(error);
        }
    }

    /**
     * valid the source, if the source is plateNo true, else false
     *
     * @param source the source string of regex
     * @return if the source is {@link PatternConstants#PLATE_NO} true, else false
     */
    public static boolean isPlateNo(String source) {
        return isMatch(PatternConstants.PLATE_NO, source);
    }

    /**
     * valid the source string is plateno
     * if the source string is not plateno, show error message<br>
     *
     * @param source the source string of regex
     * @param error the error message
     * @throws ScriptException
     */
    public static void validatePlateNo(String source, String error) throws ScriptException {
        if(false == isPlateNo(source)){
            throw new ScriptException(error);
        }
    }

    /**
     * valid the source, if the source is url true, else false
     *
     * @param source the source string of regex
     * @return if the source is {@link PatternConstants#URL} true, else false
     */
    public static boolean isUrl(String source) {
        try {
            new java.net.URL(source);
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }

    /**
     * valid the source string is url
     * if the source string is not url, show error message<br>
     *
     * @param source the source string of regex
     * @param error the error message
     * @throws ScriptException
     */
    public static void validateUrl(String source, String error) throws ScriptException {
        if(false == isUrl(source)){
            throw new ScriptException(error);
        }
    }

    /**
     * valid the source, if the source is Chinese true, else false
     *
     * @param source the source string of regex
     * @return if the source is {@link PatternConstants#CHINESE} true, else false
     */
    public static boolean isChinese(String source) {
        return isMatch(CHINESE_REGEX, source);
    }

    /**
     * valid the source string is chinese
     * if the source string is not chinese, show error message<br>
     *
     * @param source the source string of regex
     * @param error the error message
     * @throws ScriptException
     */
    public static void validateChinese(String source, String error) throws ScriptException {
        if(false == isChinese(source)){
            throw new ScriptException(error);
        }
    }

    /**
     * valid the source, if the source is general or chinese true, else false
     *
     * @param source the source string of regex
     * @return if the source is {@link PatternConstants#GENERAL_WITH_CHINESE} true, else false
     */
    public static boolean isGeneralWithChinese(String source) {
        return isMatch(PatternConstants.GENERAL_WITH_CHINESE, source);
    }

    /**
     * valid the source string is general or chinese
     * if the source string is not general or chinese, show error message<br>
     *
     * @param source the source string of regex
     * @param error the error message
     * @throws ScriptException
     */
    public static void validateGeneralWithChinese(String source, String error) throws ScriptException {
        if(false == isGeneralWithChinese(source)){
            throw new ScriptException(error);
        }
    }

    /**
     * valid the source, if the source is UUID true, else false
     *
     * @param source the source string of regex
     * @return if the source is {@link PatternConstants#UUID} true, else false
     */
    public static boolean isUUID(String source) {
        return isMatch(PatternConstants.UUID, source) || isMatch(PatternConstants.UUID32, source);
    }

    /**
     * valid the source string is UUID
     * if the source string is not UUID, show error message<br>
     *
     * @param source the source string of regex
     * @param error the error message
     * @throws ScriptException
     */
    public static void validateUUID(String source, String error) throws
            ScriptException
    {
        if(false == isUUID(source)){
            throw new ScriptException(error);
        }
    }

}