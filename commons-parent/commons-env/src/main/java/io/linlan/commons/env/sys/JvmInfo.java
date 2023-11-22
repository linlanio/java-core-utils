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
 * system environment class to deal with the java virtual machine info
 * Filename:JvmInfo.java
 * Desc:Java Virtual Machine Information
 * java virtual machine infos,such as:name, version, vendor
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017/7/4 16:02
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class JvmInfo {

    /**
     * java virtual machine name
     * such as:
     * Java HotSpot(TM) Client VM, Java HotSpot(TM) 64-Bit Server VM
     */
    private final String JAVA_VM_NAME = SysEnvUtils.get("java.vm.name");

    /**
     * java virtual machine version
     * such as:
     * Sun JDK 1.4.2:1.4.2-b28
     * Sun JDK 1.8.0_102:25.102-b14
     */
    private final String JAVA_VM_VERSION = SysEnvUtils.get("java.vm.version");

    /**
     * java virtual machine vendor
     * such as:
     * Sun Microsystems Inc
     * Oracle Corporation, since 1.5
     */
    private final String JAVA_VM_VENDOR = SysEnvUtils.get("java.vm.vendor");

    /**
     * java virtual machine info
     * such as:mixed mode
     */
    private final String JAVA_VM_INFO = SysEnvUtils.get("java.vm.info");

    /**
     * get the info of java virtual machine info
     *
     * @return info, if security then null
     */
    public final String getVmName() {
        return JAVA_VM_NAME;
    }

    /**
     * get the info of java virtual machine info
     *
     * @return info, if security then null
     */
    public final String getVmVersion() {
        return JAVA_VM_VERSION;
    }

    /**
     * get the info of java virtual machine info
     *
     * @return info, if security then null
     */
    public final String getVmVendor() {
        return JAVA_VM_VENDOR;
    }

    /**
     * get the info of java virtual machine info
     *
     * @return info, if security then null
     */
    public final String getVmInfo() {
        return JAVA_VM_INFO;
    }

    /**
     * override toString
     *
     * @return the string of java virtual machine info
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        SysEnvUtils.append(sb, "JavaVM Name:    ", getVmName());
        SysEnvUtils.append(sb, "JavaVM Version: ", getVmVersion());
        SysEnvUtils.append(sb, "JavaVM Vendor:  ", getVmVendor());
        SysEnvUtils.append(sb, "JavaVM Info:    ", getVmInfo());
        return sb.toString();
    }

}
