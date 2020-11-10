package org.sql.assistant.insert;

import org.sql.assistant.common.SqlBuilder;
import org.sql.assistant.common.SqlHolder;
import org.sql.assistant.common.SqlProvider;
import org.sql.assistant.common.column.Column;
import org.sql.assistant.common.column.ColumnGroup;
import org.sql.assistant.util.ListUtil;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * Insert Builder
 *
 * @author menfre
 */
public class InsertBuilder implements SqlProvider, SqlBuilder {
    private static final String INSERT_INTO = "INSERT INTO ";
    private static final String VALUES = "VALUES";

    /**
     * 字段列表
     */
    private List<Column> columns;

    /**
     * 表名
     */
    private Column table;

    /**
     * 插入参数
     */
    private List<Object> args;

    public InsertBuilder insertInto(String table, List<Column> columns) {
        this.columns = columns;
        this.table = Column.of(table);
        return this;
    }

    public InsertBuilder insertInto(ColumnGroup columnGroup) {
        this.columns = columnGroup.getColumns();
        this.table = columnGroup.getTable();
        return this;
    }

    public InsertBuilder values(Object... args) {
        assert args != null;
        this.args = Arrays.asList(args);
        return this;
    }

    public String subSql(List<Column> columns) {
        if (ListUtil.isEmpty(columns)) {
            throw new IllegalArgumentException("Columns less one, cannot be empty");
        }
        StringJoiner columnsSql = new StringJoiner(DELIMITER, "(", ")");
        StringJoiner valuesSql = new StringJoiner(DELIMITER, "(", ")");
        for (Column column : columns) {
            columnsSql.add(column.value());
            valuesSql.add("?");
        }
        return columnsSql.toString() + VALUES + valuesSql.toString();
    }

    private void checkArgs() {
        if (ListUtil.isEmpty(this.args) || this.args.size() != this.columns.size()) {
            throw new IllegalArgumentException("Args cannot be empty or number not match the columns");
        }
    }

    @Override
    public String getSql() {
        return INSERT_INTO +
                table.value() +
                subSql(this.columns) +
                SEMICOLON;
    }

    @Override
    public SqlHolder end() {
        checkArgs();
        return new SqlHolder(getSql(), getArgs());
    }

    @Override
    public Object[] getArgs() {
        return ListUtil.isEmpty(args) ? new Object[0] : args.toArray();
    }
}
