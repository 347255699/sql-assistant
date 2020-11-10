package org.sql.assistant.common.condition;

import lombok.AllArgsConstructor;
import org.sql.assistant.common.column.Column;

/**
 * 字段之间的比较条件，一般用在链表查询的链接条件
 *
 * @author menfre
 */
@AllArgsConstructor
public class ColumnCondition implements Condition {
    /**
     * 左边的字段
     */
    private final Column leftColumn;

    /**
     * 操作符号
     */
    private final Operator operator;

    /**
     * 右边的字段
     */
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
