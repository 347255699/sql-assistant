package org.sql.assistant.common.condition;

import org.sql.assistant.common.column.Column;

/**
 * 简单一点的条件
 *
 * @author menfre
 */
public class SimpleCondition extends AbstractCondition {
    public SimpleCondition(Column column, Operator operator, Object[] args) {
        super(column, operator, args);
    }

    @Override
    public String getSql() {
        return column.value() + " " + operator.symbol + " ?";
    }
}
