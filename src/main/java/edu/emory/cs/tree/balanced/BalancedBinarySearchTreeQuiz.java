
package edu.emory.cs.tree.balanced;

import edu.emory.cs.tree.BinaryNode;

public class BalancedBinarySearchTreeQuiz<T extends Comparable<T>> extends AbstractBalancedBinarySearchTree<T, BinaryNode<T>> {
    @Override
    public BinaryNode<T> createNode(T key) {
        return new BinaryNode<>(key);
    }

    // Check if ALL the conditions are met (return false if not)
    private boolean checkConditions(BinaryNode<T> node) {
        if (node == null) return false;
        BinaryNode<T> grandParent = node.getGrandParent();
        BinaryNode<T> parent = node.getParent();
        BinaryNode<T> uncle = node.getUncle();
        // Check if the node has ALL of its required family members
        if (node.hasParent() && grandParent != null && uncle != null) {
            // False if have sibling
            if (node.getSibling() != null)
                return false;
            // False if parent is not the right child
            if (parent != grandParent.getRightChild())
                return false;
            // False if uncle only have one Child or don't have a child
            return uncle.hasLeftChild() != uncle.hasRightChild();
        } else return false;
    }

    // Check if the case is NOT case 4 (return true if not)
    private boolean checkIfNotCase4(BinaryNode<T> node, int leftOrRight) {
        if (leftOrRight > 0) {
            return (node.getRightChild() == null);
        } else {
            return (node.getLeftChild() == null);
        }
    }

    @Override
    protected void balance(BinaryNode<T> node) {
        if (!checkConditions(node)) return;
        BinaryNode<T> grandParent = node.getGrandParent();
        BinaryNode<T> parent = node.getParent();
        BinaryNode<T> uncle = node.getUncle();

        // Change case 2 / case 3 to case 4 / case 1
        if (checkIfNotCase4(uncle, -1)) {
            rotateLeft(uncle);        // Case 2 or 3
        }

        // Change case 1 to case 4
        if (checkIfNotCase4(parent, 1)) {
            rotateRight(parent);        // Case 1
        }

        // Rotate grandparent twice
        rotateLeft(grandParent);
        rotateRight(grandParent);
    }

//    public static void testBalancedBinarySearchTreeQuiz(BalancedBinarySearchTreeQuiz<Integer> tree) {
//        tree.add(5);
//        tree.add(3);
//        tree.add(1);
//        tree.add(7);
//        tree.add(8);
//        tree.add(2);
//        tree.add(4);
//        System.out.println(tree.toString());
//    }
//
//    public static void main(String[] args) {
//        BalancedBinarySearchTreeQuiz<Integer> tree = new BalancedBinarySearchTreeQuiz<>();
//        testBalancedBinarySearchTreeQuiz(tree);
//    }
}
