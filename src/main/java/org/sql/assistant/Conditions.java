package org.sql.assistant;

import java.util.List;

/**
 * Condition 工具
 *
 * @author menfre
 */
public class Conditions {
    public static Condition equals(String column, Object arg) {
        return equals(Column.of(column), arg);
    }

    public static Condition equals(Column column, Object arg) {
        return simpleOperate(column, Operator.EQ, arg);
    }

    public static Condition notEquals(String column, Object arg) {
        return notEquals(Column.of(column), arg);
    }

    public static Condition notEquals(Column column, Object arg) {
        return simpleOperate(column, Operator.NE, new Object[]{arg});
    }

    public static Condition greaterThan(String column, Object arg) {
        return greaterThan(Column.of(column), arg);
    }

    public static Condition greaterThan(Column column, Object arg) {
        return simpleOperate(column, Operator.GT, new Object[]{arg});
    }

    public static Condition lessThan(String column, Object arg) {
        return lessThan(Column.of(column), arg);
    }

    public static Condition lessThan(Column column, Object arg) {
        return simpleOperate(column, Operator.LT, new Object[]{arg});
    }

    public static Condition greaterThanEquals(String column, Object arg) {
        return greaterThanEquals(Column.of(column), arg);
    }

    public static Condition greaterThanEquals(Column column, Object arg) {
        return simpleOperate(column, Operator.GE, new Object[]{arg});
    }

    public static Condition lessThanEquals(String column, Object arg) {
        return lessThanEquals(Column.of(column), arg);
    }

    public static Condition lessThanEquals(Column column, Object arg) {
        return simpleOperate(column, Operator.LE, new Object[]{arg});
    }

    public static Condition between(String column, Object arg1, Object arg2) {
        return between(Column.of(column), arg1, arg2);
    }

    public static Condition between(Column column, Object arg1, Object arg2) {
        return complexCondition(column, Operator.BETWEEN, new Object[]{arg1, arg2});
    }

    public static Condition notBetween(String column, Object arg1, Object arg2) {
        return notBetween(Column.of(column), arg1, arg2);
    }

    public static Condition notBetween(Column column, Object arg1, Object arg2) {
        return complexCondition(column, Operator.NOT_BETWEEN, new Object[]{arg1, arg2});
    }

    public static Condition notLike(String column, Object arg) {
        return notLike(Column.of(column), arg);
    }

    public static Condition notLike(Column column, Object arg) {
        return complexCondition(column, Operator.NOT_LIKE, new Object[]{arg});
    }

    public static Condition like(String column, Object arg) {
        return like(Column.of(column), arg);
    }

    public static Condition like(Column column, Object arg) {
        return complexCondition(column, Operator.LIKE, new Object[]{arg});
    }

    public static Condition in(String column, List<Object> args) {
        return in(Column.of(column), args);
    }

    public static Condition in(Column column, List<Object> args) {
        return complexCondition(column, Operator.IN, args.toArray());
    }

    public static Condition notIn(String column, List<Object> args) {
        return notIn(Column.of(column), args);
    }

    public static Condition notIn(Column column, List<Object> args) {
        return complexCondition(column, Operator.NOT_IN, args.toArray());
    }

    public static Condition complexCondition(Column column, Operator operator, Object[] arg) {
        return new ComplexCondition(column, operator, arg);
    }

    private static Condition simpleOperate(Column column, Operator operator, Object arg) {
        return new SimpleCondition(column, operator, new Object[]{arg});
    }
}
