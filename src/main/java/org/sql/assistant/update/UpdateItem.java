package org.sql.assistant.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.sql.assistant.common.column.Column;

/**
 * Update 列表项
 *
 * @author menfre
 */
@AllArgsConstructor
@Data
public class UpdateItem {
    /**
     * 字段
     */
    private Column column;

    /**
     * 字段值
     */
    private Object value;

    public static UpdateItem of(String column, Object value) {
        return new UpdateItem(Column.of(column), value);
    }

    public static UpdateItem of(Column column, Object value) {
        return new UpdateItem(column, value);
    }
}
