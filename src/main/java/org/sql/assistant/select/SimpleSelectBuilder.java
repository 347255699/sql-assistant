package org.sql.assistant.select;

import org.sql.assistant.common.column.Column;
import org.sql.assistant.common.column.Columns;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的 Select Builder
 *
 * @author menfre
 */
public class SimpleSelectBuilder extends AbstractSelectBuilder<SimpleSelectBuilder> {
    /**
     * 表名
     */
    private Column table;

    public SimpleSelectBuilder select(String... column) {
        return select(Columns.asList(column));
    }

    public SimpleSelectBuilder select(List<Column> columns) {
        if (this.columns == null) {
            this.columns = new ArrayList<>();
        }
        this.columns.addAll(columns);
        return this;
    }

    public SimpleSelectBuilder from(String table) {
        return from(Column.of(table));
    }

    public SimpleSelectBuilder from(Column table) {
        this.table = table;
        return this;
    }

    @Override
    protected String fromSubSql() {
        return FROM.concat(table.value());
    }

    @Override
    protected SimpleSelectBuilder me() {
        return this;
    }
}
