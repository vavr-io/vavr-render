package io.vavr.render.swing;

import io.vavr.collection.Tree;
import org.junit.Test;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Enumeration;

import static org.assertj.core.api.Assertions.assertThat;

public class TreeTest {

    @Test
    public void test() {
        final Tree<String> tree =
                Tree.of("Ann",
                        Tree.of("Mary",
                                Tree.of("John",
                                        Tree.of("Avila")),
                                Tree.of("Karen"),
                                Tree.of("Steven\nAbbot\nBraddock")),
                        Tree.of("Peter",
                                Tree.of("Paul\nPalucci"),
                                Tree.of("Anthony")));

        final DefaultMutableTreeNode t = io.vavr.render.swing.Tree.create(tree);
        @SuppressWarnings("rawtypes")
        final Enumeration v = t.preorderEnumeration();
        final StringBuilder builder = new StringBuilder();
        while (v.hasMoreElements())
            builder.append(v.nextElement()).append('\n');
        final String s = builder.toString();

        assertThat(s).isEqualTo("Ann\n" +
                                "Mary\n" +
                                "John\n" +
                                "Avila\n" +
                                "Karen\n" +
                                "Steven\n" +
                                "Abbot\n" +
                                "Braddock\n" +
                                "Peter\n" +
                                "Paul\n" +
                                "Palucci\n" +
                                "Anthony\n" +
                                "");
    }
}
