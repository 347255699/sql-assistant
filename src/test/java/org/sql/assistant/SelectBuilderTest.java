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
                .from("fx_node")
                .where(Conditions.equals("name", "menfre"))
                .end();
        System.out.println(ss.getSql());
        System.out.println(Arrays.toString(ss.getArgs()));
    }

    @Test
    public void testColumnGroupMode() {
        ColumnGroup group1 = Columns.createGroup(
                "n",
                Column.of("fx_node"),
                Columns.asList("name_space", "name", "public_ip", "private_ip")
        );
        ColumnGroup group2 = Columns.createGroup(
                "l",
                Column.of("fx_label"),
                Columns.asList("label_key", "label_value")
        );
        SelectSql ss = SqlAssistant.beginSelect()
                .select(group1)
                .select(group2)
                .where(Conditions.columnEquals("n.fx_api_object_id", "l.object_id"))
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
                Column.of("fx_node"),
                Columns.asList("name_space", "name", "public_ip", "private_ip")
        );
        ColumnGroup group2 = Columns.createGroup(
                "l",
                Column.of("fx_label"),
                Columns.asList("label_key", "label_value")
        );

        SelectSql ss = SqlAssistant.beginSelect()
                .select(group1)
                .select(group2)
                .join(group1.left(group2, Conditions.columnEquals("n.fx_api_object_id", "l.object_id")))
                .where(Conditions.equals("n.name", "menfre"))
                .where(Conditions.equals("n.name_space", "test"))
                .orderBy(Sort.of("n.name", Sort.Direction.ASC))
                .limit(10)
                .end();
        System.out.println(ss.getSql());
        System.out.println(Arrays.toString(ss.getArgs()));
    }

    @Test
    public void testSelect() {
//        List<Column> columns1 = Columns.batchPrefix(NODE_PREFIX, "name_space", "name", "public_ip", "private_ip");
//        List<Column> columns2 = Columns.batchPrefix(LABEL_PREFIX, "label_key", "label_value");

//        Condition other = Conditions.equals(Columns.prefix("name", "n"), "menfre").and(Conditions.notEquals("phone", "dsfd"));
//        SelectBuilder selectBuilder = SqlAssistant
//                .beginSelect()
//                .select(columns1)
//                .select(columns2)
//                .from("fx_node", "n")
//                .leftJoin("fx_label as l")
//                .on("n.fx_api_object_id", "l.object_id")
//                .where(other.or(Conditions.greaterThan("age", 12).and(Conditions.lessThan("age", 30))))
//                .where(Conditions.greaterThanEquals("age", "12").and(Conditions.lessThanEquals("age", 30)))
//                .where(Conditions.between("n.nameSpace2", "1", "10"))
//                .where(Conditions.notBetween("n.nameSpace2", "1", "10"))
//                .where(Conditions.like("n.nameSpace2", "haha"))
//                .where(Conditions.in("test", Arrays.asList("sdfsdf", "sdfadsf")))
//                .where(Conditions.notIn("test", Arrays.asList("sdfsdf", "sdfadsf")))
//                .where(Conditions.notLike("n.nameSpace2", "haha"));
//        String sql = selectBuilder.getSql();
//        System.out.println(sql);
//        System.out.println(Arrays.toString(selectBuilder.getArgs()));

//        Join leftJoin = Join.of("a")
//                .rightJoin("b", "a.id", "b.a_id")
//                .leftJoin("c", "a.id", "c.a_id")
//                .fullJoin("d", "b.id", "d.b_id")
//                .innerJoin("e", "b.id", "d.b_id");
//
//        SelectBuilder selectBuilder = SqlAssistant
//                .beginSelect()
//                .select(columns1)
//                .select(columns2)
//                .join(leftJoin)
//                .where(Conditions.equals("n.name", "menfre"))
//                .where(Conditions.equals("n.nameSpace", "test"))
//                .orderBy(Sort.of("n.name", Sort.Direction.ASC))
//                .orderBy(Sort.of("n.nameSpace", Sort.Direction.DESC));
//        String sql = selectBuilder.getSql();
//        System.out.println(sql);
//        System.out.println(Arrays.toString(selectBuilder.getArgs()));
    }
}
