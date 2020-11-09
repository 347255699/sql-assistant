package org.sql.assistant;

import lombok.Setter;

/**
 * 字段
 *
 * @author menfre
 */
public class Column {
    /**
     * 字段值
     */
    @Setter
    private String value;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 别名
     */
    private String alias;

    private Column() {
    }

    /**
     * 简单值
     *
     * @param value 字段值
     * @return 字段对象
     */
    public static Column of(String value) {
        Column column = new Column();
        column.setValue(value);
        return column;
    }

    /**
     * 前缀
     *
     * @param prefix 前缀
     * @return 字段对象
     */
    public Column prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public Column alias(String alias) {
        this.alias = alias;
        return this;
    }

    public String value() {
        if (!isNotEmpty(this.value)) {
            throw new IllegalArgumentException("The value cannot be empty");
        }
        String value = this.value;
        if (isNotEmpty(prefix)) {
            value = prefix.concat(".").concat(this.value);
        }
        if (isNotEmpty(alias)) {
            value = value.concat(" AS ").concat(this.alias);
        }
        return value;
    }

    private boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }
}
