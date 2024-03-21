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

import java.io.Serializable;

/**
 * ResponseResult返回结果包装类
 * @Desc: 接口和服务返回对象类, 支持 member 和 pub 两种模式
 * @author Linlan
 * CreateTime:2023-07-18 14:06:00
 * @version 1.0
 * @since 1.0
 */
public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 成功标记:正常为true,错误为false
     */
    private boolean success = false;

    /**
     * 返回状态码:正常状态为0
     */
    private String code;

    /**
     * 返回消息
     */
    private String msg;

    /**
     * 返回数据对象
     */
    private T data;

    /**
     * 返回时间戳字符串
     */
    private String timestamp;


    public ResponseResult() {

    }

    /**
     * @param success success
     * @param msg the msg
     */
    public ResponseResult(boolean success, String msg) {
        this.setSuccess(success);
        this.setMsg(msg);
    }

    /**
     * @param success success
     */
    public ResponseResult(boolean success) {
        this.setSuccess(success);
    }

    /**
     * @param code the code, 状态码
     * @param msg the msg, 消息
     */
    public ResponseResult(String code, String msg) {
        this.setCode(code);
        this.setMsg(msg);
    }

    /**
     * Success response. 正常成功构造函数, 默认状态为0, 消息为成功.
     *
     * @return ResponseResult
     */
    public static ResponseResult ok() {
        return builder(HttpStatus.HA_OK.code,"成功",true);
    }

    public ResponseResult<T> setResultData(T t){
        this.setData(t);
        return this;
    }

    /**
     * Failed response. 错误构造函数, 默认状态码用 1.
     *
     * @param msg  the msg, 消息
     * @return ResponseResult
     */
    public static ResponseResult error(String msg) {
        return builder(HttpStatus.HA_FAIL.code, msg, false);
    }

    /**
     * Failed response. 错误构造函数
     *
     * @param code the code, 状态码
     * @param msg  the msg, 消息
     * @return ResponseResult
     */
    public static ResponseResult error(String code, String msg) {
        return builder(code, msg, false);
    }

    /** 生成器
     * @param code 状态码
     * @param msg 消息
     * @param success 成功标记, 正常为true, 错误为false
     * @param <T> 包装类
     * @return ResponseResult  {@link ResponseResult<T>}
     */
    private static <T> ResponseResult<T> builder(String code, String msg, boolean success) {
        ResponseResult result = new ResponseResult();
        result.setCode(code);
        result.setSuccess(success);
        result.setMsg(msg);
        result.setTimestamp(DateUtils.getNowString());
        return result;
    }

    /**
     * the enum Status to define code status
     * when ok, the code is 0
     * when fail, the code is 1
     * other the code is 500
     */
    private enum HttpStatus {
        /**
         * normal status, the right status
         */
        HA_OK("0"),
        /**
         * failure status
         */
        HA_FAIL("1");

        /**
         * code, response of request
         */
        private String code;

        HttpStatus(String var) {
            this.code = var;
        }

        /**
         * get the code of Status
         * @return code
         */
        public String code(){
            return this.code;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}