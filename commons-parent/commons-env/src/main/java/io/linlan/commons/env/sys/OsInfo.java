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
 * system environment class to deal with the operation system info
 * Filename:OsInfo.java
 * Desc:Operation System information
 * operation sys infos, such as:version, name, arch, separator
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017/7/4 16:03
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class OsInfo {

    /**
     * operation sys version
     * such as:
     * Unix:2.4.7-10
     * Windows:8.1
     */
    private final String OS_VERSION = SysEnvUtils.get("os.version");

    /**
     * operation sys architecture
     * such as:
     * Unix:i386
     * Windows:amd64
     */
    private final String OS_ARCH = SysEnvUtils.get("os.arch");

    /**
     * operation sys name
     * such as:
     * Linux
     * Windows 7
     */
    private final String OS_NAME = SysEnvUtils.get("os.name");

    /**
     * file separator
     * such as:
     * Unix:/
     * Windows:\
     */
    private final String FILE_SEPARATOR = SysEnvUtils.get("create.separator");

    /**
     * line separator
     * such as:
     * Unix:\n
     * Windows:\r\n
     */
    private final String LINE_SEPARATOR = SysEnvUtils.get("line.separator");

    /**
     * path separator
     * such as:
     * Unix::
     * Windows:;
     */
    private final String PATH_SEPARATOR = SysEnvUtils.get("path.separator");

    /**
     * is the operation sys name match AIX
     * if match then true, else false
     */
    private final boolean IS_OS_AIX = getOsMatches("AIX");
    /**
     * is the operation sys name match HP-UX
     * if match then true, else false
     */
    private final boolean IS_OS_HP_UX = getOsMatches("HP-UX");

    /**
     * is the operation sys name match Irix
     * if match then true, else false
     */
    private final boolean IS_OS_IRIX = getOsMatches("Irix");

    /**
     * is the operation sys name match Linux or LINUX
     * if match then true, else false
     */
    private final boolean IS_OS_LINUX = getOsMatches("Linux") || getOsMatches("LINUX");

    /**
     * is the operation sys name match Mac
     * if match then true, else false
     */
    private final boolean IS_OS_MAC = getOsMatches("Mac");

    /**
     * is the operation sys name match OS/2
     * if match then true, else false
     */
    private final boolean IS_OS_OS2 = getOsMatches("OS/2");

    /**
     * is the operation sys name match Solaris
     * if match then true, else false
     */
    private final boolean IS_OS_SOLARIS = getOsMatches("Solaris");

    /**
     * is the operation sys name match SunOS
     * if match then true, else false
     */
    private final boolean IS_OS_SUN_OS = getOsMatches("SunOS");

    /**
     * is the operation sys name match Windows
     * if match then true, else false
     */
    private final boolean IS_OS_WINDOWS = getOsMatches("Windows");

    /**
     * get the info of opreation sys info
     *
     * @return info, if security then null
     */
    public final String getOsArch() {
        return OS_ARCH;
    }

    /**
     * get the info of opreation sys info
     *
     * @return info, if security then null
     */
    public final String getOsName() {
        return OS_NAME;
    }

    /**
     * get the info of opreation sys info
     *
     * @return info, if security then null
     */
    public final String getOsVersion() {
        return OS_VERSION;
    }

    /**
     * get the info of opreation sys info
     *
     * @return info, if security then null
     */
    public final String getFileSeparator() {
        return FILE_SEPARATOR;
    }

    /**
     * get the info of opreation sys info
     *
     * @return info, if security then null
     */
    public final String getLineSeparator() {
        return LINE_SEPARATOR;
    }

    /**
     * get the info of opreation sys info
     *
     * @return info, if security then null
     */
    public final String getPathSeparator() {
        return PATH_SEPARATOR;
    }

    /** if name match AIX*
     * @return true
     */
    public final boolean isAix() {
        return IS_OS_AIX;
    }

    /** if name match HP-UX*
     * @return true
     */
    public final boolean isHpUx() {
        return IS_OS_HP_UX;
    }

    /** if name match Irix*
     * @return true
     */
    public final boolean isIrix() {
        return IS_OS_IRIX;
    }

    /** if name match Linux or LINUX*
     * @return true
     */
    public final boolean isLinux() {
        return IS_OS_LINUX;
    }

    /** if name match MAC*
     * @return true
     */
    public final boolean isMac() {
        return IS_OS_MAC;
    }

    /** if name match OS/2*
     * @return true
     */
    public final boolean isOs2() {
        return IS_OS_OS2;
    }

    /** if name match Solaris*
     * @return true
     */
    public final boolean isSolaris() {
        return IS_OS_SOLARIS;
    }

    /** if name match SunOS*
     * @return true
     */
    public final boolean isSunOS() {
        return IS_OS_SUN_OS;
    }

    /** if name match Windows*
     * @return true
     */
    public final boolean isWindows() {
        return IS_OS_WINDOWS;
    }

    /**
     * to judge if operation sys name matches
     *
     * @param osNamePrefix operation sys name prefix
     *
     * @return if match, then true
     */
    private final boolean getOsMatches(String osNamePrefix) {
        if (OS_NAME == null) {
            return false;
        }
        return OS_NAME.startsWith(osNamePrefix);
    }

    /**
     * to judge if operation sys name matches
     *
     * @param osNamePrefix operation sys name prefix
     * @param osVersionPrefix operation sys version prefix
     *
     * @return if both match, then true
     */

    private final boolean getOsMatches(String osNamePrefix, String osVersionPrefix) {
        if ((OS_NAME == null) || (OS_VERSION == null)) {
            return false;
        }
        return OS_NAME.startsWith(osNamePrefix) && OS_VERSION.startsWith(osVersionPrefix);
    }

    /**
     * override toString
     *
     * @return the string of operation system info
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        SysEnvUtils.append(sb, "OS Arch:        ", getOsArch());
        SysEnvUtils.append(sb, "OS Name:        ", getOsName());
        SysEnvUtils.append(sb, "OS Version:     ", getOsVersion());
        SysEnvUtils.append(sb, "File Separator: ", getFileSeparator());
        SysEnvUtils.append(sb, "Line Separator: ", getLineSeparator());
        SysEnvUtils.append(sb, "Path Separator: ", getPathSeparator());
        return sb.toString();
    }

}
