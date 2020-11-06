
package edu.emory.cs.graph;

import java.util.*;

public class GraphQuiz extends Graph {
    Deque<Integer> traversalStack = new ArrayDeque<>();
    Set<Integer> blockedVertices = new HashSet<>();
    int count = 0;

    public GraphQuiz(int size) { super(size); }
    public GraphQuiz(Graph g) { super(g); }

    /** @return the total number of cycles in this graph. */
    public int numberOfCycles() {
        List<Deque<Edge>> subgraphOutgoingEdges = getOutgoingEdges();
        int size = size();
        // Traverse thru all vertices so that every vertex get to be the starting vertex
        for (int startIdx = 0; startIdx < size; startIdx++) {
            // Isolate the last starting vertex
            for (Deque<Edge> vertex: subgraphOutgoingEdges) {
                int idxTmp = startIdx;
                vertex.removeIf(e -> e.getSource() < idxTmp || e.getTarget() < idxTmp);
            }
            // Start searching cycles recursively
            numberOfCyclesAux(startIdx, startIdx, subgraphOutgoingEdges);
        }
        return count;
    }

    private boolean numberOfCyclesAux(int startIdx, int currIdx, List<Deque<Edge>> allOutgoingEdges){
        boolean foundCycle = false;
        // Push the current vertex to the stack
        // Also block the vertex so that it won't appear twice in the stack
        traversalStack.push(currIdx);
        blockedVertices.add(currIdx);
        // Traverse thru all the outgoing edges of the current vertex
        Deque<Edge> currOutGoingEdges = allOutgoingEdges.get(currIdx);
        for (Edge e: currOutGoingEdges) {
            int neighborIdx = e.getTarget();
            // If the neighbor is the starting vertex, a cycle is found
            // Else, if the neighbor is not blocked, keep on searching recursively
            if (neighborIdx == startIdx) {
                foundCycle = true;
                count++;
            } else if (!blockedVertices.contains(neighborIdx)) {
                // Recursive call
                boolean foundCycleLater = numberOfCyclesAux(startIdx, neighborIdx, allOutgoingEdges);
                 if (foundCycle || foundCycleLater) foundCycle = true;
            }
        }
        // Unblock the current index so that it doesn't get in the way of future cycles.
        blockedVertices.remove(currIdx);
        // Backtracking
        traversalStack.pop();
        return foundCycle;
    }
}
