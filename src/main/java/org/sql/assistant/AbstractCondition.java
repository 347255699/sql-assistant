package org.sql.assistant;

import lombok.AllArgsConstructor;

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
