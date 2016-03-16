package test;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.selonj.Node;
import com.selonj.NodeTree;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Administrator on 2016-03-16.
 */
@Configuration
@ComponentScan(basePackageClasses = NodeTree.class)
@EnableTransactionManagement
public class SpringHibernateConfig {
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        return new LocalSessionFactoryBean() {{
            setDataSource(mysql());
            setAnnotatedClasses(Node.class);
            setHibernateProperties(new Properties() {{
                put(Environment.SHOW_SQL, true);
            }});
        }};
    }

    private DataSource mysql() {
        MysqlDataSource mysql = new MysqlDataSource();
        mysql.setUser("root");
        mysql.setPassword("root");
        mysql.setDatabaseName("tree-test");
        return mysql;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
}
