package org.sql.assistant.select;

import lombok.Data;
import org.sql.assistant.common.column.Column;

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
