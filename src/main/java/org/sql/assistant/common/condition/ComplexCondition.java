package org.sql.assistant.common.condition;

import org.sql.assistant.common.column.Column;

import java.util.StringJoiner;

/**
 * @author menfre
 */
public class ComplexCondition extends AbstractCondition {
    public ComplexCondition(Column column, Operator operator, Object[] args) {
        super(column, operator, args);
    }

    @Override
    public String getSql() {
        StringBuilder sql = new StringBuilder()
                .append(column.value())
                .append(" ");
        if (Operator.LIKE == operator || Operator.NOT_LIKE == operator) {
            sql.append(operator.symbol).append(" '%?%'");
        } else if (Operator.IN == operator || Operator.NOT_IN == operator) {
            sql.append(operator.symbol)
                    .append("(");
            StringJoiner args = new StringJoiner(", ");
            for (int i = 0; i < this.args.length; i++) {
                args.add("?");
            }
            sql.append(args.toString())
                    .append(")");
        } else {
            sql.insert(0, "(")
                    .append(operator.symbol).append(" ? AND ?)");
        }
        return sql.toString();
    }
}
