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
    public void testColumnMode() {
        SelectSql ss = SqlAssistant.beginSelect()
                .select("name_space", "name", "public_ip", "private_ip")
                .from("m_node")
                .where(Conditions.equals("name", "menfre"))
                .end();
        System.out.println(ss.getSql());
        System.out.println(Arrays.toString(ss.getArgs()));
    }

    @Test
    public void testColumnGroupMode() {
        ColumnGroup group1 = Columns.createGroup(
                "n",
                Column.of("m_node"),
                Columns.asList("name_space", "name", "public_ip", "private_ip")
        );
        ColumnGroup group2 = Columns.createGroup(
                "l",
                Column.of("m_label"),
                Columns.asList("label_key", "label_value")
        );
        SelectSql ss = SqlAssistant.beginSelect()
                .select(group1)
                .select(group2)
                .where(Conditions.columnEquals("n.m_api_object_id", "l.object_id"))
                .orderBy(Sort.of("n.name", Sort.Direction.ASC))
                .end();
        System.out.println(ss.getSql());
        System.out.println(Arrays.toString(ss.getArgs()));
    }

    @Test
    public void testJoin() {
        Join left = Join.of("A AS a")
                .left(Join.of("B AS b"), Conditions.columnEquals("a.id", "b.a_id"))
                .right(Join.of("C AS c"), Conditions.columnEquals("b.id", "c.b_id"));

        Join right = Join.of("D AS d")
                .inner(Join.of("E AS e"), Conditions.columnEquals("d.id", "e.d_id"))
                .full(Join.of("F AS f"), Conditions.columnEquals("e.id", "f.e_id"));
        System.out.println(left.left(right, Conditions.columnEquals("a.id", "f.a_id")).getSql());
    }

    @Test
    public void testJoin2() {
        ColumnGroup group1 = Columns.createGroup(
                "n",
                Column.of("m_node"),
                Columns.asList("name_space", "name", "public_ip", "private_ip")
        );
        ColumnGroup group2 = Columns.createGroup(
                "l",
                Column.of("m_label"),
                Columns.asList("label_key", "label_value")
        );

        SelectSql ss = SqlAssistant.beginSelect()
                .select(group1)
                .select(group2)
                .join(group1.left(group2, Conditions.columnEquals("n.m_api_object_id", "l.object_id")))
                .where(Conditions.equals("n.name", "menfre"))
                .where(Conditions.equals("n.name_space", "test"))
                .orderBy(Sort.of("n.name", Sort.Direction.ASC))
                .limit(10)
                .end();
        System.out.println(ss.getSql());
        System.out.println(Arrays.toString(ss.getArgs()));
    }
}
