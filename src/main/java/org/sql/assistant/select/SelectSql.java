package org.sql.assistant.select;

import lombok.Data;

/**
 * @author menfre
 */
@Data
public class SelectSql {
    private String sql;

    private Object[] args;
}
