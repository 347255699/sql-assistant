package org.sql.assistant.common.condition;

import lombok.AllArgsConstructor;

/**
 * @author menfre
 */
@AllArgsConstructor
public class AndOrCondition implements Condition {
    private final Condition first;

    private final boolean isOr;

    private final Condition second;

    @Override
    public String getSql() {
        String linker = isOr ? " OR " : " AND ";
        return "(" + first.getSql() + linker + second.getSql() + ")";
    }

    @Override
    public Object[] getArgs() {
        Object[] args = new Object[first.getArgs().length + second.getArgs().length];
        System.arraycopy(first.getArgs(), 0, args, 0, first.getArgs().length);
        System.arraycopy(second.getArgs(), 0, args, first.getArgs().length, second.getArgs().length);
        return args;
    }
}
