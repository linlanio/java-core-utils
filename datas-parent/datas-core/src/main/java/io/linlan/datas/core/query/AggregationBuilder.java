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
package io.linlan.datas.core.query;

import io.linlan.commons.script.json.JsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * Filename:AggregationBuilder.java
 * Desc:提供给后续进行聚合使用的Builder
 *
 * @author Linlan
 * CreateTime:2020/12/20 22:13
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class AggregationBuilder {

    /**
     * @param fieldName
     * @param size
     * @param missing
     * @return
     */
    public static JsonBuilder termsAggregation(String fieldName, int size, Object missing) {
        return JsonBuilder.json("terms", JsonBuilder.json()
                .put("field", fieldName)
                .put("size", size)
                .put("missing", missing)
        );
    }

    /**
     * @param fieldName
     * @param interval
     * @param min_doc_count
     * @return
     */
    public static JsonBuilder dateHistAggregation(String fieldName, String interval, int min_doc_count) {
        return dateHistAggregation(fieldName, interval, 0, null, null);
    }

    /**
     * @param fieldName
     * @param interval
     * @param min_doc_count
     * @param min
     * @param max
     * @return
     */
    public static JsonBuilder dateHistAggregation(String fieldName, String interval, int min_doc_count, Long min, Long max) {
        String format = "yyyy-MM-dd HH:mm";
        JsonBuilder extendedBound = JsonBuilder.json();
        if (min != null) {
            extendedBound.put("min", timestamp2DateStr(min, format));
        }
        if (max != null) {
            extendedBound.put("max", timestamp2DateStr(max, format));
        }
        TimeZone tz = Calendar.getInstance().getTimeZone();
        return JsonBuilder.json("date_histogram",
                JsonBuilder.json().put("field", fieldName)
                        .put("format", format)
                        .put("time_zone", tz.getID())
                        .put("interval", interval)
                        .put("min_doc_count", min_doc_count)
                        .put("extended_bounds", extendedBound)
        );
    }

    /**
     * @param timestamp
     * @param format
     * @return
     */
    private static String timestamp2DateStr(long timestamp, String format) {
        Date date = new Date();
        date.setTime(timestamp);
        return new SimpleDateFormat(format).format(date);
    }
}
