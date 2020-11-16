package org.sql.assistant.common.column;

import lombok.Getter;
import org.sql.assistant.util.StrUtil;

/**
 * column 对象
 *
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
    @Getter
    private String prefix;

    /**
     * 默认值
     */
    private String defaultVal;

    private Column(String value) {
        this.value = value;
    }

    public Column ifNull(String defaultVal) {
        this.defaultVal = defaultVal;
        return this;
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
     * 获得 Column
     *
     * @param value 数值
     * @return Column
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
        if (StrUtil.isNotEmpty(defaultVal)) {
            value = String.format("IFNULL(%s, %s)", value, defaultVal);
        }
        if (StrUtil.isNotEmpty(alias)) {
            value = value.concat(" AS ").concat(this.alias);
        }
        return value;
    }
}
