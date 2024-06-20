package io.linlan.commons.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义属性名称
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldName {
    /**
     * 字段名称
     * @return
     */
    String value();
    /**
     * 解析时是否忽略空值
     * @return
     */
    boolean isIgnoreNull() default false;
}
