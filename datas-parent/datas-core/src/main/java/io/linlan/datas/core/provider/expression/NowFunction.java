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
package io.linlan.datas.core.provider.expression;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorLong;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

/**
 * 
 * Filename:NowFunction.java
 * Desc: 当前时间函数类，提供给各操作进行时间处理
 *
 * @author linlan of linlan.io
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017/12/20 22:13
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class NowFunction extends AbstractFunction {

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2, AviatorObject arg3) {
        String unit = FunctionUtils.getStringValue(arg1, env);
        Number interval = FunctionUtils.getNumberValue(arg2, env);
        String format = FunctionUtils.getStringValue(arg3, env);
        Calendar c = Calendar.getInstance();
        c.add(getUnit(unit), interval.intValue());
        if ("timestamp".equals(format)) {
            return new AviatorLong(c.getTime().getTime());
        } else {
            return new AviatorString(new SimpleDateFormat(format).format(c.getTime()));
        }
    }

    private int getUnit(String unit) {
        switch (unit) {
            case "D":
                return Calendar.DAY_OF_YEAR;
            case "Y":
                return Calendar.YEAR;
            case "M":
                return Calendar.MONTH;
            case "W":
                return Calendar.WEEK_OF_YEAR;
            case "h":
                return Calendar.HOUR;
            case "m":
                return Calendar.MINUTE;
            default:
                return Calendar.DAY_OF_YEAR;
        }
    }

    @Override
    public String getName() {
        return "now";
    }
}
