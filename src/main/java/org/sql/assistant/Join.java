package org.sql.assistant;

/**
 * @author menfre
 */
public interface Join extends SqlProvider {

    /**
     * 链接
     *
     * @param toTable  待链接的表
     * @param fromOn 链接条件 a
     * @param toOn   链接条件 b
     * @return Join 对象
     */
    Join leftJoin(Column toTable, Column fromOn, Column toOn);

    /**
     * 链接
     *
     * @param toTable 待链接的表
     * @param fromOn  链接条件 a
     * @param toOn    链接条件 b
     * @return Join 对象
     */
    Join leftJoin(String toTable, String fromOn, String toOn);

    /**
     * 链接
     *
     * @param toTable  待链接的表
     * @param fromOn 链接条件 a
     * @param toOn   链接条件 b
     * @return Join 对象
     */
    Join rightJoin(Column toTable, Column fromOn, Column toOn);

    /**
     * 链接
     *
     * @param toTable 待链接的表
     * @param fromOn  链接条件 a
     * @param toOn    链接条件 b
     * @return Join 对象
     */
    Join rightJoin(String toTable, String fromOn, String toOn);


    /**
     * 链接
     *
     * @param toTable  待链接的表
     * @param fromOn 链接条件 a
     * @param toOn   链接条件 b
     * @return Join 对象
     */
    Join innerJoin(Column toTable, Column fromOn, Column toOn);

    /**
     * 链接
     *
     * @param toTable 待链接的表
     * @param fromOn  链接条件 a
     * @param toOn    链接条件 b
     * @return Join 对象
     */
    Join innerJoin(String toTable, String fromOn, String toOn);

    /**
     * 链接
     *
     * @param toTable  待链接的表
     * @param fromOn 链接条件 a
     * @param toOn   链接条件 b
     * @return Join 对象
     */
    Join fullJoin(Column toTable, Column fromOn, Column toOn);

    /**
     * 链接
     *
     * @param toTable 待链接的表
     * @param fromOn  链接条件 a
     * @param toOn    链接条件 b
     * @return Join 对象
     */
    Join fullJoin(String toTable, String fromOn, String toOn);

    /**
     * Join 的构造入口
     *
     * @param fromTable 来源表
     * @return Join
     */
    static Join of(String fromTable) {
        return of(Column.of(fromTable));
    }

    /**
     * Join 的构造入口
     *
     * @param fromTable 来源表
     * @return Join
     */
    static Join of(Column fromTable) {
        return new SimpleJoin(fromTable);
    }

    /**
     * 升级  join
     *
     * @param toTable  链接表
     * @param fromOn   链接条件
     * @param toOn     链接条件
     * @param joinType join 类型
     * @return Join
     */
    default Join upgradeJoin(Column toTable, Column fromOn, Column toOn, JoinType joinType) {
        MultiJoin multiJoin = new MultiJoin(this);
        switch (joinType) {
            case RIGHT:
                return multiJoin.rightJoin(toTable, fromOn, toOn);
            case INNER:
                return multiJoin.innerJoin(toTable, fromOn, toOn);
            case FULL:
                return multiJoin.fullJoin(toTable, fromOn, toOn);
            default:
                return multiJoin.leftJoin(toTable, fromOn, toOn);
        }
    }

    /**
     * 获得 sql 参数组
     *
     * @return 参数组
     */
    @Override
    default Object[] getArgs() {
        return null;
    }
}
