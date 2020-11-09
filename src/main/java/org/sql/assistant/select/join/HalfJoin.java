package org.sql.assistant.select.join;

import lombok.AllArgsConstructor;
import org.sql.assistant.common.column.Column;

/**
 * @author menfre
 */
@AllArgsConstructor
public class HalfJoin implements Join {
    /**
     * 表字段
     */
    private final Column table;

    @Override
    public String getSql() {
        return table.value();
    }
}
