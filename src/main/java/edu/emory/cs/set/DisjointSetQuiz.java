
package edu.emory.cs.set;

public class DisjointSetQuiz {
    static public void main(String[] args) {
        DisjointSet ds = new DisjointSet(5);
        ds.union(2,3);
        ds.union(4,3);
        ds.union(0,1);
        ds.union(1,3);
//        ds.find(0);
//        System.out.println(ds);
    }
}
