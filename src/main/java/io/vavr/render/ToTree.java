package io.vavr.render;

import io.vavr.collection.Tree;

public interface ToTree<E> {
    Tree.Node<E> toTree();
}
