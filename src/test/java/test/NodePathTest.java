package test;

import com.selonj.Node;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Administrator on 2016-03-16.
 */
public class NodePathTest {
    @Test
    public void rootNode() throws Exception {
        assertThat(node(1).getPath(), equalTo("1"));
    }

    @Test
    public void childNode() throws Exception {
        assertThat(node(2).appendTo(node(1)).getPath(), equalTo("1/2"));
        assertThat(node(3).appendTo(node(2).appendTo(node(1))).getPath(), equalTo("1/2/3"));
    }

    @Test
    public void returnNullIfIdIsNull() throws Exception {
        assertThat(new Node().getPath(), is(nullValue()));
    }

    private Node node(int id) {
        return new Node(id);
    }
}
