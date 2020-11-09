package org.sql.assistant;

/**
 * @author menfre
 */
public class MultiJoin implements Join {
    private final Join fromJoin;
    private JoinType joinType;
    private Column toTable;
    private final Column[] on = new Column[2];

    public MultiJoin(Join fromJoin) {
        this.fromJoin = fromJoin;
    }

    @Override
    public String getSql() {
        return "(" + fromJoin.getSql() + " " + joinType.type + " " + toTable.value() + " ON " + on[0].value() + " " + Operator.EQ.symbol + " " + on[1].value() + ")";
    }

    @Override
    public Join leftJoin(Column toTable, Column fromOn, Column toOn) {
        return join(toTable, fromOn, toOn, JoinType.LEFT);
    }

    @Override
    public Join leftJoin(String toTable, String fromOn, String toOn) {
        return leftJoin(Column.of(toTable), Column.of(fromOn), Column.of(toOn));
    }

    @Override
    public Join rightJoin(Column toTable, Column fromOn, Column toOn) {
        return join(toTable, fromOn, toOn, JoinType.RIGHT);
    }

    @Override
    public Join rightJoin(String toTable, String fromOn, String toOn) {
        return rightJoin(Column.of(toTable), Column.of(fromOn), Column.of(toOn));
    }

    @Override
    public Join innerJoin(Column toTable, Column fromOn, Column toOn) {
        return join(toTable, fromOn, toOn, JoinType.INNER);
    }

    @Override
    public Join innerJoin(String toTable, String fromOn, String toOn) {
        return innerJoin(Column.of(toTable), Column.of(fromOn), Column.of(toOn));
    }

    @Override
    public Join fullJoin(Column toTable, Column fromOn, Column toOn) {
        return join(toTable, fromOn, toOn, JoinType.FULL);
    }

    @Override
    public Join fullJoin(String toTable, String fromOn, String toOn) {
        return fullJoin(Column.of(toTable), Column.of(fromOn), Column.of(toOn));
    }

    private Join join(Column toTable, Column fromOn, Column toOn, JoinType joinType) {
        if (this.joinType != null) {
            return upgradeJoin(toTable, fromOn, toOn, joinType);
        } else {
            this.toTable = toTable;
            on[0] = fromOn;
            on[1] = toOn;
            this.joinType = joinType;
            return this;
        }
    }
}
