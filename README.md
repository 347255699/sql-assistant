# sql-assistant
一个简洁易用的 SQL Builder，能够让你通过 java 的链式调用方式来编写复杂的 sql 语句。
> sql-assistant 使用 lombok 第三方依赖包，使用 sql-assistant 前请确保你的 classpath 路径下已有 lombok 依赖。

## Common

sql-assistant 为所有的 sql 语句构造提供了统一的入口。你可以通过 `SqlAssistant` 对象来快速开始。
```java
String sql = SqlAssistant.beginSimpleSelect()
                .select("name_space", "name", "public_ip", "private_ip")
                .from("m_node")
                .where(Conditions.equals("name", "menfre"))
                .end()
                .getSql();
```
## SelectBuilder
Select 语句根据不同的使用习惯被拆分为三种模式，当然以下三种模式均可以使用 `SqlAssistant` 来快速开始。

* SimpleSelectBuilder
* ComplexSelectBuilder
* JoinSelectBuilder

为了迎合大部分主流的 orm 框架，`SelectBuilder` 并不会将 where 子句的条件参数拼接到 sql 语句中，而是通过 Object 数组的方式额外提供，sql 语句中的条件参数均以占位符 `?` 代替。

```java
SelectSql ss = SqlAssistant.beginSimpleSelect()
                .select("name_space", "name", "public_ip", "private_ip")
                .from("m_node")
                .where(Conditions.equals("name", "menfre"))
                .end();
System.out.println(ss.getSql());
System.out.println(Arrays.toString(ss.getArgs()));
```

### SimpleSelectBuilder
`SimpleSelectBuilder` 是 `SelectBuilder` 中最简单的一种，能满足常见单表查询的场景。

```java
SelectSql ss = SqlAssistant.beginSimpleSelect()
                .select("name_space", "name", "public_ip", "private_ip")
                .from("m_node")
                .orderBy(Sort.of("n.name", Sort.Direction.ASC))
                .limit(10)
                .end();
System.out.println(ss.getSql());
System.out.println(Arrays.toString(ss.getArgs()));
```

### ComplexSelectBuilder
`ComplexSelectBuilder` 对比 `SimpleSelectBuilder` 增加了多表查询的能力，多表可以通过 `ColumnGroup` 对象维护起来。这种模式通常适用于多表查询且链接条件比较简单的情况。

```java
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
```

### JoinSelectBuilder

`JoinSelectBuilder` 是 `SelectBuilder` 最复杂的一种，在 `ComplexSelectBuilder` 增加了 join 子句；能过通过 join 链将不同的 join 子句链接起来，提升 join 逻辑的可读性和易用性。

```java
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
```


