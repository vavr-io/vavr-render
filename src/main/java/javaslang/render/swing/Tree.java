package javaslang.render.swing;

import javax.swing.tree.DefaultMutableTreeNode;

public class Tree {

    public static <T> DefaultMutableTreeNode create(javaslang.collection.Tree<T> tree) {
        DefaultMutableTreeNode mt = new DefaultMutableTreeNode(tree.get());
        for (javaslang.collection.Tree.Node<T> child : tree.getChildren())
            mt.add(create(child));
        return mt;
    }

}
