package org.sql.assistant.select;

import org.sql.assistant.common.column.Column;
import org.sql.assistant.common.column.ColumnGroup;
import org.sql.assistant.util.ListUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * 拥有 ColumnGroup 特性的 Select Builder 特性
 *
 * @param <T>
 * @author menfre
 */
public abstract class ColumnGroupSelectBuilder<T> extends AbstractSelectBuilder<T> {
    /**
     * column 组
     */
    protected List<ColumnGroup> columnGroups;

    public T select(ColumnGroup... columnGroups) {
        if (this.columnGroups == null) {
            this.columnGroups = new ArrayList<>();
        }
        assert columnGroups != null;
        this.columnGroups.addAll(Arrays.asList(columnGroups));
        return me();
    }

    @Override
    protected String selectSubSql() {
        if (!ListUtil.isNotEmpty(this.columnGroups)) {
            return "";
        }
        StringJoiner sql = new StringJoiner(DELIMITER, SELECT, "");
        this.columnGroups.stream()
                .map(ColumnGroup::getColumns)
                .reduce((a, b) -> {
                    a.addAll(b);
                    return a;
                })
                .ifPresent(columns -> columns.stream().map(Column::value).forEach(sql::add));
        return sql.toString();
    }
}
