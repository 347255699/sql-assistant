package org.sql.assistant.select.join;

import lombok.AllArgsConstructor;
import org.sql.assistant.common.condition.Condition;

/**
 * 复杂 join
 *
 * @author menfre
 */
@AllArgsConstructor
public class CompleteJoin implements Join {
    /**
     * 发起链接对象
     */
    private final Join firstJoin;

    /**
     * 接受链接对象
     */
    private final Join secondJoin;

    /**
     * 链接类型
     */
    private final JoinType joinType;

    /**
     * 链接条件
     */
    private final Condition condition;

    @Override
    public String getSql() {
        return "(" + firstJoin.getSql() + " " + joinType.name + " " + secondJoin.getSql() + " ON " + condition.getSql() + ")";
    }
}
