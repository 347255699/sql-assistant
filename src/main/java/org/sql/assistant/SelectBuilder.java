package org.sql.assistant;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author menfre
 */
public class SelectBuilder implements SqlProvider {
    /**
     * 待组装的 sql
     */
    private List<Column> columns;
    /**
     * sql 条件子句
     */
    private List<Condition> conditions;

    private Column table;

    private Join join;

    private List<Sort> sorts;

    public SelectBuilder select(String... columns) {
        return select(Arrays.stream(columns).map(Column::of).collect(Collectors.toList()));
    }

    public SelectBuilder select(Column... columns) {
        return select(Arrays.asList(columns));
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

    public SelectBuilder from(String table, String alias) {
        return from(Columns.as(table, alias));
    }

    public SelectBuilder from(String table) {
        return from(Column.of(table));
    }

    public SelectBuilder from(Column table) {
        this.table = table;
        return this;
    }

    public SelectBuilder join(Join join) {
        this.join = join;
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

    @Override
    public String getSql() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT ");
        StringJoiner selectColumns = new StringJoiner(", ");
        this.columns.stream().map(Column::value).forEach(selectColumns::add);
        sqlBuilder.append(selectColumns.toString());
        sqlBuilder.append(" FROM ");
        if (join == null) {
            sqlBuilder.append(table.value());
        } else {
            sqlBuilder.append(join.getSql());
        }
        if (this.conditions != null && !this.conditions.isEmpty()) {
            StringJoiner conditionsSql = new StringJoiner(" AND ");
            this.conditions.stream()
                    .map(Condition::getSql)
                    .forEach(conditionsSql::add);
            sqlBuilder.append(" WHERE ");
            sqlBuilder.append(conditionsSql.toString());
        }
        if (this.sorts != null && !this.sorts.isEmpty()) {
            StringJoiner sortSql = new StringJoiner(", ");
            this.sorts.stream()
                    .map(sort -> sort.getColumn().value() + " " + sort.getDirection().name)
                    .forEach(sortSql::add);
            sqlBuilder.append(" ORDER BY ");
            sqlBuilder.append(sortSql.toString());
        }
        sqlBuilder.append(";");
        return sqlBuilder.toString();
    }

    @Override
    public Object[] getArgs() {
        if (this.conditions == null || this.conditions.isEmpty()) {
            return new Object[0];
        }
        List<Object> argList = new ArrayList<>();
        conditions.stream().map(Condition::getArgs).forEach(args -> argList.addAll(Arrays.asList(args)));
        return argList.toArray();
    }
}
