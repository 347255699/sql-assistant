package org.sql.assistant.util;

import java.util.List;

/**
 * List 工具
 *
 * @author menfre
 */
public class ListUtil {
    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }
}
