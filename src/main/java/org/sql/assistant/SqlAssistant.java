package org.sql.assistant;

import org.sql.assistant.select.SelectBuilder;

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
