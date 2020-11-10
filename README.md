# sql-assistant
一个简洁易用的 SQL Builder，能够让你通过 java 的链式调用方式来编写复杂的 sql 语句。

## Common

sql-assistant 为所有的 sql 语句构造提供了统一的入口。你可以通过 `SqlAssistant` 对象来快速开始。
```java
SqlHolder sqlHolder = SqlAssistant.beginSimpleSelect()
                .select("name_space", "name", "public_ip", "private_ip")
                .from("m_node")
                .where(Conditions.equals("name", "menfre"))
                .end();
System.out.println(sqlHolder.getSql());
System.out.println(Arrays.toString(sqlHolder.getArgs()));
```
## SelectBuilder
Select 语句根据不同的使用习惯被拆分为三种模式，当然以下三种模式均可以使用 `SqlAssistant` 来快速开始。

* SimpleSelectBuilder
* ComplexSelectBuilder
* JoinSelectBuilder

为了迎合大部分主流的 orm 框架，`SelectBuilder` 并不会将 where 子句的条件参数拼接到 sql 语句中，而是通过 Object 数组的方式额外提供，sql 语句中的条件参数均以占位符 `?` 代替。

```java
SqlHolder sqlHolder = SqlAssistant.beginSimpleSelect()
                .select("name_space", "name", "public_ip", "private_ip")
                .from("m_node")
                .where(Conditions.equals("name", "menfre"))
                .end();
```

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

### ComplexSelectBuilder
`ComplexSelectBuilder` 对比 `SimpleSelectBuilder` 增加了多表查询的能力，多表可以通过 `ColumnGroup` 对象维护起来。这种模式通常适用于多表查询且链接条件比较简单的情况。

```java
ColumnGroup node = Columns.createGroup("n", "m_node", Columns.asList("name", "name_space", "public_ip", "private_ip"));
ColumnGroup label = Columns.createGroup("l", "m_label", Columns.asList("label_key", "label_value"));
SqlHolder sqlHolder = SqlAssistant.beginComplexSelect()
                .select(node, label)
                .where(Conditions.columnEquals("n.m_api_object_id", "l.object_id"))
                .where(Conditions.equals("n.name", "menfre"))
                .orderBy(Sort.of("n.name", Sort.Direction.ASC))
                .limit(5, 10)
                .end();
```

### JoinSelectBuilder

`JoinSelectBuilder` 是 `SelectBuilder` 最复杂的一种，在 `ComplexSelectBuilder` 的基础上增加了 join 子句；能过通过 join 链将不同的 join 子句链接起来，提升 join 逻辑的可读性和易用性。

```java
ColumnGroup node = Columns.createGroup("n", "m_node", Columns.asList("name", "name_space", "public_ip", "private_ip"));
ColumnGroup label = Columns.createGroup("l", "m_label", Columns.asList("label_key", "label_value"));
Join leftJoin = node.left(label, Conditions.columnEquals("n.m_api_object_id", "l.object_id"));
SqlHolder sqlHolder = SqlAssistant.beginJoinSelect()
                .select(node, label)
                .join(leftJoin)
                .where(Conditions.equals("n.name", "menfre"))
                .orderBy(Sort.of("n.name", Sort.Direction.ASC))
                .limit(5, 10)
                .end();
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

## DeleteBuilder

```java
SqlHolder sqlHolder = SqlAssistant.beginDelete()
                .deleteFrom("m_node")
                .where(Conditions.equals("name", "menfre"))
                .where(Conditions.equals("name_space", "test"))
                .end();
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
 


