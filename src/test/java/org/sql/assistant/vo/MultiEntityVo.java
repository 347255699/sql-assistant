package org.sql.assistant.vo;

import lombok.Data;
import org.sql.assistant.common.annotation.AliasColumn;
import org.sql.assistant.common.annotation.IgnoreColumn;
import org.sql.assistant.common.annotation.MultiEntity;

import javax.persistence.Column;

@Data
@MultiEntity(tables = {"user1","user2"}, alias = {"a","b"})
public class MultiEntityVo {
    @Column(table = "user1")
    private int id;

    private String name;

    @Column(table = "user2", name = "age1", columnDefinition = "18")
    @AliasColumn("age2")
    private int age;

    @IgnoreColumn
    private String address;
}
