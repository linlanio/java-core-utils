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

import io.linlan.commons.core.io.FileUtils;

/**
 * system environment class to deal with the runtime info
 * Filename:RuntimeInfo.java
 * Desc: runtime info
 * to get max memory, total memory, free memory, usable memory
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017/7/4 16:49
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class RuntimeInfo {

    /**
     * current runtime, to get max memory, total memory etc
     */
    private Runtime current = Runtime.getRuntime();

    /**
     * get the max memory for java runtime environment
     *
     * @return max memory
     */
    public final long getMaxMemory(){
        return current.maxMemory();
    }

    /**
     * get the total memory for java runtime environment
     *
     * @return total memory
     */
    public final long getTotalMemory(){
        return current.totalMemory();
    }

    /**
     * get the free memory for java runtime environment
     *
     * @return free memory
     */
    public final long getFreeMemory(){
        return current.freeMemory();
    }

    /**
     * get the usable memory for java runtime environment
     *
     * @return usable memory
     */
    public final long getUsableMemory(){
        return current.maxMemory() - current.totalMemory() + current.freeMemory();
    }

    /**
     * get the readable file size
     * @param size long unit
     * @return byte, B, KB, MB, GB, TB, EB
     */
    public static String getReadableFileSize(long size) {
        return FileUtils.readableFileSize(size);
    }

    /**
     * override toString
     *
     * @return the string of runtime info
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        SysEnvUtils.append(sb, "Max Memory:    ", getReadableFileSize(getMaxMemory()));
        SysEnvUtils.append(sb, "Total Memory:  ", getReadableFileSize(getTotalMemory()));
        SysEnvUtils.append(sb, "Free Memory:   ", getReadableFileSize(getFreeMemory()));
        SysEnvUtils.append(sb, "Usable Memory: ", getReadableFileSize(getUsableMemory()));
        return sb.toString();
    }
}
