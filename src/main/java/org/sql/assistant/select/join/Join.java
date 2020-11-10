package org.sql.assistant.select.join;

import org.sql.assistant.common.SqlProvider;
import org.sql.assistant.common.column.Column;
import org.sql.assistant.common.condition.Condition;

/**
 * @author menfre
 */
public interface Join extends SqlProvider {
    /**
     * 左链接
     *
     * @param join      待链接对象
     * @param condition 链接条件
     * @return 链接对象
     */
    default Join left(Join join, Condition condition) {
        return completeJoin(join, condition, JoinType.LEFT);
    }

    /**
     * 右链接
     *
     * @param join      待链接对象
     * @param condition 链接条件
     * @return 链接对象
     */
    default Join right(Join join, Condition condition) {
        return completeJoin(join, condition, JoinType.RIGHT);
    }

    /**
     * 内链接
     *
     * @param join      待链接对象
     * @param condition 链接条件
     * @return 链接对象
     */
    default Join inner(Join join, Condition condition) {
        return completeJoin(join, condition, JoinType.INNER);
    }

    /**
     * 全链接
     *
     * @param join      待链接对象
     * @param condition 链接条件
     * @return 链接对象
     */
    default Join full(Join join, Condition condition) {
        return completeJoin(join, condition, JoinType.FULL);
    }

    /**
     * 完成 Join 操作
     *
     * @param join      待链接对象
     * @param condition 链接条件
     * @param joinType  链接类型
     * @return 链接对象
     */
    default Join completeJoin(Join join, Condition condition, JoinType joinType) {
        return new CompleteJoin(this, join, joinType, condition);
    }

    /**
     * Join 构造入口
     *
     * @param table 表名称
     * @return 链接对象
     */
    static Join of(String table) {
        return of(Column.of(table));
    }

    /**
     * Join 构造入口
     *
     * @param table 表字段
     * @return 链接对象
     */
    static Join of(Column table) {
        return new JoinTable(table);
    }

    /**
     * 获得参数
     *
     * @return 空参数组
     */
    @Override
    default Object[] getArgs() {
        return new Object[0];
    }
}
