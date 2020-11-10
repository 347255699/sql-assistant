package org.sql.assistant.select;

import lombok.AllArgsConstructor;
import org.sql.assistant.common.SqlBuilder;
import org.sql.assistant.common.SqlProvider;

/**
 * limit 子句
 *
 * @author menfre
 */
@AllArgsConstructor
public class Limit implements SqlProvider {
    private static final String LIMIT = " LIMIT %s";

    private final long offset;

    private final long limit;

    public static Limit of(long limit) {
        return new Limit(0, limit);
    }

    public static Limit of(long offset, long limit) {
        return new Limit(offset, limit);
    }

    @Override
    public String getSql() {
        String limitSubSql = offset == 0 ? String.valueOf(limit) : offset + SqlBuilder.DELIMITER + limit;
        return String.format(LIMIT, limitSubSql);
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{offset, limit};
    }
}
