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


import io.linlan.commons.core.lang.Singleton;

import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.util.Properties;

/**
 * system environment class to provide utils for use
 * Filename:SysEnvUtils.java
 * Desc:System Environment Util
 * provide to get info of sys environment
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017/7/4 17:11
 *
 * @version 1.0
 * @since 1.0
 *
 */
public final class SysEnvUtils {

    /**
     * constructor of sys util
     */
    private SysEnvUtils() {

    }


    /**
     *  get value by name
     * @param name 
     * @return value or null
     */
    public static String get(String name) {
        try {
            return System.getProperty(name);
        } catch (SecurityException e) {
            return null;
        }
    }

    /**
     * get value by name, if quiet, do not show info in console
     * if error the print to log, then return null
     * @param name
     * @param quiet
     * @return value or null
     */
    public static String get(String name, boolean quiet) {
        try {
            return System.getProperty(name);
        } catch (SecurityException e) {
            if (!quiet) {
                PrintStream err = System.err;
                System.setErr(err.printf("Caught a SecurityException reading the " +
                    "sys property %s " +
                    "the SysEnvUtils property value will default to null.%n", name));
            }
            return null;
        }
    }

    /**
     * append more info to StringBuilder, with \n at the end
     * @param sb to StringBuilder
     * @param caption add caption, title, name
     * @param value add ext value, the match value
     * @param line add line getString
     */
    protected static void appendWith(StringBuilder sb, String caption, Object value, String line) {
        sb.append(caption).append(value.toString()).append(line);
    }

    /**
     * append more info to StringBuilder, with \n at the end
     * @param sb to StringBuilder
     * @param caption add caption, title, name
     * @param value add ext value, the match value
     */
    protected static void append(StringBuilder sb, String caption, Object value) {
        if (value == null){
            appendWith(sb, caption, "null", "\n");
        }else {
            appendWith(sb, caption, value, "\n");
        }
    }


    /**
     * get the system properties
     * @return properties object
     */
    static Properties props() {
        return System.getProperties();
    }

    /**
     * get process id of current
     * @return currentPID
     */
    public static long getCurrentPID(){
        return Long.parseLong(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
    }

    /**
     * get the host info
     * @return host info object
     */
    public static final HostInfo getHostInfo() {
        return Singleton.get(HostInfo.class);
    }

    /**
     * get the jvm info
     * @return jvm info object
     */
    public static final JvmInfo getJvmInfo() {
        return Singleton.get(JvmInfo.class);
    }


    /**
     * get the java info
     * @return java info object
     */
    public static final JavaInfo getJavaInfo() {
        return Singleton.get(JavaInfo.class);
    }

    /**
     * get the java runtime info
     * @return java runtime info object
     */
    public static final JavaRuntimeInfo getJavaRuntimeInfo() {
        return Singleton.get(JavaRuntimeInfo.class);
    }

    /**
     * get the runtime info
     * @return runtime info object
     */
    public static final RuntimeInfo getRuntimeInfo() {
        return Singleton.get(RuntimeInfo.class);
    }


    /**
     * get the operation system info
     * @return operation system info object
     */
    public static final OsInfo getOsInfo() {
        return Singleton.get(OsInfo.class);
    }

    /**
     * get the user info
     * @return user info object
     */
    public static final UserInfo getUserInfo() {
        return Singleton.get(UserInfo.class);
    }

}
