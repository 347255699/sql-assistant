package org.sql.assistant.common.condition;

import lombok.AllArgsConstructor;
import org.sql.assistant.common.column.Column;

/**
 * 抽象条件
 *
 * @author menfre
 */
@AllArgsConstructor
public abstract class AbstractCondition implements Condition {
    protected Column column;

    protected Operator operator;

    protected Object[] args;

    @Override
    public Object[] getArgs() {
        return args;
    }
}
