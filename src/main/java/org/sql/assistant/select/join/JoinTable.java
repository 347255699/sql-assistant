package org.sql.assistant.select.join;

import lombok.AllArgsConstructor;
import org.sql.assistant.common.column.Column;

/**
 * 参与 Join 的 Table
 *
 * @author menfre
 */
@AllArgsConstructor
public class JoinTable implements Join {
    /**
     * 表字段
     */
    private final Column table;

    @Override
    public String getSql() {
        return table.value();
    }
}
