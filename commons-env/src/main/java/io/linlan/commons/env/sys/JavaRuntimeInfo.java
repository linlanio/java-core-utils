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
 * system environment class to deal with the java runtime info
 * Filename:JavaRuntimeInfo.java
 * Desc:Java Runtime Info
 * java runtime infos, such as:version, name, home directory
 *
 * CreateTime:2020/7/4 14:43
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class JavaRuntimeInfo {

    /**
     * java runtime name
     * such as:
     * Java(TM) 2 Runtime Environment, Standard Edition
     */
    private final String JAVA_RUNTIME_NAME = SysEnvUtils.get("java.runtime.name");

    /**
     * java runtime version
     * such as: 1.7.,1.8.
     */
    private final String JAVA_RUNTIME_VERSION = SysEnvUtils.get("java.runtime.version");

    /**
     * java home directory
     * Installation directory for Java Runtime Environment (JRE)
     * such as:
     * /usr/java/jdk1.7
     * C:\Program Files\Java\jre1.8.0_102\
     */
    private final String JAVA_HOME = SysEnvUtils.get("java.home");

    /**
     * java extension directories
     */
    private final String JAVA_EXT_DIRS = SysEnvUtils.get("java.ext.dirs");

    /**
     * java endorsed directories
     * JVM Endorsed Standards Override Mechanism
     */
    private final String JAVA_ENDORSED_DIRS = SysEnvUtils.get("java.endorsed.dirs");

    /**
     * java class path
     * Path used to find directories and JAR archives containing class files.
     * Elements of the class path are separated by a platform-specific character
     * specified in the path.separator property.
     * such as:
     * /usr/java/jdk1.7/jre/lib/
     * C:\Program Files\Java\jre1.8.0_102\lib\
     */
    private final String JAVA_CLASS_PATH = SysEnvUtils.get("java.class.path");

    /**
     * java class version
     * such as:
     * Sun JDK 1.4.2:48.0
     * Sun JDK 1.7.0:51.0
     * Sun JDk 1.8.0:52.0
     */
    private final String JAVA_CLASS_VERSION = SysEnvUtils.get("java.class.version");

    /**
     * java library path
     * such as:
     * /usr/java/jdk1./bin
     * C:\Program Files\Java\jre1.8.0_102\bin
     */
    private final String JAVA_LIBRARY_PATH = SysEnvUtils.get("java.library.path");

    /**
     * sun boot class path
     * such as:
     * /usr/java/jdk1./jse/lib/rt.jar:/usr/java/jdk1./jse/lib/dt.jar:/usr/java/jdk1./jse/lib/tools.jar
     */
    private final String SUN_BOOT_CLASS_PATH = SysEnvUtils.get("sun.boot.class.path");

    /**
     * sun arch date model
     * such as:32, 64
     */
    private final String SUN_ARCH_DATA_MODEL = SysEnvUtils.get("sun.arch.data.model");

    /**
     * get the info of java runtime info
     *
     * @return info, if security then null
     */
    public final String getSunBootClassPath() {
        return SUN_BOOT_CLASS_PATH;
    }

    /**
     * get the info of java runtime info
     *
     * @return info, if security then null
     */
    public final String getSunArchDataModel() {
        return SUN_ARCH_DATA_MODEL;
    }

    /**
     * get the info of java runtime info
     *
     * @return info, if security then null
     */
    public final String getRuntimeName() {
        return JAVA_RUNTIME_NAME;
    }

    /**
     * get the info of java runtime info
     *
     * @return info, if security then null
     */
    public final String getRuntimeVersion() {
        return JAVA_RUNTIME_VERSION;
    }

    /**
     * get the info of java runtime info
     *
     * @return info, if security then null
     */
    public final String getHomeDir() {
        return JAVA_HOME;
    }

    /**
     * get the info of java runtime info
     *
     * @return info, if security then null
     */
    public final String getExtDirs() {
        return JAVA_EXT_DIRS;
    }

    /**
     * get the info of java runtime info
     *
     * @return info, if security then null
     */
    public final String getEndorsedDirs() {
        return JAVA_ENDORSED_DIRS;
    }

    /**
     * get the info of java runtime info
     *
     * @return info, if security then null
     */
    public final String getClassPath() {
        return JAVA_CLASS_PATH;
    }


    /**
     * get the info of java runtime info
     *
     * @return info, if security then null
     */
    public final String getClassVersion() {
        return JAVA_CLASS_VERSION;
    }

    /**
     * get the info of java runtime info
     *
     * @return info, if security then null
     */
    public final String getLibraryPath() {
        return JAVA_LIBRARY_PATH;
    }


    /**
     * get the info of java runtime info
     *
     * @return info, if security then null
     */
    public final String getProtocolPackages() {
        return SysEnvUtils.get("java.protocol.handler.pkgs", true);
    }

    /**
     * override toString
     *
     * @return the string of java runtime info
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        SysEnvUtils.append(sb, "Java Runtime Name:      ", getRuntimeName());
        SysEnvUtils.append(sb, "Java Runtime Version:   ", getRuntimeVersion());
        SysEnvUtils.append(sb, "Java Home Dir:          ", getHomeDir());
        SysEnvUtils.append(sb, "Java Extension Dirs:    ", getExtDirs());
        SysEnvUtils.append(sb, "Java Endorsed Dirs:     ", getEndorsedDirs());
        SysEnvUtils.append(sb, "Java Class Path:        ", getClassPath());
        SysEnvUtils.append(sb, "Java Class Version:     ", getClassVersion());
        SysEnvUtils.append(sb, "Java Library Path:      ", getLibraryPath());
        SysEnvUtils.append(sb, "Java Protocol Packages: ", getProtocolPackages());
        return sb.toString();
    }

}
