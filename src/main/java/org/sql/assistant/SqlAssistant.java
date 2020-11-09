package org.sql.assistant;

/**
 * sql 入口
 *
 * @author menfre
 */
public class SqlAssistant {
    public static SelectBuilder beginSelect() {
        return new SelectBuilder();
    }
}
