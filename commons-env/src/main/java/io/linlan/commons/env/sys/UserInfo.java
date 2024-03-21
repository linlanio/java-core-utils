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
package io.linlan.commons.env.sys;

/**
 * system environment class to deal with the user info
 * Filename:UserInfo.java
 * Desc: user info
 * the user info of current sys
 *
 * CreateTime:2020/7/4 16:58
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class UserInfo {

    /**
     * user name
     * such as:root, test, administrator
     */
    private final String USER_NAME = SysEnvUtils.get("user.name");

    /**
     * user home directory
     * such as:
     * Unix:/usr/home, /home/test
     * Windows:C:\User\test
     */
    private final String USER_HOME = SysEnvUtils.get("user.home");

    /**
     * user current dir for work
     * such as:
     * Unix:/home/test/dir
     * Windows:C:\User\test\dir
     */
    private final String USER_DIR = SysEnvUtils.get("user.dir");

    /**
     * user language info
     * such as:zh, en
     */
    private final String USER_LANGUAGE = SysEnvUtils.get("user.language");
    /**
     * user country info
     * such as:CN, US, UK
     */
    private final String USER_COUNTRY = ((SysEnvUtils.get("user.country") == null)
            ? SysEnvUtils.get("user.region") :
            SysEnvUtils.get("user.country"));
    /**
     * java runtime environment temp directory
     * such as:
     * Unix:/tmp
     * Windows:C:\Windows\Temp
     */
    private final String JAVA_ENV_TMPDIR = SysEnvUtils.get("java.env.tmpdir");

    /**
     * get the info of user info
     *
     * @return info, if security then null
     */
    public final String getUserName() {
        return USER_NAME;
    }

    /**
     * get the info of user info
     *
     * @return info, if security then null
     */
    public final String getUserHome() {
        return USER_HOME;
    }

    /**
     * get the info of user info
     *
     * @return info, if security then null
     */
    public final String getCurrentDir() {
        return USER_DIR;
    }

    /**
     * get the info of user info
     *
     * @return info, if security then null
     */
    public final String getTempDir() {
        return JAVA_ENV_TMPDIR;
    }

    /**
     * get the info of user info
     *
     * @return info, if security then null
     */
    public final String getLanguage() {
        return USER_LANGUAGE;
    }

    /**
     * get the info of user info
     *
     * @return info, if security then null
     */
    public final String getCountry() {
        return USER_COUNTRY;
    }

    /**
     * override toString
     *
     * @return the string of user info
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        SysEnvUtils.append(sb, "User Name:        ", getUserName());
        SysEnvUtils.append(sb, "User Home Dir:    ", getUserHome());
        SysEnvUtils.append(sb, "User Current Dir: ", getCurrentDir());
        SysEnvUtils.append(sb, "User Temp Dir:    ", getTempDir());
        SysEnvUtils.append(sb, "User Language:    ", getLanguage());
        SysEnvUtils.append(sb, "User Country:     ", getCountry());
        return sb.toString();
    }

}
