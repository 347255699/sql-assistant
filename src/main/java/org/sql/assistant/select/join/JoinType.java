package org.sql.assistant.select.join;

/**
 * @author menfre
 */
public enum JoinType {

    /**
     * join type
     */
    LEFT("LEFT JOIN"),
    RIGHT("RIGHT JOIN"),
    INNER("INNER JOIN"),
    FULL("FULL JOIN"),
    ;

    public final String name;

    JoinType(String name) {
        this.name = name;
    }
}
