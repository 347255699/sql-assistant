package org.sql.assistant.common.column;

import org.sql.assistant.common.annotation.AliasColumn;
import org.sql.assistant.common.annotation.IgnoreColumn;
import org.sql.assistant.common.annotation.MultiEntity;
import org.sql.assistant.util.StrUtil;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public static ColumnGroup[] groupByMultiEntity(Class<?> tClass) {
        MultiEntity multiEntity = tClass.getAnnotation(MultiEntity.class);
        HashMap<String, String> tableAliasMap;
        if (multiEntity == null) {
            throw new IllegalArgumentException("The MultiEntity annotation not found");
        }
        String[] tables = multiEntity.tables();
        String[] alias = multiEntity.alias();
        if (tables.length == 0) {
            throw new IllegalArgumentException("The tables length cannot be 0");
        }
        if (tables.length != alias.length) {
            throw new IllegalArgumentException("The alias length not same to the tables");
        }
        tableAliasMap = new HashMap<>(tables.length, 1.0f);
        IntStream.range(0, tables.length).forEach(i -> tableAliasMap.put(tables[i], alias[i]));
        String currentColumnTable = ColumnGroup.DEFAULT_PREFIX;
        List<Column> columns = new ArrayList<>(tClass.getDeclaredFields().length);
        for (Field declaredField : tClass.getDeclaredFields()) {
            if (declaredField.getAnnotation(IgnoreColumn.class) != null) {
                continue;
            }
            javax.persistence.Column columnAnnotation = declaredField.getAnnotation(javax.persistence.Column.class);
            AliasColumn aliasColumnAnnotation = declaredField.getAnnotation(AliasColumn.class);
            String value = declaredField.getName();
            String defaultVal = "";
            if (columnAnnotation != null) {
                if (StrUtil.isNotEmpty(columnAnnotation.table())) {
                    currentColumnTable = columnAnnotation.table();
                }
                if (StrUtil.isNotEmpty(columnAnnotation.name())) {
                    value = columnAnnotation.name();
                }
                if (StrUtil.isNotEmpty(columnAnnotation.columnDefinition())) {
                    defaultVal = columnAnnotation.columnDefinition();
                }
            }
            Column column = Column.of(value).prefix(currentColumnTable);
            if (aliasColumnAnnotation != null) {
                column.as(aliasColumnAnnotation.value());
            }
            if (StrUtil.isNotEmpty(defaultVal)) {
                column.ifNull(defaultVal);
            }
            columns.add(column);
        }

        Map<String, List<Column>> columnGroup = columns.stream().collect(Collectors.groupingBy(Column::getPrefix));
        return columnGroup.entrySet().stream()
                .map(cg -> {
                    String prefix = tableAliasMap.get(cg.getKey());
                    if (ColumnGroup.DEFAULT_PREFIX.equals(prefix)) {
                        prefix = "";
                    }
                    Column table = Column.of(cg.getKey()).as(cg.getKey());
                    return new ColumnGroup(prefix, table, cg.getValue());
                })
                .toArray(ColumnGroup[]::new);
    }

    public static List<Column> asList(Class<?> tClass) {
        return Arrays.stream(tClass.getDeclaredFields())
                .filter(f -> f.getAnnotation(IgnoreColumn.class) == null)
                .map(f -> {
                    javax.persistence.Column columnAnnotation = f.getAnnotation(javax.persistence.Column.class);
                    AliasColumn aliasColumnAnnotation = f.getAnnotation(AliasColumn.class);
                    String value = f.getName();
                    String defaultVal = "";
                    if (columnAnnotation != null) {
                        if (StrUtil.isNotEmpty(columnAnnotation.name())) {
                            value = columnAnnotation.name();
                        }
                        if (StrUtil.isNotEmpty(columnAnnotation.columnDefinition())) {
                            defaultVal = columnAnnotation.columnDefinition();
                        }
                    }
                    Column column = Column.of(value);
                    if (aliasColumnAnnotation != null) {
                        column.as(aliasColumnAnnotation.value());
                    }
                    if (StrUtil.isNotEmpty(defaultVal)) {
                        column.ifNull(defaultVal);
                    }
                    return column;
                })
                .collect(Collectors.toList());
    }
}
