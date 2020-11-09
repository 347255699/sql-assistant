package org.sql.assistant.common.condition;

import org.sql.assistant.common.SqlProvider;

/**
 * 条件子句
 *
 * @author menfre
 */
public interface Condition extends SqlProvider {
    /**
     * 通过 and 拼接条件
     *
     * @param condition 拼接条件
     * @return 拼接完的条件
     */
    default Condition and(Condition condition) {
        return new AndOrCondition(this, false, condition);
    }

    /**
     * 通过 or 拼接条件
     *
     * @param condition 拼接条件
     * @return 拼接完的条件
     */
    default Condition or(Condition condition) {
        return new AndOrCondition(this, true, condition);
    }
}
