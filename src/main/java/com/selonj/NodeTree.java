package com.selonj;

import java.util.List;

/**
 * Created by Administrator on 2016-03-16.
 */
public interface NodeTree {
    NodeTree append(Node node);

    List<Node> getAllChildrenOf(Integer nodeId);

    void clear();
}
