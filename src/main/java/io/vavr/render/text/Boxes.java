/*                        __    __  __  __    __  ___
 *                       \  \  /  /    \  \  /  /  __/
 *                        \  \/  /  /\  \  \/  /  /
 *                         \____/__/  \__\____/__/.ɪᴏ
 * ᶜᵒᵖʸʳᶦᵍʰᵗ ᵇʸ ᵛᵃᵛʳ ⁻ ˡᶦᶜᵉⁿˢᵉᵈ ᵘⁿᵈᵉʳ ᵗʰᵉ ᵃᵖᵃᶜʰᵉ ˡᶦᶜᵉⁿˢᵉ ᵛᵉʳˢᶦᵒⁿ ᵗʷᵒ ᵈᵒᵗ ᶻᵉʳᵒ
 */
package io.vavr.render.text;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Tree;
import io.vavr.collection.Tree.Node;

import java.util.Objects;

public final class Boxes {

    private Boxes() {
    }

    public static <T> Box box(Tree<T> tree) {
        Objects.requireNonNull(tree, "tree is null");
        if (tree.isEmpty())
            return Box.of("▣");
        Tuple2<Box, Integer> x = nodeToBox((Node<T>) tree);
        return Box.col(Box.row(Box.of('┌'), Box.of('─', x._2-2, 1), Box.of('┐')), x._1);
    }

    private static <T> Tuple2<Box, Integer> nodeToBox(Node<T> tree) {
        Box b = Box.of(tree.getValue().toString());
        Box side = Box.of('│', 1, b.height());
        b = Box.row(side, b, side);
        int b_width = b.width();
        if (tree.isLeaf()) {
            b = Box.col(b, Box.row(Box.of('└'), Box.of('─', b_width - 2, 1), Box.of('┘')));
            return Tuple.of(b, b_width);
        }
        List<Tuple2<Box,Integer>> children = tree.getChildren().map(t -> nodeToBox(t));
        if (children.length() == 1) {
            Tuple2<Box,Integer> c = children.head();
            Box c_box = c._1;
            int c_width = c_box.width();
            int c_node_width = c._2;
            b = Box.col(b, Box.of(mkheader(b_width, '└', '─', '┬', '┘', b_width < c_width ? 0 : 1)));
            c_box = Box.col(Box.of(mkheader(c_node_width, '┌', '─', '┴', '┐', c_width < b_width ? 0 : 1)), c_box);
            Box z = Box.col(b, Box.of('│'), c_box);
            return Tuple.of(z, b_width);
        }
        b = Box.col(b, Box.of(mkheader(b_width, '└', '─', '┬', '┘', 0)));
        List<Box> lst = children.map(p -> {
            Box k = p._1;
            int m = p._2;
            k = Box.col(Box.of(mkheader(m, '┌', '─', '┴', '┐', 0)), k);
            return k;
        });
        b = b.connect(lst);
        return Tuple.of(b, b_width);
    }

    private static String mkheader(int width, char a, char m1, char m2, char z, int control) {
        StringBuilder builder = new StringBuilder();
        builder.append(a);
        for (int i = 1; i < width - 1; i++)
            builder.append(m1);
        builder.append(z);
        builder.setCharAt((width-control)/2, m2);
        return builder.toString();
    }
}
