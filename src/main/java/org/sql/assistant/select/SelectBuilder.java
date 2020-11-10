package org.sql.assistant.select;

import org.sql.assistant.common.SqlProvider;

/**
 * SelectBuilder 接口
 *
 * @author menfre
 */
public interface SelectBuilder extends SqlProvider {
    String DELIMITER = ", ";
    String SELECT = "SELECT ";
    String AND = " AND ";
    String WHERE = " WHERE ";
    String ORDER_BY = " ORDER BY ";
    String LIMIT = " LIMIT %s";
    String SEMICOLON = ";";
    String FROM = " FROM ";
}
