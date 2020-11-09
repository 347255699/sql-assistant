package org.sql.assistant;

import lombok.Data;

/**
 * @author menfre
 */
@Data
public class Sort {
    public static Sort of(String column, Direction direction) {
        return of(Column.of(column), direction);
    }

    public static Sort of(Column column, Direction direction) {
        Sort sort = new Sort();
        sort.setColumn(column);
        sort.setDirection(direction);
        return sort;
    }

    private Column column;

    private Direction direction;

    public enum Direction {
        /**
         * 顺序
         */
        ASC("ASC"),
        DESC("DESC"),
        ;

        public final String name;

        Direction(String name) {
            this.name = name;
        }
    }
}
