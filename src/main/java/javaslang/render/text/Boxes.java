package javaslang.render.text;

import javaslang.collection.List;
import javaslang.collection.Tree;

import java.util.Objects;

public final class Boxes {

    private Boxes() {
    }

    public static <T> Box box(Tree<T> tree) {
        return box(tree, true);
    }

    private static <T> Box box(Tree<T> tree, boolean isRoot) {
        Objects.requireNonNull(tree, "tree is null");
        if (tree.isEmpty()) {
            return Box.of("▣");
        }
        final Box b = Box.of(tree.getValue().toString()).frame(isRoot, tree.isLeaf());
        if (tree.isLeaf()) {
            return b;
        }
        final List<Box> lst = tree.getChildren().map(t -> box(t, false));
        return b.connect(lst);
    }
}
