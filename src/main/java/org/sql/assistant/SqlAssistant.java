package org.sql.assistant;

import org.sql.assistant.delete.DeleteBuilder;
import org.sql.assistant.insert.InsertBuilder;
import org.sql.assistant.select.ComplexSelectBuilder;
import org.sql.assistant.select.JoinSelectBuilder;
import org.sql.assistant.select.SimpleSelectBuilder;
import org.sql.assistant.update.UpdateBuilder;

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

    public static InsertBuilder beginInsert() {
        return new InsertBuilder();
    }

    public static DeleteBuilder beginDelete() {
        return new DeleteBuilder();
    }

    public static UpdateBuilder beginUpdate() {
        return new UpdateBuilder();
    }
}
