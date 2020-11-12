package org.sql.assistant.select;

import org.sql.assistant.common.SqlBuilder;
import org.sql.assistant.common.SqlHolder;
import org.sql.assistant.common.column.Column;
import org.sql.assistant.common.condition.AbstractConditionBuilder;
import org.sql.assistant.util.ListUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * 抽象 Select 语句 Builder
 *
 * @param <T>
 * @author menfre
 */
public abstract class AbstractSelectBuilder<T> extends AbstractConditionBuilder<T> implements SqlBuilder {
    protected static final String SELECT = "SELECT ";
    protected static final String ORDER_BY = " ORDER BY ";

    /**
     * 字段列表
     */
    protected List<Column> columns;

    /**
     * 排序依据列表
     */
    protected List<Sort> sorts;

    /**
     * limit 信息
     */
    protected Limit limit;

    protected String selectSubSql() {
        if (ListUtil.isEmpty(columns)) {
            return SELECT + "*";
        }
        StringJoiner sql = new StringJoiner(DELIMITER, SELECT, "");
        columns.stream().map(Column::value).forEach(sql::add);
        return sql.toString();
    }

    protected String sortSubSql() {
        if (ListUtil.isEmpty(sorts)) {
            return "";
        }
        StringJoiner sql = new StringJoiner(DELIMITER, ORDER_BY, "");
        sorts.stream().map(sort -> sort.getColumn().value().concat(" ").concat(sort.getDirection().name)).forEach(sql::add);
        return sql.toString();
    }

    protected String limitSubSql() {
        if (limit == null) {
            return "";
        }
        return limit.getSql();
    }

    /**
     * from 子句由子类实现
     *
     * @return from 子句
     */
    protected abstract String fromSubSql();

    public T orderBy(Sort... sorts) {
        if (this.sorts == null) {
            this.sorts = new ArrayList<>();
        }
        this.sorts.addAll(Arrays.asList(sorts));
        return me();
    }

    public T limit(long limit) {
        return limit(Limit.of(limit));
    }

    public T limit(long offset, long limit) {
        return limit(Limit.of(offset, limit));
    }

    public T limit(Limit limit) {
        this.limit = limit;
        return me();
    }

    @Override
    public String getSql() {
        return selectSubSql() +
                fromSubSql() +
                whereSubSql() +
                sortSubSql() +
                limitSubSql() +
                SEMICOLON;
    }

    @Override
    public SqlHolder end() {
        return new SqlHolder(getSql(), getArgs());
    }
}
