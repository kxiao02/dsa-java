package edu.emory.cs.graph.span;

public class Bee {
    public static void main(String[] args) {
        // It's a bee
        Graph bee = new Graph(26);
        bee.setUndirectedEdge(0, 1, 1);
        bee.setUndirectedEdge(1, 2, 1);
        bee.setUndirectedEdge(2, 3, 1);
        bee.setUndirectedEdge(3, 4, 1);
        bee.setUndirectedEdge(4, 5, 2);
        bee.setUndirectedEdge(5, 6, 2);
        bee.setUndirectedEdge(6, 7, 1);
        bee.setUndirectedEdge(7, 8, 2);
        bee.setUndirectedEdge(8, 9, 2);
        bee.setUndirectedEdge(9, 10, 1);
        bee.setUndirectedEdge(10, 11, 2);
        bee.setUndirectedEdge(11, 12, 2);
        bee.setUndirectedEdge(12, 13, 1);
        bee.setUndirectedEdge(12, 14, 2);
        bee.setUndirectedEdge(14, 15, 2);
        bee.setUndirectedEdge(15, 16, 1);
        bee.setUndirectedEdge(16, 17, 2);
        bee.setUndirectedEdge(17, 18, 2);
        bee.setUndirectedEdge(18, 19, 1);
        bee.setUndirectedEdge(19, 20, 2);
        bee.setUndirectedEdge(20, 21, 2);
        bee.setUndirectedEdge(21, 22, 1);
        bee.setUndirectedEdge(22, 23, 1);
        bee.setUndirectedEdge(24, 25, 1);
        bee.setUndirectedEdge(2, 23, 1);
        bee.setUndirectedEdge(4, 21, 1);
        bee.setUndirectedEdge(5, 20, 2);
        bee.setUndirectedEdge(8, 17, 2);
        bee.setUndirectedEdge(11, 14, 2);
        bee.setUndirectedEdge(6, 8, 2.65);
        bee.setUndirectedEdge(10, 8, 2.65);
        bee.setUndirectedEdge(19, 17, 2.65);
        bee.setUndirectedEdge(15, 17, 2.65);
    }
}
