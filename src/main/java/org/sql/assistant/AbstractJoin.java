package org.sql.assistant;

/**
 * @author menfre
 */
public abstract class AbstractJoin implements Join {
    protected final Column[] table = new Column[2];

    protected final Column[] on = new Column[2];

    protected JoinType joinType;
}
