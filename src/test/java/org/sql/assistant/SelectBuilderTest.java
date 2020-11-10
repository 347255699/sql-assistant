package org.sql.assistant;

import org.junit.jupiter.api.Test;
import org.sql.assistant.common.column.Column;
import org.sql.assistant.common.column.ColumnGroup;
import org.sql.assistant.common.column.Columns;
import org.sql.assistant.common.condition.Conditions;
import org.sql.assistant.select.SelectSql;
import org.sql.assistant.select.Sort;
import org.sql.assistant.select.join.Join;

import java.util.Arrays;

public class SelectBuilderTest {
    @Test
    public void testSimpleSelect() {
        SelectSql ss = SqlAssistant.beginSimpleSelect()
                .select("name_space", "name", "public_ip", "private_ip")
                .from("m_node")
                .where(Conditions.equals("name", "menfre"))
                .orderBy(Sort.of("name", Sort.Direction.DESC))
                .limit(5, 10)
                .end();
        System.out.println(ss.getSql());
        System.out.println(Arrays.toString(ss.getArgs()));
    }

    @Test
    public void testComplexSelect() {
        ColumnGroup node = Columns.createGroup("n", Column.of("m_node"), Columns.asList("name", "name_space", "public_ip", "private_ip"));
        ColumnGroup label = Columns.createGroup("l", Column.of("m_label"), Columns.asList("label_key", "label_value"));
        SelectSql ss = SqlAssistant.beginComplexSelect()
                .select(node, label)
                .where(Conditions.columnEquals("n.m_api_object_id", "l.object_id"))
                .where(Conditions.equals("n.name", "menfre"))
                .orderBy(Sort.of("n.name", Sort.Direction.ASC))
                .limit(5, 10)
                .end();
        System.out.println(ss.getSql());
        System.out.println(Arrays.toString(ss.getArgs()));
    }

    @Test
    public void testJoinSelect() {
        ColumnGroup node = Columns.createGroup("n", Column.of("m_node"), Columns.asList("name", "name_space", "public_ip", "private_ip"));
        ColumnGroup label = Columns.createGroup("l", Column.of("m_label"), Columns.asList("label_key", "label_value"));
        Join leftJoin = node.left(label, Conditions.columnEquals("n.m_api_object_id", "l.object_id"));
        SelectSql ss = SqlAssistant.beginJoinSelect()
                .select(node, label)
                .join(leftJoin)
                .where(Conditions.equals("n.name", "menfre"))
                .orderBy(Sort.of("n.name", Sort.Direction.ASC))
                .limit(5, 10)
                .end();
        System.out.println(ss.getSql());
        System.out.println(Arrays.toString(ss.getArgs()));
    }
}
