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

    public static void batchPrefix(String prefix, List<Column> columns) {
        columns.forEach(c -> c.prefix(prefix));
    }

    public static ColumnGroup group(String prefix, String table, List<Column> columns) {
        return new ColumnGroup(prefix, Column.of(table), columns);
    }

    public static ColumnGroup group(String prefix, String table, Column... columns) {
        return new ColumnGroup(prefix, Column.of(table), Arrays.asList(columns));
    }

    public static ColumnGroup group(String table, List<Column> columns) {
        return new ColumnGroup("", Column.of(table), columns);
    }

    public static ColumnGroup group(String table, Column... columns) {
        return new ColumnGroup("", Column.of(table), Arrays.asList(columns));
    }

    public static Column prefix(String value, String prefix) {
        return Column.of(value).prefix(prefix);
    }
}
