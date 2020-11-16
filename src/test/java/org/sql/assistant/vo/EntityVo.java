package org.sql.assistant.vo;

import org.sql.assistant.common.annotation.AliasColumn;
import org.sql.assistant.common.annotation.IgnoreColumn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EntityVo {
    @Id
    private int id;

    @Column(name = "my_name", columnDefinition = "'menfre'")
    private String myName;

    @Column(columnDefinition = "18")
    @AliasColumn("age2")
    private int age;

    @IgnoreColumn
    private String address;
}
