package com.selonj;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;
import static org.hibernate.criterion.Restrictions.like;
import static org.hibernate.criterion.Restrictions.or;

/**
 * Created by Administrator on 2016-03-16.
 */
@Repository
public class HibernateNodeTree implements NodeTree {
    private SessionFactory sessionFactory;

    @Autowired
    public HibernateNodeTree(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void clear() {
        sessionFactory.getCurrentSession().createQuery("delete from Node").executeUpdate();
    }

    @Override
    @Transactional
    public NodeTree append(Node node) {
        sessionFactory.getCurrentSession().save(node);
        return this;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Node> getAllChildrenOf(Integer nodeId) {
        return sessionFactory.getCurrentSession().createCriteria(Node.class).add(matchesPathOfChildren(nodeId)).list();
    }

    private LogicalExpression matchesPathOfChildren(Integer nodeId) {
        return or(matchesPathOfRootNodeChildren(nodeId), matchesPathOfNormalChildren(nodeId));
    }

    private Criterion matchesPathOfRootNodeChildren(Integer nodeId) {
        //nodeId= 1, like 1/2/3/4/5;
        return like("path", format("%d/", nodeId), MatchMode.START);
    }

    private Criterion matchesPathOfNormalChildren(Integer nodeId) {
        //nodeId= 1, like 0/1/2/3/4/5;
        return like("path", format("/%d/", nodeId), MatchMode.ANYWHERE);
    }
}
