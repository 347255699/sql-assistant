package org.sql.assistant.common.column;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.sql.assistant.select.join.Join;
import org.sql.assistant.util.StrUtil;

import java.util.List;

/**
 * column 组
 *
 * @author menfre
 */
@AllArgsConstructor
@Data
public class ColumnGroup implements Join {
    /**
     * 前缀
     */
    private final String prefix;

    /**
     * 所属表名
     */
    private final Column table;

    /**
     * 字段组
     */
    private final List<Column> columns;

    @Override
    public String getSql() {
        return table.value();
    }
}
