# List all children node in sql

## 实现方式

当子节点加入到父节点上面时，根据该子树上面的**Id**计算该子节点的**path**。

```java
class Node{ ...
    public String getPath() {
        return parent.pathOf(this);
    }
    
    private String pathOf(Node node) {
        return getPath() + "/" + node.id;
    }
}
```

## 注意事项

- ID生成策略不能使用**sequence**或**identity**，因为NodeTree在持久化Node时要计算该Node的**path**。
```java
@GeneratedValue(strategy = GenerationType.TABLE, generator = "sequences")
private Integer id;
```

- 将父节点加载模式设置成**LAZY**,可以节省加载父节点的开销。
```java
@ManyToOne(fetch = FetchType.LAZY)
private Node parent;
```