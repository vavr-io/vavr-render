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
        assertThat(lispString).isEqualTo("(Ann (Mary (John Avila) (Karen Frank) Steven Abbot Braddock) (Peter Paul Palucci Anthony) (Christopher Samuel))");

        final String boxesString = Boxes.box(tree).toString();
        assertThat(boxesString).isEqualTo("" +
                "                            ┌───┐                            \n" +
                "                            │Ann│                            \n" +
                "                            └─┬─┘                            \n" +
                "             ┌────────────────┴──────┬────────────────┐      \n" +
                "          ┌──┴─┐                  ┌──┴──┐       ┌─────┴─────┐\n" +
                "          │Mary│                  │Peter│       │Christopher│\n" +
                "          └──┬─┘                  └──┬──┘       └─────┬─────┘\n" +
                "   ┌───────┬─┴───────┐          ┌────┴────┐           │      \n" +
                "┌──┴─┐  ┌──┴──┐ ┌────┴───┐  ┌───┴───┐ ┌───┴───┐   ┌───┴──┐   \n" +
                "│John│  │Karen│ │ Steven │  │ Paul  │ │Anthony│   │Samuel│   \n" +
                "└──┬─┘  └──┬──┘ │ Abbot  │  │Palucci│ └───────┘   └──────┘   \n" +
                "   │       │    │Braddock│  └───────┘                        \n" +
                "┌──┴──┐ ┌──┴──┐ └────────┘                                   \n" +
                "│Avila│ │Frank│                                              \n" +
                "└─────┘ └─────┘                                              "
        );
    }
}
