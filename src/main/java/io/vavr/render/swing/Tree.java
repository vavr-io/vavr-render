/*                        __    __  __  __    __  ___
 *                       \  \  /  /    \  \  /  /  __/
 *                        \  \/  /  /\  \  \/  /  /
 *                         \____/__/  \__\____/__/.ɪᴏ
 * ᶜᵒᵖʸʳᶦᵍʰᵗ ᵇʸ ᵛᵃᵛʳ ⁻ ˡᶦᶜᵉⁿˢᵉᵈ ᵘⁿᵈᵉʳ ᵗʰᵉ ᵃᵖᵃᶜʰᵉ ˡᶦᶜᵉⁿˢᵉ ᵛᵉʳˢᶦᵒⁿ ᵗʷᵒ ᵈᵒᵗ ᶻᵉʳᵒ
 */
package io.vavr.render.swing;

import javax.swing.tree.DefaultMutableTreeNode;

public class Tree {

    public static <T> DefaultMutableTreeNode create(io.vavr.collection.Tree<T> tree) {
        DefaultMutableTreeNode mt = new DefaultMutableTreeNode(tree.get());
        for (io.vavr.collection.Tree.Node<T> child : tree.getChildren())
            mt.add(create(child));
        return mt;
    }

}
