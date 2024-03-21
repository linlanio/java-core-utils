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
package io.linlan.commons.core;

import java.util.HashMap;
import java.util.Map;

/**
 * The Return Code of Response to interaction
 * Filename:Rcode.java
 * Desc: Rcode is the Return code of Response
 *
 * CreateTime:2020-07-24 9:42 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class Rcode extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;


    /**
     * the constructor of Rcode, the default code is 0
     */
    public Rcode() {
        put("code", Status.HA_OK.code());
    }

    /**
     * the ok method, return code 0 msg "SUCCESS"
     * @return {@link @Rcode}
     */
    public static Rcode ok() {
        return new Rcode().put("msg", "SUCCESS");
    }


    /**
     * the ok method, return code 0 msg with input msg
     * @param msg the input msg
     * @return {@link @Rcode}
     */
    public static Rcode ok(String msg) {
        Rcode r = new Rcode();
        r.put("msg", msg);
        return r;
    }

    /** the ok method, return code 0, the output is map
     * @param map the map with output info
     * @return {@link @Rcode}
     */
    public static Rcode ok(Map<String, Object> map) {
        Rcode r = new Rcode();
        r.putAll(map);
        return r;
    }

    /** put key with value
     * @param key the key
     * @param value the value
     * @return {@link @Rcode}
     */
    public Rcode put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    /** the error method, code is 500, the default msg is "未知异常，请联系管理员"
     * @return {@link @Rcode}
     */
    public static Rcode error() {
        return error(Status.HA_ERROR_500.code(), "未知异常，请联系管理员");
    }

    /** the error method, code is 1, the msg is input msg
     * @param msg the input msg
     * @return {@link @Rcode}
     */
    public static Rcode error(String msg) {
        return error(Status.HA_FAIL.code(), msg);
    }

    /**
     * error，错误返回，输出code和msg
     */
    /** the error method with input code and msg
     * @param code the input code
     * @param msg the input msg
     * @return {@link @Rcode}
     */
    public static Rcode error(int code, String msg) {
        Rcode r = new Rcode();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }


    /**
     * the enum Status to define code status
     * when ok, the code is 0
     * when fail, the code is 1
     * other the code is 500
     */
    public enum Status {
        /**
         * normal status, the right status
         */
        HA_OK(0),
        /**
         * failure status
         */
        HA_FAIL(1),
        /**
         * error status, such as http 500
         */
        HA_ERROR_500(500);

        /**
         * the constructor of Status
         * @param code the input code
         */
        Status(int code) {
            this.code = code;
        }

        /**
         * code, response of request
         */
        private Integer code;

        /**
         * get the code of Status
         * @return code
         */
        public Integer code(){
            return this.code;
        }
    }


}
