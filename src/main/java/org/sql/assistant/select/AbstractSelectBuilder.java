package org.sql.assistant.select;

import org.sql.assistant.common.column.Column;
import org.sql.assistant.common.condition.Condition;
import org.sql.assistant.util.ListUtil;

import java.util.*;

/**
 * 抽象 Select 语句 Builder
 *
 * @param <T>
 * @author menfre
 */
public abstract class AbstractSelectBuilder<T> implements SelectBuilder {
    /**
     * 字段列表
     */
    protected List<Column> columns;

    /**
     * 条件列表
     */
    protected List<Condition> conditions;

    /**
     * 排序依据列表
     */
    protected List<Sort> sorts;

    /**
     * limit 信息
     */
    protected Limit limit;

    protected String selectSubSql() {
        if (!ListUtil.isNotEmpty(columns)) {
            return "";
        }
        StringJoiner sql = new StringJoiner(DELIMITER, SELECT, "");
        columns.stream().map(Column::value).forEach(sql::add);
        return sql.toString();
    }

    protected String whereSubSql() {
        if (!ListUtil.isNotEmpty(conditions)) {
            return "";
        }
        StringJoiner sql = new StringJoiner(AND, WHERE, "");
        conditions.stream().map(Condition::getSql).forEach(sql::add);
        return sql.toString();
    }

    protected String sortSubSql() {
        if (!ListUtil.isNotEmpty(sorts)) {
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

    /**
     * 返回子类
     *
     * @return 子类
     */
    protected abstract T me();

    public T where(Condition... conditions) {
        if (this.conditions == null) {
            this.conditions = new ArrayList<>();
        }
        this.conditions.addAll(Arrays.asList(conditions));
        return me();
    }

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
    public Object[] getArgs() {
        if (!ListUtil.isNotEmpty(conditions)) {
            return new Object[0];
        }
        List<Object> argList = new ArrayList<>();
        conditions.stream().map(Condition::getArgs).forEach(args -> argList.addAll(Arrays.asList(args)));
        return argList.toArray();
    }

    public SelectSql end() {
        return new SelectSql(getSql(), getArgs());
    }
}
