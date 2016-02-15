/*     / \____  _    _  ____   ______  / \ ____  __    _ _____
 *    /  /    \/ \  / \/    \ /  /\__\/  //    \/  \  / /  _  \   Javaslang
 *  _/  /  /\  \  \/  /  /\  \\__\\  \  //  /\  \ /\\/  \__/  /   Copyright 2014-now Daniel Dietrich
 * /___/\_/  \_/\____/\_/  \_/\__\/__/___\_/  \_//  \__/_____/    Licensed under the Apache License, Version 2.0
 */
package javaslang.render.text;

import javaslang.collection.Tree;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BoxesTest {

    @Test
    public void shouldCreateBoxesOfTree() {

        final Tree<String> tree1 = Tree.of("TEST");
        assertThat(Boxes.box(tree1).toString()).isEqualTo("" +
                "┌────┐\n" +
                "│TEST│\n" +
                "└────┘");

        final Tree<String> tree2 = Tree.of("XXXX*XXXX", Tree.of("AA=AA"));
        assertThat(Boxes.box(tree2).toString()).isEqualTo("" +
                "┌─────────┐\n" +
                "│XXXX*XXXX│\n" +
                "└────┬────┘\n" +
                "     │     \n" +
                "  ┌──┴──┐  \n" +
                "  │AA=AA│  \n" +
                "  └─────┘  ");

        final Tree<String> tree3 = Tree.of("XXXX*XXX", Tree.of("AA=AA"));
        assertThat(Boxes.box(tree3).toString()).isEqualTo("" +
                "┌────────┐\n" +
                "│XXXX*XXX│\n" +
                "└───┬────┘\n" +
                "    │     \n" +
                " ┌──┴──┐  \n" +
                " │AA=AA│  \n" +
                " └─────┘  ");

        final Tree<String> tree4 = Tree.of("XXXX*XXXX", Tree.of("AA=A"));
        assertThat(Boxes.box(tree4).toString()).isEqualTo("" +
                "┌─────────┐\n" +
                "│XXXX*XXXX│\n" +
                "└────┬────┘\n" +
                "     │     \n" +
                "  ┌──┴─┐   \n" +
                "  │AA=A│   \n" +
                "  └────┘   ");

        final Tree<String> tree5 = Tree.of("AA=AA", Tree.of("XXXX*XXXX"));
        assertThat(Boxes.box(tree5).toString()).isEqualTo("" +
                "  ┌─────┐  \n" +
                "  │AA=AA│  \n" +
                "  └──┬──┘  \n" +
                "     │     \n" +
                "┌────┴────┐\n" +
                "│XXXX*XXXX│\n" +
                "└─────────┘");

        final Tree<String> tree6 = Tree.of("AA=A", Tree.of("XXXX*XXXX"));
        assertThat(Boxes.box(tree6).toString()).isEqualTo("" +
                "  ┌────┐   \n" +
                "  │AA=A│   \n" +
                "  └──┬─┘   \n" +
                "     │     \n" +
                "┌────┴────┐\n" +
                "│XXXX*XXXX│\n" +
                "└─────────┘");

        final Tree<String> tree7 = Tree.of("AA=AA", Tree.of("XXXX*XXX"));
        assertThat(Boxes.box(tree7).toString()).isEqualTo("" +
                " ┌─────┐  \n" +
                " │AA=AA│  \n" +
                " └──┬──┘  \n" +
                "    │     \n" +
                "┌───┴────┐\n" +
                "│XXXX*XXX│\n" +
                "└────────┘");

        final Tree<String> ast =
                Tree.of("IfExp",
                        Tree.of("BoolExp: false"),
                        Tree.of("IntExp: 10"),
                        Tree.of("IntExp: 20"));
        assertThat(Boxes.box(ast).toString()).isEqualTo("" +
                "                 ┌─────┐                  \n" +
                "                 │IfExp│                  \n" +
                "                 └──┬──┘                  \n" +
                "        ┌───────────┴──┬────────────┐     \n" +
                "┌───────┴──────┐ ┌─────┴────┐ ┌─────┴────┐\n" +
                "│BoolExp: false│ │IntExp: 10│ │IntExp: 20│\n" +
                "└──────────────┘ └──────────┘ └──────────┘");

        final Tree<String> tree =
                Tree.of("Ann",
                        Tree.of("Mary",
                                Tree.of("John",
                                        Tree.of("Avila")),
                                Tree.of("Karen",
                                        Tree.of("Frank")),
                                Tree.of("Steven\nAbbot\nBraddock")),
                        Tree.of("Peter",
                                Tree.of("Paul\nPalucci"),
                                Tree.of("Anthony")),
                        Tree.of("Christopher",
                                Tree.of("Samuel")));

        final String lispString = tree.toString().replaceAll("\n", " ");
        assertThat(lispString).isEqualTo("Tree(Ann (Mary (John Avila) (Karen Frank) Steven Abbot Braddock) (Peter Paul Palucci Anthony) (Christopher Samuel))");

        final String boxesString = Boxes.box(tree).toString();
        assertThat(boxesString).isEqualTo("" +
                "                           ┌───┐                            \n" +
                "                           │Ann│                            \n" +
                "                           └─┬─┘                            \n" +
                "             ┌───────────────┴──────┬────────────────┐      \n" +
                "          ┌──┴─┐                 ┌──┴──┐       ┌─────┴─────┐\n" +
                "          │Mary│                 │Peter│       │Christopher│\n" +
                "          └──┬─┘                 └──┬──┘       └─────┬─────┘\n" +
                "   ┌───────┬─┴───────┐         ┌────┴────┐           │      \n" +
                "┌──┴─┐  ┌──┴──┐ ┌────┴───┐ ┌───┴───┐ ┌───┴───┐   ┌───┴──┐   \n" +
                "│John│  │Karen│ │ Steven │ │ Paul  │ │Anthony│   │Samuel│   \n" +
                "└──┬─┘  └──┬──┘ │ Abbot  │ │Palucci│ └───────┘   └──────┘   \n" +
                "   │       │    │Braddock│ └───────┘                        \n" +
                "┌──┴──┐ ┌──┴──┐ └────────┘                                  \n" +
                "│Avila│ │Frank│                                             \n" +
                "└─────┘ └─────┘                                             "
        );
    }
}
