# sql-assistant
![](https://img.shields.io/badge/license-Apache%202-blue)  

一个简洁易用的 SQL Builder，能够让你通过 java 的链式调用方式来编写复杂的 sql 语句。

## Installation
通过 maven 的方式引入:
```xml
<dependency>
    <groupId>com.github.347255699</groupId>
    <artifactId>sql-assistant</artifactId>
    <version>1.1.0</version>
</dependency>
```
通过 gradle 的方式引入：
```groovy
compile group: 'com.github.347255699', name: 'sql-assistant', version: '1.1.0'
```

## Usage
### Quick Start
sql-assistant 为所有的 sql 语句构造提供了统一的入口。你可以通过 `SqlAssistant` 对象来快速开始。
```java
SqlHolder sqlHolder = SqlAssistant.beginSimpleSelect()
                .select("name_space", "name", "public_ip", "private_ip")
                .from("m_node")
                .where(Conditions.equals("name", "menfre"))
                .end();
log.info("my sql: {}", sqlHolder.getSql());
log.info("my condition args: {}", Arrays.toString(sqlHolder.getArgs()));
```
结果:
```text
sql: SELECT name_space, name, public_ip, private_ip FROM m_node WHERE name = ?;
my condition args: [menfre]
```
### SelectBuilder
Select 语句根据不同的使用习惯被拆分为三种模式，当然以下三种模式均可以使用 `SqlAssistant` 来快速开始。

* SimpleSelectBuilder
* ComplexSelectBuilder
* JoinSelectBuilder

为了迎合大部分主流的 orm 框架，`SelectBuilder` 并不会将 where 子句的条件参数拼接到 sql 语句中，而是通过 Object 数组的方式额外提供，sql 语句中的条件参数均以占位符 `?` 代替。

### SimpleSelectBuilder
`SimpleSelectBuilder` 是 `SelectBuilder` 中最简单的一种，能满足常见单表查询的场景。

```java
SqlHolder sqlHolder = SqlAssistant.beginSimpleSelect()
                .select("name_space", "name", "public_ip", "private_ip")
                .from("m_node")
                .orderBy(Sort.of("n.name", Sort.Direction.ASC))
                .limit(10)
                .end();
```
对应的 sql:
```sql
SELECT name_space, name, public_ip, private_ip FROM m_node ORDER BY n.name ASC LIMIT 10;
```

### ComplexSelectBuilder
`ComplexSelectBuilder` 对比 `SimpleSelectBuilder` 增加了多表查询的能力，多表可以通过 `ColumnGroup` 对象维护起来。这种模式通常适用于多表查询且链接条件比较简单的情况。

```java
ColumnGroup node = Columns.group("n", "m_node", Columns.asList("name", "name_space", "public_ip", "private_ip"));
ColumnGroup label = Columns.group("l", "m_label", Columns.asList("label_key", "label_value"));
SqlHolder sqlHolder = SqlAssistant.beginComplexSelect()
                .select(node, label)
                .where(Conditions.columnEquals("n.m_api_object_id", "l.object_id"))
                .where(Conditions.equals("n.name", "menfre"))
                .orderBy(Sort.of("n.name", Sort.Direction.ASC))
                .limit(5, 10)
                .end();
```
对应的 sql:
```sql
SELECT n.name, n.name_space, n.public_ip, n.private_ip, l.label_key, l.label_value FROM m_node AS n, m_label AS l WHERE n.m_api_object_id = l.object_id AND n.name = ? ORDER BY n.name ASC LIMIT 5, 10;
```

### JoinSelectBuilder

`JoinSelectBuilder` 是 `SelectBuilder` 最复杂的一种，在 `ComplexSelectBuilder` 的基础上增加了 join 子句；能够通过 join 链将不同的 join 子句链接起来，提升 join 逻辑的可读性和易用性。

```java
ColumnGroup node = Columns.group("n", "m_node", Columns.asList("name", "name_space", "public_ip", "private_ip"));
ColumnGroup label = Columns.group("l", "m_label", Columns.asList("label_key", "label_value"));
Join leftJoin = node.left(label, Conditions.columnEquals("n.m_api_object_id", "l.object_id"));
SqlHolder sqlHolder = SqlAssistant.beginJoinSelect()
                .select(node, label)
                .join(leftJoin)
                .where(Conditions.equals("n.name", "menfre"))
                .orderBy(Sort.of("n.name", Sort.Direction.ASC))
                .limit(5, 10)
                .end();
```
对应的 sql:
```sql
SELECT n.name, n.name_space, n.public_ip, n.private_ip, l.label_key, l.label_value FROM m_node AS n LEFT JOIN m_label AS l ON n.m_api_object_id = l.object_id WHERE n.name = ? ORDER BY n.name ASC LIMIT 5, 10;
```

## InsertBuilder

`InsertBuilder` 支持 `ColumnGroup` 作为参数来执行 insert into 操作。

```java
ColumnGroup node = Columns.group("m_node", Columns.asList("name", "name_space", "private_ip", "public_ip"));
SqlHolder sqlHolder = SqlAssistant.beginInsert()
                .insertInto(node)
                .values("menfre", "test", "192.168.0.1", "123.3.4.1")
                .end();
```
对应的 sql:
```sql
INSERT INTO m_node(name, name_space, private_ip, public_ip)VALUES(?, ?, ?, ?);
```

## DeleteBuilder

```java
SqlHolder sqlHolder = SqlAssistant.beginDelete()
                .deleteFrom("m_node")
                .where(Conditions.equals("name", "menfre"))
                .where(Conditions.equals("name_space", "test"))
                .end();
```
对应的 sql:
```sql
DELETE FROM m_node WHERE name = ? AND name_space = ?;
```

## UpdateBuilder

```java
SqlHolder sqlHolder = SqlAssistant.beginUpdate()
                .update("m_node")
                .set("name", "menfre2")
                .set(UpdateItem.of("name_space", "dev"))
                .where(Conditions.equals("name", "menfre"))
                .where(Conditions.equals("name_space", "test"))
                .end();
```
对应的 sql：
```sql
UPDATE m_node SET name = ?, name_space = ? WHERE name = ? AND name_space = ?;
```
## 注解
sql-assistant 的 Select 语句支持通过给 pojo 注解的方式来替代繁琐的 `Column` 构造。

* MultiEntity
* Entity

### MultiEntity
Pojo:
```java
@Data
@MultiEntity(tables = {"A","B"}, alias = {"a","b"})
public class MultiEntityVo {
    @Column(table = "user1")
    private int id;

    private String name;

    @Column(table = "A", name = "age1", columnDefinition = "18")
    @AliasColumn("age2")
    private int age;

    @IgnoreColumn
    private String address;
}
```
```java
SqlHolder sqlHolder = SqlAssistant.beginComplexSelect()
                .select(Columns.groupByMultiEntity(MultiEntityVo.class))
                .getSql();
```

对应的 sql:
```sql
SELECT a.id, a.name, IFNULL(b.age1, 18) AS age2 FROM user1 AS a, user2 AS b;
```

### Entity
Pojo:
```java
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
```
> @Entity 和 @Id 是 JPA 中的注解，在这里只会扫描 @Column、@AliasColumn、@IgnoreColumn 用于构造 sql 语句。
```java
SqlHolder sqlHolder = SqlAssistant.beginSimpleSelect()
                .select(Columns.asList(EntityVo.class))
                .from("user")
                .getSql();
```
对应的 sql:
```sql
SELECT id, IFNULL(my_name, 'menfre'), IFNULL(age, 18) AS age2 FROM user;
```

