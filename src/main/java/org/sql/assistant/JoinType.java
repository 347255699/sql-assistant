package org.sql.assistant;

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

    public final String type;

    JoinType(String type) {
        this.type = type;
    }
}
