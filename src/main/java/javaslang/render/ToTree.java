package javaslang.render;

import javaslang.collection.Tree;

public interface ToTree<E> {
    public abstract Tree.Node<E> toTree();
}
