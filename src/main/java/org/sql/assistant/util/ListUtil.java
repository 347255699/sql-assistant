package org.sql.assistant.util;

import java.util.List;

/**
 * @author menfre
 */
public class ListUtil {
    public static boolean isNotEmpty(List<?> list) {
        return list != null && !list.isEmpty();
    }
}
