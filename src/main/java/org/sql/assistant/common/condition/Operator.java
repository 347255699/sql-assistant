package org.sql.assistant.common.condition;

/**
 * where 子句操作符
 *
 * @author menfre
 */
public enum Operator {
    /**
     * Operator
     */
    EQ("="),
    NE("!="),
    GT(">"),
    LT("<"),
    GE(">="),
    LE("<="),
    BETWEEN("BETWEEN"),
    NOT_BETWEEN("NOT BETWEEN"),
    LIKE("LIKE"),
    NOT_LIKE("NOT LIKE"),
    IN("IN"),
    NOT_IN("NOT IN"),
    ;

    public final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }
}
