package org.sql.assistant.common.condition;

import lombok.AllArgsConstructor;

/**
 * And/Or 拼接条件
 *
 * @author menfre
 */
@AllArgsConstructor
public class AndOrCondition implements Condition {
    /**
     * 拼接左边条件
     */
    private final Condition left;

    /**
     * 拼接符号标记
     */
    private final boolean isOr;

    /**
     * 拼接右边符号
     */
    private final Condition right;

    @Override
    public String getSql() {
        String linker = isOr ? " OR " : " AND ";
        return "(" + left.getSql() + linker + right.getSql() + ")";
    }

    @Override
    public Object[] getArgs() {
        Object[] args = new Object[left.getArgs().length + right.getArgs().length];
        System.arraycopy(left.getArgs(), 0, args, 0, left.getArgs().length);
        System.arraycopy(right.getArgs(), 0, args, left.getArgs().length, right.getArgs().length);
        return args;
    }
}
