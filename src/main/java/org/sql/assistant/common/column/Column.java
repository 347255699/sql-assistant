package org.sql.assistant.common.column;

import org.sql.assistant.util.StrUtil;

/**
 * @author menfre
 */
public class Column {
    /**
     * 字段值
     */
    private final String value;

    /**
     * 别名
     */
    private String alias;

    /**
     * 前缀
     */
    private String prefix;

    private Column(String value) {
        this.value = value;
    }

    public Column as(String alias) {
        this.alias = alias;
        return this;
    }

    public Column prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * 获得 SelectColumn
     *
     * @param value 数值
     * @return SelectColumn
     */
    public static Column of(String value) {
        return new Column(value);
    }

    public String value() {
        if (!StrUtil.isNotEmpty(value)) {
            throw new IllegalArgumentException("The value cannot be empty");
        }
        String value = this.value;
        if (StrUtil.isNotEmpty(prefix)) {
            value = prefix.concat(".").concat(this.value);
        }
        if (StrUtil.isNotEmpty(alias)) {
            value = value.concat(" AS ").concat(this.alias);
        }
        return value;
    }
}
