package org.sql.assistant.common.column;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.sql.assistant.select.join.Join;

import java.util.List;

/**
 * column group
 *
 * @author menfre
 */
@AllArgsConstructor
@Data
public class ColumnGroup implements Join {
    private final String prefix;

    private final Column table;

    private final List<Column> columns;

    @Override
    public String getSql() {
        return table.value();
    }
}
