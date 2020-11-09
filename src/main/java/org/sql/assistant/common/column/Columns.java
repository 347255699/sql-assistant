package org.sql.assistant.common.column;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字段工具
 *
 * @author menfre
 */
public class Columns {
    public static List<Column> asList(String... columns) {
        return Arrays.stream(columns).map(Column::of).collect(Collectors.toList());
    }

    public static List<Column> batchPrefix(String prefix, List<Column> columns) {
        return columns.stream().map(c -> c.prefix(prefix)).collect(Collectors.toList());
    }

    public static ColumnGroup createGroup(String prefix, Column table, List<Column> columns) {
        return new ColumnGroup(prefix, table.as(prefix), batchPrefix(prefix, columns));
    }

    public static Column prefix(String value, String prefix) {
        return Column.of(value).prefix(prefix);
    }
}
