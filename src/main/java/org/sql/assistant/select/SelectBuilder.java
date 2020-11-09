package org.sql.assistant.select;

import org.sql.assistant.common.SqlProvider;
import org.sql.assistant.common.column.Column;
import org.sql.assistant.common.column.ColumnGroup;
import org.sql.assistant.common.column.Columns;
import org.sql.assistant.common.condition.Condition;
import org.sql.assistant.select.join.CompleteJoin;
import org.sql.assistant.select.join.Join;
import org.sql.assistant.util.ListUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author menfre
 */
public class SelectBuilder implements SqlProvider {
    /**
     * 待组装的 column
     */
    private List<Column> columns;

    /**
     * 待组装的 column group
     */
    private List<ColumnGroup> columnGroups;

    /**
     * sql 条件子句
     */
    private List<Condition> conditions;

    /**
     * 表名
     */
    private Column table;

    /**
     * 排序
     */
    private List<Sort> sorts;

    /**
     * join 子句
     */
    private Join join;

    /**
     * limit 参数
     */
    private long offset;
    private long limit;

    /**
     * 模式，有三种模式
     */
    private SelectMode mode = SelectMode.COLUMN;

    public enum SelectMode {
        /**
         * mode
         */
        COLUMN,
        COLUMN_GROUP,
        JOIN
    }

    private void switchMode(SelectMode mode) {
        if (this.mode == SelectMode.JOIN) {
            return;
        }
        this.mode = mode;
    }

    private String getColumnSql(List<Column> columns) {
        StringJoiner selectColumns = new StringJoiner(", ");
        columns.stream().map(Column::value).forEach(selectColumns::add);
        return selectColumns.toString();
    }

    public SelectBuilder select(String... columns) {
        return select(Columns.asList(columns));
    }

    /**
     * 拼装 select 字段
     *
     * @param columns 字段列表
     * @return this
     */
    public SelectBuilder select(List<Column> columns) {
        if (this.columns == null) {
            this.columns = columns;
        } else {
            this.columns.addAll(columns);
        }
        return this;
    }

    public SelectBuilder select(ColumnGroup... columnGroup) {
        if (columnGroups == null) {
            columnGroups = new ArrayList<>();
        }
        columnGroups.addAll(Arrays.asList(columnGroup));
        switchMode(SelectMode.COLUMN_GROUP);
        return this;
    }

    public SelectBuilder from(String table) {
        return from(Column.of(table));
    }

    public SelectBuilder from(Column table) {
        this.table = table;
        return this;
    }

    public SelectBuilder join(Join join) {
        if (!(join instanceof CompleteJoin)) {
            throw new IllegalArgumentException("Only accept CompleteJoin Object");
        }
        this.join = join;
        switchMode(SelectMode.JOIN);
        return this;
    }

    public SelectBuilder where(Condition condition) {
        if (this.conditions == null) {
            this.conditions = new ArrayList<>();
        }
        conditions.add(condition);
        return this;
    }

    public SelectBuilder orderBy(Sort... sorts) {
        if (this.sorts == null) {
            this.sorts = new ArrayList<>();
        }
        Collections.addAll(this.sorts, sorts);
        return this;
    }

    public SelectBuilder limit(long offset, long limit) {
        this.offset = offset;
        this.limit = limit;
        return this;
    }

    public SelectBuilder limit(long limit) {
        this.limit = limit;
        return this;
    }

    private void buildSelectSql(StringBuilder sqlBuilder){
        sqlBuilder.append("SELECT ");
        if (mode == SelectMode.COLUMN_GROUP) {
            List<Column> columns = new ArrayList<>();
            columnGroups.stream().map(ColumnGroup::getColumns).forEach(columns::addAll);
            List<Column> tables = columnGroups.stream().map(ColumnGroup::getTable).collect(Collectors.toList());
            sqlBuilder.append(getColumnSql(columns));
            sqlBuilder.append(" FROM ");
            sqlBuilder.append(getColumnSql(tables));
        } else if (mode == SelectMode.JOIN) {
            List<Column> columns = new ArrayList<>();
            columnGroups.stream().map(ColumnGroup::getColumns).forEach(columns::addAll);
            sqlBuilder.append(getColumnSql(columns));
            sqlBuilder.append(" FROM ");
            String joinSql = join.getSql();
            sqlBuilder.append(joinSql, 1, joinSql.length() - 1);
        } else {
            sqlBuilder.append(getColumnSql(this.columns));
            sqlBuilder.append(" FROM ");
            sqlBuilder.append(table.value());
        }
    }

    private void buildConditionSql(StringBuilder sqlBuilder){
        if (ListUtil.isNotEmpty(this.conditions)) {
            StringJoiner conditionsSql = new StringJoiner(" AND ");
            this.conditions.stream()
                    .map(Condition::getSql)
                    .forEach(conditionsSql::add);
            sqlBuilder.append(" WHERE ");
            sqlBuilder.append(conditionsSql.toString());
        }
    }

    private void buildSortSql(StringBuilder sqlBuilder){
        if (ListUtil.isNotEmpty(this.sorts)) {
            StringJoiner sortSql = new StringJoiner(", ");
            this.sorts.stream()
                    .map(sort -> sort.getColumn().value() + " " + sort.getDirection().name)
                    .forEach(sortSql::add);
            sqlBuilder.append(" ORDER BY ");
            sqlBuilder.append(sortSql.toString());
        }
    }

    private void buildLimitSql(StringBuilder sqlBuilder){
        if (this.limit != 0) {
            sqlBuilder.append(" LIMIT ");
            if (this.offset != 0) {
                sqlBuilder.append(this.offset);
                sqlBuilder.append(", ");
                sqlBuilder.append(this.limit);
            } else {
                sqlBuilder.append(this.limit);
            }
        }
    }

    @Override
    public String getSql() {
        StringBuilder sqlBuilder = new StringBuilder();
        buildSelectSql(sqlBuilder);
        buildConditionSql(sqlBuilder);
        buildSortSql(sqlBuilder);
        buildLimitSql(sqlBuilder);
        return sqlBuilder.append(";").toString();
    }

    public SelectSql end() {
        SelectSql selectSql = new SelectSql();
        selectSql.setSql(getSql());
        selectSql.setArgs(getArgs());
        return selectSql;
    }

    @Override
    public Object[] getArgs() {
        if (!ListUtil.isNotEmpty(this.conditions)) {
            return new Object[0];
        }
        List<Object> argList = new ArrayList<>();
        conditions.stream().map(Condition::getArgs).forEach(args -> argList.addAll(Arrays.asList(args)));
        return argList.toArray();
    }
}
