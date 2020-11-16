package org.sql.assistant.common.annotation;

import java.lang.annotation.*;

/**
 * @author menfre
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MultiEntity {
    String[] tables();

    String[] alias();
}
