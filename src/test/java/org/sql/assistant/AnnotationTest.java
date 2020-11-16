package org.sql.assistant;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sql.assistant.common.column.Columns;
import org.sql.assistant.vo.EntityVo;
import org.sql.assistant.vo.MultiEntityError1Vo;
import org.sql.assistant.vo.MultiEntityVo;

@Slf4j
public class AnnotationTest {

    @SneakyThrows
    @Test
    public void testMultiEntity() {
        String sql = SqlAssistant.beginComplexSelect()
                .select(Columns.groupByMultiEntity(MultiEntityVo.class))
                .getSql();
        Assertions.assertEquals("SELECT a.id, a.name, IFNULL(b.age1, 18) AS age2 FROM user1 AS a, user2 AS b;", sql);
    }

    @SneakyThrows
    @Test
    public void testMultiEntityError1Vo() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () ->
                        SqlAssistant.beginComplexSelect()
                                .select(Columns.groupByMultiEntity(MultiEntityError1Vo.class))
                                .getSql(),
                "The MultiEntity annotation not found");
    }

    @SneakyThrows
    @Test
    public void testMultiEntityError2Vo() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () ->
                        SqlAssistant.beginComplexSelect()
                                .select(Columns.groupByMultiEntity(MultiEntityError1Vo.class))
                                .getSql(),
                "The tables length cannot be 0");
    }

    @SneakyThrows
    @Test
    public void testMultiEntityError3Vo() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () ->
                        SqlAssistant.beginComplexSelect()
                                .select(Columns.groupByMultiEntity(MultiEntityError1Vo.class))
                                .getSql(),
                "The alias length not same to the tables");
    }

    @Test
    public void testEntityVo() {
        String sql = SqlAssistant.beginSimpleSelect()
                .select(Columns.asList(EntityVo.class))
                .from("user")
                .getSql();
        Assertions.assertEquals("SELECT id, IFNULL(my_name, 'menfre'), IFNULL(age, 18) AS age2 FROM user;", sql);
    }
}
