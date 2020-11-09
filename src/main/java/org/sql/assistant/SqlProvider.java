package org.sql.assistant;

/**
 * Sql 提供者
 *
 * @author menfre
 */
public interface SqlProvider {

    /**
     * 获得 sql 语句
     *
     * @return sql
     */
    String getSql();

    /**
     * 获得 sql 参数组
     *
     * @return 参数组
     */
    Object[] getArgs();
}
