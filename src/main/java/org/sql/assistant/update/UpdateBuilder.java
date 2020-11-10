package org.sql.assistant.update;

import org.sql.assistant.common.SqlHolder;
import org.sql.assistant.common.column.Column;
import org.sql.assistant.common.condition.AbstractConditionBuilder;
import org.sql.assistant.common.condition.Operator;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Update Builder
 *
 * @author menfre
 */
public class UpdateBuilder extends AbstractConditionBuilder<UpdateBuilder> {
    private static final String UPDATE = "UPDATE ";
    private static final String SET = " SET ";
    /**
     * 更改项列表
     */
    private List<UpdateItem> updateItems;

    /**
     * 表名称
     */
    private Column table;

    public UpdateBuilder update(String table) {
        this.table = Column.of(table);
        return this;
    }

    public UpdateBuilder set(UpdateItem updateItem) {
        if (this.updateItems == null) {
            this.updateItems = new ArrayList<>();
        }
        this.updateItems.add(updateItem);
        return this;
    }

    public UpdateBuilder set(String column, Object value) {
        return set(UpdateItem.of(column, value));
    }

    @Override
    protected UpdateBuilder me() {
        return this;
    }

    @Override
    public SqlHolder end() {
        return new SqlHolder(getSql(), mergeArgs());
    }

    private String subSql() {
        StringJoiner sql = new StringJoiner(DELIMITER);
        updateItems.stream().map(ui -> ui.getColumn().value() + " " + Operator.EQ.symbol + " ?").forEach(sql::add);
        return sql.toString();
    }

    private Object[] mergeArgs() {
        Object[] updateValues = updateItems.stream().map(UpdateItem::getValue).toArray();
        Object[] conditionArgs = getArgs();
        Object[] dest = new Object[updateValues.length + conditionArgs.length];
        System.arraycopy(updateValues, 0, dest, 0, updateValues.length);
        System.arraycopy(conditionArgs, 0, dest, updateValues.length, conditionArgs.length);
        return dest;
    }

    @Override
    public String getSql() {
        return UPDATE + table.value() + SET + subSql() + whereSubSql() + SEMICOLON;
    }
}
