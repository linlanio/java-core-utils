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
package io.linlan.commons.script;

import io.linlan.commons.core.StringUtils;
import io.linlan.commons.core.CoreException;

/**
 * this is the script exception of commons script package
 * Filename:ScriptException.java
 * Desc:ScriptException to extends CoreException
 * and provide all exception catch to commons script group
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017-07-26 10:19 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class ScriptException extends CoreException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new commons utils exception with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public ScriptException(Throwable cause) {
        super(StringUtils.format("{}: {}", cause.getClass().getSimpleName(), cause.getMessage()), cause);
    }

    /**
     * Constructs a new commons utils exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public ScriptException(String message) {
        super(message);
    }


    /**
     * Constructs a new utils exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public ScriptException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * Constructs a new commons utils exception with the specified detail message.
     * to throw message with template, such as:{} : {}
     * @param messageTemplate pattern string
     * @param params params to input
     */
    public ScriptException(String messageTemplate, Object... params) {
        super(StringUtils.format(messageTemplate, params));
    }

    /**
     * Constructs a new utils exception with the specified detail message and cause.
     * <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     * to throw message with template, such as:{}:{}:{}:{}
     *
     * @param cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @param messageTemplate pattern string
     * @param params params to input
     */
    public ScriptException(Throwable cause, String messageTemplate, Object... params) {
        super(StringUtils.format(messageTemplate, params), cause);
    }

}

