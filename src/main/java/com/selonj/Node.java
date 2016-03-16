package com.selonj;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2016-03-16.
 */
@Entity
@Table
@TableGenerator(name = "sequences")
public class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "sequences")
    private Integer id;
    private String graph;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "none"))
    private Node parent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent",fetch = FetchType.LAZY)
    private Set<Node> children = new HashSet<>();
    @Access(AccessType.PROPERTY)
    private String path;

    public Node() {
    }

    public Node(Integer id) {
        this(id,null);
    }

    public Node(Integer id, String graph) {
        this.id = id;
        this.graph = graph;
    }


    public static Node node(String graph) {
        Node node = new Node();
        node.graph = graph;
        return node;
    }

    public Node append(Node child) {
        child.parent = this;
        children.add(child);
        return this;
    }

    public Node appendTo(Node parent) {
        parent.append(this);
        return this;
    }

    public Integer getId() {
        return id;
    }

    public String getGraph() {
        return graph;
    }

    public Node getParent() {
        return parent;
    }

    public String getPath() {
        if (path == null) {
            if (id == null) {
                return null;
            }
            if (parent == null) {
                return path = String.valueOf(id);
            }
            return path = parent.pathOf(this);
        }
        return path;
    }

    private void setPath(String path) {
        this.path = path;
    }

    private String pathOf(Node node) {
        return getPath() + "/" + node.id;
    }
}
