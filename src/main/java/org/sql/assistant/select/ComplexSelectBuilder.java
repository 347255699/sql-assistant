package org.sql.assistant.select;

import org.sql.assistant.common.column.Column;
import org.sql.assistant.common.column.ColumnGroup;
import org.sql.assistant.util.ListUtil;

import java.util.StringJoiner;

/**
 * 复杂的 Select Builder
 *
 * @author menfre
 */
public class ComplexSelectBuilder extends ColumnGroupSelectBuilder<ComplexSelectBuilder> {
    @Override
    protected String fromSubSql() {
        if (ListUtil.isEmpty(columnGroups)) {
            throw new IllegalArgumentException("ColumnGroup less on element, cannot be empty");
        }
        StringJoiner sql = new StringJoiner(DELIMITER, FROM, "");
        columnGroups.stream().map(ColumnGroup::getTable).map(Column::value).forEach(sql::add);
        return sql.toString();
    }

    @Override
    protected ComplexSelectBuilder me() {
        return this;
    }
}
