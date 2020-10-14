
package edu.emory.cs.tree.balanced;

import edu.emory.cs.tree.AbstractBinaryNode;
import edu.emory.cs.tree.AbstractBinarySearchTree;

public abstract class AbstractBalancedBinarySearchTree<T extends Comparable<T>, N extends AbstractBinaryNode<T, N>> extends AbstractBinarySearchTree<T, N> {
    protected void rotateLeft(N node) {
        N child = node.getRightChild();
        node.setRightChild(child.getLeftChild());

        if (node.hasParent())
            node.getParent().replaceChild(node, child);
        else
            setRoot(child);

        child.setLeftChild(node);
    }

    protected void rotateRight(N node) {
        N child = node.getLeftChild();
        node.setLeftChild(child.getRightChild());

        if (node.hasParent())
            node.getParent().replaceChild(node, child);
        else
            setRoot(child);

        child.setRightChild(node);
    }

//	============================== Override ==============================

    @Override
    public N add(T key) {
        N node = super.add(key);
        balance(node);
        return node;
    }

    @Override
    public N remove(T key) {
        N node = findNode(root, key);

        if (node != null) {
            N lowest = node.hasBothChildren() ? removeHibbard(node) : removeSelf(node);
            if (lowest != null && lowest != node) balance(lowest);
        }

        return node;
    }

    protected abstract void balance(N node);
}
