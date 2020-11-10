package org.sql.assistant.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author menfre
 */
@AllArgsConstructor
@Data
public class SqlHolder {
    private String sql;

    private Object[] args;
}
