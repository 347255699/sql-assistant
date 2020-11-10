package org.sql.assistant.select;

import org.sql.assistant.select.join.Join;

/**
 * Join Select Builder
 *
 * @author menfre
 */
public class JoinSelectBuilder extends ColumnGroupSelectBuilder<JoinSelectBuilder> {
    /**
     * join 子句
     */
    private Join join;

    public JoinSelectBuilder join(Join join) {
        this.join = join;
        return this;
    }

    @Override
    protected String fromSubSql() {
        String joinSql = join.getSql();
        return FROM + joinSql.substring(1, joinSql.length() - 1);
    }

    @Override
    protected JoinSelectBuilder me() {
        return this;
    }
}
