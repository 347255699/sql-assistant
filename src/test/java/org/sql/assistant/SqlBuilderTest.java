package org.sql.assistant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sql.assistant.common.column.Column;
import org.sql.assistant.common.column.ColumnGroup;
import org.sql.assistant.common.column.Columns;
import org.sql.assistant.common.condition.Conditions;
import org.sql.assistant.common.SqlHolder;
import org.sql.assistant.select.Sort;
import org.sql.assistant.select.join.Join;
import org.sql.assistant.update.UpdateItem;

import java.util.Arrays;

public class SqlBuilderTest {
    @Test
    public void testUpdate() {
        SqlHolder sqlHolder = SqlAssistant.beginUpdate()
                .update("m_node")
                .set("name", "menfre2")
                .set(UpdateItem.of("name_space", "dev"))
                .where(Conditions.equals("name", "menfre"))
                .where(Conditions.equals("name_space", "test"))
                .end();
        Assertions.assertEquals("UPDATE m_node SET name = ?, name_space = ? WHERE name = ? AND name_space = ?;"
                , sqlHolder.getSql());
        Assertions.assertArrayEquals(new Object[]{"menfre2", "dev", "menfre", "test"}, sqlHolder.getArgs());
    }

    @Test
    public void testDelete() {
        SqlHolder sqlHolder = SqlAssistant.beginDelete()
                .deleteFrom("m_node")
                .where(Conditions.equals("name", "menfre"))
                .where(Conditions.equals("name_space", "test"))
                .end();
        Assertions.assertEquals("DELETE FROM m_node WHERE name = ? AND name_space = ?;", sqlHolder.getSql());
        Assertions.assertArrayEquals(new Object[]{"menfre", "test"}, sqlHolder.getArgs());
    }

    @Test
    public void testInsert() {
        ColumnGroup node = Columns.group("m_node", Columns.asList("name", "name_space", "private_ip", "public_ip"));
        SqlHolder sqlHolder = SqlAssistant.beginInsert()
                .insertInto(node)
                .values("menfre", "test", "192.168.0.1", "123.3.4.1")
                .end();

        Assertions.assertEquals("INSERT INTO m_node(name, name_space, private_ip, public_ip)VALUES(?, ?, ?, ?);", sqlHolder.getSql());
        Assertions.assertArrayEquals(new Object[]{"menfre", "test", "192.168.0.1", "123.3.4.1"}, sqlHolder.getArgs());
    }

    @Test
    public void testSimpleSelect() {
        SqlHolder sqlHolder = SqlAssistant.beginSimpleSelect()
                .select("name_space", "name", "public_ip", "private_ip")
                .from("m_node")
                .where(Conditions.equals("name", "menfre"))
                .orderBy(Sort.of("name", Sort.Direction.DESC))
                .limit(5, 10)
                .end();
        Assertions.assertEquals("SELECT name_space, name, public_ip, private_ip FROM m_node WHERE name = ? ORDER BY name DESC LIMIT 5, 10;", sqlHolder.getSql());
        Assertions.assertArrayEquals(new Object[]{"menfre"}, sqlHolder.getArgs());
    }

    @Test
    public void testComplexSelect() {
        ColumnGroup node = Columns.group("n", "m_node", Columns.asList("name", "name_space", "public_ip", "private_ip"));
        ColumnGroup label = Columns.group("l", "m_label", Columns.asList("label_key", "label_value"));
        SqlHolder sqlHolder = SqlAssistant.beginComplexSelect()
                .select(node, label)
                .where(Conditions.columnEquals("n.m_api_object_id", "l.object_id"))
                .where(Conditions.equals("n.name", "menfre"))
                .orderBy(Sort.of("n.name", Sort.Direction.ASC))
                .limit(5, 10)
                .end();
        Assertions.assertEquals("SELECT n.name, n.name_space, n.public_ip, n.private_ip, l.label_key, l.label_value FROM n.m_node, l.m_label WHERE n.m_api_object_id = l.object_id AND n.name = ? ORDER BY n.name ASC LIMIT 5, 10;", sqlHolder.getSql());
        Assertions.assertArrayEquals(new Object[]{"menfre"}, sqlHolder.getArgs());
    }

    @Test
    public void testJoinSelect() {
        ColumnGroup node = Columns.group("n", "m_node", Columns.asList("name", "name_space", "public_ip", "private_ip"));
        ColumnGroup label = Columns.group("l", "m_label", Columns.asList("label_key", "label_value"));
        Join leftJoin = node.left(label, Conditions.columnEquals("n.m_api_object_id", "l.object_id"));
        leftJoin = leftJoin.left(Join.of(Column.of("m_api_object").as("o")), Conditions.columnEquals("n.fx_api_object_id", "o.id"));
        SqlHolder sqlHolder = SqlAssistant.beginJoinSelect()
                .select(node, label)
                .join(leftJoin)
                .where(Conditions.equals("n.name", "menfre"))
                .orderBy(Sort.of("n.name", Sort.Direction.ASC))
                .limit(5, 10)
                .end();
        Assertions.assertEquals("SELECT n.name, n.name_space, n.public_ip, n.private_ip, l.label_key, l.label_value FROM (n.m_node LEFT JOIN l.m_label ON n.m_api_object_id = l.object_id) LEFT JOIN m_api_object AS o ON n.fx_api_object_id = o.id WHERE n.name = ? ORDER BY n.name ASC LIMIT 5, 10;", sqlHolder.getSql());
        Assertions.assertArrayEquals(new Object[]{"menfre"}, sqlHolder.getArgs());
    }
}
