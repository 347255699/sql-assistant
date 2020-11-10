package org.sql.assistant.delete;

import org.sql.assistant.common.SqlHolder;
import org.sql.assistant.common.column.Column;
import org.sql.assistant.common.condition.AbstractConditionBuilder;

/**
 * Delete Builder
 *
 * @author menfre
 */
public class DeleteBuilder extends AbstractConditionBuilder<DeleteBuilder> {
    private static final String DELETE = "DELETE";
    /**
     * 表名称
     */
    private Column table;

    public DeleteBuilder deleteFrom(String table) {
        this.table = Column.of(table);
        return this;
    }

    @Override
    public String getSql() {
        return DELETE + FROM + table.value() + whereSubSql() + SEMICOLON;
    }

    @Override
    protected DeleteBuilder me() {
        return this;
    }

    @Override
    public SqlHolder end() {
        return new SqlHolder(getSql(), getArgs());
    }
}
