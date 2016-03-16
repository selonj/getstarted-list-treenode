package test;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.selonj.Node;
import com.selonj.NodeTree;
import org.hamcrest.Matcher;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

import static com.selonj.Node.node;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by Administrator on 2016-03-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringHibernateConfig.class)
public class NodeTreeTest {
    @Autowired
    private NodeTree nodeTree;

    @Before
    public void setUp() throws Exception {
        nodeTree.clear();
    }

    @Test
    public void listChildrenOfRootNode() throws Exception {
        Node root = node("1").
                append(node("1.1").append(node("1.1.1"))).
                append(node("1.2"));
        nodeTree.append(root);

        List<Node> children = nodeTree.getAllChildrenOf(root.getId());

        assertThat(children, allOf(hasSize(3),
                hasItem(graph("1.1")),
                hasItem(graph("1.1.1")),
                hasItem(graph("1.2"))
        ));

    }

    @Test
    public void listChildrenOfParentNode() throws Exception {
        Node parent = node("1").
                append(node("1.1").append(node("1.1.1"))).
                append(node("1.2"));
        nodeTree.append(node("index").append(parent));

        List<Node> children = nodeTree.getAllChildrenOf(parent.getId());

        assertThat(children, allOf(hasSize(3),
                hasItem(graph("1.1")),
                hasItem(graph("1.1.1")),
                hasItem(graph("1.2"))
        ));

    }

    private Matcher graph(String graph) {
        return hasProperty("graph", equalTo(graph));
    }

}
