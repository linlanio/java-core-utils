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
package io.linlan.commons.env.sys;

/**
 * system environment class to deal with the java info
 * Filename:JavaInfo.java
 * Desc:get Java Info
 * java env infos, such as:version/vendor/
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017/7/4 11:31
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class JavaInfo {

    /**
     * java version
     * such as:1.3.1_04/1.7.0_69/1.7.0_75/1.8.0_102
     */
    private final String JAVA_VERSION = SysEnvUtils.get("java.version");

    /**
     * java vendor info
     * such as:Sun Microsystems Inc.
     */
    private final String JAVA_VENDOR = SysEnvUtils.get("java.vendor");

    /**
     * java vendor url
     * such as:http://java.sun.com/
     */
    private final String JAVA_VENDOR_URL = SysEnvUtils.get("java.vendor.url");

    //ignore 1.1/1.2/1/3/1.4
    /**
     * is the java version equal 1.5
     * if equal then true, else false
     */
    private final boolean IS_JAVA_15 = getVersion("1.5");

    /**
     * is the java version equal 1.6
     * if equal then true, else false
     */
    private final boolean IS_JAVA_16 = getVersion("1.6");

    /**
     * is the java version equal 1.7
     * if equal then true, else false
     */
    private final boolean IS_JAVA_17 = getVersion("1.7");

    /**
     * is the java version equal 1.8
     * if equal then true, else false
     */
    private final boolean IS_JAVA_18 = getVersion("1.8");

    /**
     * get the info from sys environment
     *
     * @return info, if security then null
     */
    public final String getVersion() {
        return JAVA_VERSION;
    }

    /**
     * get the info from sys environment
     *
     * @return info, if security then null
     */
    public final String getVendor() {
        return JAVA_VENDOR;
    }

    /**
     * get the info from sys environment
     *
     * @return info, if security then null
     */

    public final String getVendorURL() {
        return JAVA_VENDOR_URL;
    }

    /** if version match 1.5*
     * @return true
     */
    public final boolean isJava15() {
        return IS_JAVA_15;
    }

    /** if version match 1.6*
     * @return true
     */
    public final boolean isJava16() {
        return IS_JAVA_16;
    }

    /** if version match 1.7*
     * @return true
     */
    public final boolean isJava17() {
        return IS_JAVA_17;
    }

    /** if version match 1.8*
     * @return true
     */
    public final boolean isJava18() {
        return IS_JAVA_18;
    }

    /**
     * if match by version prefix
     * @param versionPrefix
     * @return true/false
     */
    private final boolean getVersion(String versionPrefix) {
        if (JAVA_VERSION == null) {
            return false;
        }
        return JAVA_VERSION.startsWith(versionPrefix);
    }

    /**
     * override toString
     *
     * @return the string of java info
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        SysEnvUtils.append(sb, "Java Version:    ", getVersion());
        SysEnvUtils.append(sb, "Java Vendor:     ", getVendor());
        SysEnvUtils.append(sb, "Java Vendor URL: ", getVendorURL());
        return sb.toString();
    }

}
