package org.sql.assistant;

/**
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
