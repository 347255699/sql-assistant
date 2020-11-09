package org.sql.assistant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字段工具
 *
 * @author menfre
 */
public class Columns {
    public static List<Column> batchPrefix(String prefix, List<Column> columns) {
        return columns.stream().map(c -> c.prefix(prefix)).collect(Collectors.toList());
    }

    public static List<Column> batchPrefix(String prefix, String... column) {
        return batchPrefix(prefix, Arrays.stream(column).map(Column::of).collect(Collectors.toList()));
    }

    public static Column as(String value, String alias) {
        return Column.of(value).alias(alias);
    }

    public static Column prefix(String value, String prefix) {
        return Column.of(value).prefix(prefix);
    }
}
