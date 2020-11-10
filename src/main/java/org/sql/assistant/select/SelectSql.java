package org.sql.assistant.select;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author menfre
 */
@AllArgsConstructor
@Data
public class SelectSql {
    private String sql;

    private Object[] args;
}
