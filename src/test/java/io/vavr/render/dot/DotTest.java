package io.vavr.render.dot;

import io.vavr.render.dot.DotFile;
import io.vavr.collection.Tree;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DotTest {

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

    final String dot = "digraph test {\n" +
            "  lbl8 [label=\"Anthony\"];\n" +
            "  lbl6 [label=\"Peter\"];\n" +
            "  lbl7 [label=\"Paul\n" +
            "Palucci\"];\n" +
            "  lbl0 [label=\"Ann\"];\n" +
            "  lbl1 [label=\"Mary\"];\n" +
            "  lbl4 [label=\"Karen\"];\n" +
            "  lbl5 [label=\"Steven\n" +
            "Abbot\n" +
            "Braddock\"];\n" +
            "  lbl2 [label=\"John\"];\n" +
            "  lbl3 [label=\"Avila\"];\n" +
            "  lbl0 -> lbl1;\n" +
            "  lbl1 -> lbl2;\n" +
            "  lbl2 -> lbl3;\n" +
            "  lbl1 -> lbl4;\n" +
            "  lbl1 -> lbl5;\n" +
            "  lbl0 -> lbl6;\n" +
            "  lbl6 -> lbl7;\n" +
            "  lbl6 -> lbl8;\n" +
            "}\n" +
            "";

    @Test
    public void test() {
        assertThat(DotFile.create(tree).setName("test").toString()).isEqualTo(dot);
    }
}
