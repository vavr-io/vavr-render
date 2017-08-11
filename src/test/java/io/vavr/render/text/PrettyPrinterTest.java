/*                        __    __  __  __    __  ___
 *                       \  \  /  /    \  \  /  /  __/
 *                        \  \/  /  /\  \  \/  /  /
 *                         \____/__/  \__\____/__/.ɪᴏ
 * ᶜᵒᵖʸʳᶦᵍʰᵗ ᵇʸ ᵛᵃᵛʳ ⁻ ˡᶦᶜᵉⁿˢᵉᵈ ᵘⁿᵈᵉʳ ᵗʰᵉ ᵃᵖᵃᶜʰᵉ ˡᶦᶜᵉⁿˢᵉ ᵛᵉʳˢᶦᵒⁿ ᵗʷᵒ ᵈᵒᵗ ᶻᵉʳᵒ
 */
package io.vavr.render.text;

import io.vavr.collection.Tree;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PrettyPrinterTest {

    @Test
    public void shouldPrettyPrintTree() {

        final Tree<String> empty = Tree.empty();
        assertThat(PrettyPrinter.pp(empty)).isEqualTo("▣");

        final Tree<String> tree1 = Tree.of("TEST");
        assertThat(PrettyPrinter.pp(tree1)).isEqualTo("TEST");

        final Tree<String> ast =
                Tree.of("IfExp",
                        Tree.of("BoolExp: false"),
                        Tree.of("IntExp: 10"),
                        Tree.of("IntExp: 20"));

        assertThat(PrettyPrinter.pp(ast)).isEqualTo("IfExp\n" +
                                                    "├──BoolExp: false\n" +
                                                    "├──IntExp: 10\n" +
                                                    "└──IntExp: 20");

        final Tree<String> persons =
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

        assertThat(PrettyPrinter.pp(persons)).isEqualTo("Ann\n" +
                                                        "├──Mary\n" +
                                                        "│  ├──John\n" +
                                                        "│  │  └──Avila\n" +
                                                        "│  ├──Karen\n" +
                                                        "│  │  └──Frank\n" +
                                                        "│  └──Steven\n" +
                                                        "│     Abbot\n" +
                                                        "│     Braddock\n" +
                                                        "├──Peter\n" +
                                                        "│  ├──Paul\n" +
                                                        "│  │  Palucci\n" +
                                                        "│  └──Anthony\n" +
                                                        "└──Christopher\n" +
                                                        "   └──Samuel");



    }

}
