package javaslang.render.text;

import javaslang.collection.List;
import javaslang.collection.Tree;

import java.util.Objects;

public final class Boxes {

    private Boxes() {
    }

    public static <T> Box box(Tree<T> tree) {
        Objects.requireNonNull(tree, "tree is null");
        if (tree.isEmpty()) {
            return Box.of("▣");
        }
        final Box b = Box.of(tree.getValue().toString()).frame();
        if (tree.isLeaf()) {
            return b;
        }
        final List<Box> lst = tree.getChildren().map(Boxes::box);
        return b.connect(lst);
    }
}
