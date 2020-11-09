package org.sql.assistant.common.condition;

import lombok.AllArgsConstructor;
import org.sql.assistant.common.column.Column;

/**
 * @author menfre
 */
@AllArgsConstructor
public class ColumnCondition implements Condition {
    private final Column leftColumn;

    private final Operator operator;

    private final Column rightColumn;

    @Override
    public String getSql() {
        return leftColumn.value() + " " + operator.symbol + " " + rightColumn.value();
    }

    @Override
    public Object[] getArgs() {
        return new Object[0];
    }
}
