package org.sql.assistant;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class SelectBuilderTest {
    private static final String NODE_PREFIX = "n";
    private static final String LABEL_PREFIX = "l";

    @Test
    public void testSelect() {
        List<Column> columns1 = Columns.batchPrefix(NODE_PREFIX, "name_space", "name", "public_ip", "private_ip");
        List<Column> columns2 = Columns.batchPrefix(LABEL_PREFIX, "label_key", "label_value");

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

        Join leftJoin = Join.of("a")
                .rightJoin("b", "a.id", "b.a_id")
                .leftJoin("c", "a.id", "c.a_id")
                .fullJoin("d", "b.id", "d.b_id")
                .innerJoin("e", "b.id", "d.b_id");

        SelectBuilder selectBuilder = SqlAssistant
                .beginSelect()
                .select(columns1)
                .select(columns2)
                .join(leftJoin)
                .where(Conditions.equals("n.name", "menfre"))
                .where(Conditions.equals("n.nameSpace", "test"))
                .orderBy(Sort.of("n.name", Sort.Direction.ASC))
                .orderBy(Sort.of("n.nameSpace", Sort.Direction.DESC));
        String sql = selectBuilder.getSql();
        System.out.println(sql);
        System.out.println(Arrays.toString(selectBuilder.getArgs()));
    }
}
