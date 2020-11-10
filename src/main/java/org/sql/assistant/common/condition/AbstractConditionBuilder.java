package org.sql.assistant.common.condition;

import org.sql.assistant.common.SqlBuilder;
import org.sql.assistant.common.SqlProvider;
import org.sql.assistant.util.ListUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * 抽象 Condition Builder
 *
 * @param <T>
 * @author menfre
 */
public abstract class AbstractConditionBuilder<T> implements SqlProvider, SqlBuilder {
    protected static final String AND = " AND ";
    protected static final String WHERE = " WHERE ";

    /**
     * 条件列表
     */
    protected List<Condition> conditions;

    protected String whereSubSql() {
        if (ListUtil.isEmpty(conditions)) {
            return "";
        }
        StringJoiner sql = new StringJoiner(AND, WHERE, "");
        conditions.stream().map(Condition::getSql).forEach(sql::add);
        return sql.toString();
    }

    public T where(Condition... conditions) {
        if (this.conditions == null) {
            this.conditions = new ArrayList<>();
        }
        this.conditions.addAll(Arrays.asList(conditions));
        return me();
    }

    @Override
    public Object[] getArgs() {
        if (ListUtil.isEmpty(conditions)) {
            return new Object[0];
        }
        List<Object> argList = new ArrayList<>();
        conditions.stream().map(Condition::getArgs).forEach(args -> argList.addAll(Arrays.asList(args)));
        return argList.toArray();
    }

    /**
     * 返回子类
     *
     * @return 子类
     */
    protected abstract T me();
}
