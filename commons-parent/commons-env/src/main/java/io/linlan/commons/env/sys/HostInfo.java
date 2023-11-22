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

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * system environment class to deal with the host info
 * Filename:HostInfo.java
 * Desc:Host Info
 * host name, host address
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017/7/4 11:26
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class HostInfo {

    /**
     * host name
     * such as:localDomain, howaddServer
     */
    private final String HOST_NAME;

    /**
     * host address
     * such as:192.168.0.100, 127.0.0.1
     */
    private final String HOST_ADDRESS;

    /**
     * constructor of self
     */
    public HostInfo() {
        String hostName;
        String hostAddress;

        try {
            InetAddress localhost = InetAddress.getLocalHost();
            hostName = localhost.getHostName();
            hostAddress = localhost.getHostAddress();
        } catch (UnknownHostException e) {
            hostName = "localhost";
            hostAddress = "127.0.0.1";
        }

        HOST_NAME = hostName;
        HOST_ADDRESS = hostAddress;
    }

    /**
     * get the info of local host
     * @return info
     */
    public final String getHostName() {
        return HOST_NAME;
    }

    /**
     * get the info of local host
     * @return info
     */
    public final String getHostAddress() {
    //        System.getenv();
        return HOST_ADDRESS;
    }

    /**
     * override toString
     *
     * @return the string of host info
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        SysEnvUtils.append(sb, "Host Name:    ", getHostName());
        SysEnvUtils.append(sb, "Host Address: ", getHostAddress());
        return sb.toString();
    }

}
