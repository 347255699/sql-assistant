package org.sql.assistant.common;

/**
 * Sql 构造器
 *
 * @author menfre
 */
public interface SqlBuilder {
    String DELIMITER = ", ";
    String SEMICOLON = ";";
    String FROM = " FROM ";

    /**
     * 返回 Sql Holder
     *
     * @return SqlHolder
     */
    SqlHolder end();
}
