package org.sql.assistant;

import org.sql.assistant.select.ComplexSelectBuilder;
import org.sql.assistant.select.JoinSelectBuilder;
import org.sql.assistant.select.SimpleSelectBuilder;

/**
 * sql 入口
 *
 * @author menfre
 */
public class SqlAssistant {
    public static SimpleSelectBuilder beginSimpleSelect() {
        return new SimpleSelectBuilder();
    }

    public static ComplexSelectBuilder beginComplexSelect() {
        return new ComplexSelectBuilder();
    }

    public static JoinSelectBuilder beginJoinSelect() {
        return new JoinSelectBuilder();
    }
}
