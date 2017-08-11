/*                        __    __  __  __    __  ___
 *                       \  \  /  /    \  \  /  /  __/
 *                        \  \/  /  /\  \  \/  /  /
 *                         \____/__/  \__\____/__/.ɪᴏ
 * ᶜᵒᵖʸʳᶦᵍʰᵗ ᵇʸ ᵛᵃᵛʳ ⁻ ˡᶦᶜᵉⁿˢᵉᵈ ᵘⁿᵈᵉʳ ᵗʰᵉ ᵃᵖᵃᶜʰᵉ ˡᶦᶜᵉⁿˢᵉ ᵛᵉʳˢᶦᵒⁿ ᵗʷᵒ ᵈᵒᵗ ᶻᵉʳᵒ
 */
package io.vavr.render.text;

import io.vavr.collection.List;
import io.vavr.collection.Tree;

import java.util.Objects;

public class PrettyPrinter {

    public static <T> String pp(Tree<T> tree) {
        Objects.requireNonNull(tree, "tree is null");
        if (tree.isEmpty()) { return "▣"; }
        final StringBuilder builder = new StringBuilder();
        prettyPrint((Tree.Node<T>) tree, "", "", builder);
        return builder.toString();
    }

    private static <T> void prettyPrint(Tree.Node<T> treeNode, String indent, String indent2, StringBuilder s) {
        final String[] lines = treeNode.get().toString().split("\n");
        s.append(lines[0]);
        for (int i = 1; i < lines.length; i++) {
            s.append('\n')
             .append(indent2)
             .append(lines[i]);
        }
        for (List<Tree.Node<T>> it = treeNode.getChildren(); it.nonEmpty(); it = it.tail()) {
            final boolean isLast = it.tail().isEmpty();
            s.append('\n')
             .append(indent)
             .append(isLast ? "└──" : "├──");
            prettyPrint(it.head(), indent + (isLast ? "   " : "│  "), indent + (isLast ? "   " : "│  "), s);
        }
    }

}
