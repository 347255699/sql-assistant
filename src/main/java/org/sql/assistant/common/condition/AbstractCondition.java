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
    /**
     * 比较字段
     */
    protected Column column;

    /**
     * 操作符
     */
    protected Operator operator;

    /**
     * 条件参数
     */
    protected Object[] args;

    @Override
    public Object[] getArgs() {
        return args;
    }
}
