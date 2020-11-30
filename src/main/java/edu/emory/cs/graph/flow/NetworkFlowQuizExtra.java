
package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;

import java.util.*;

public class NetworkFlowQuizExtra {

    /**
     * Using breadth-first traverse.
     * @param graph  a directed graph.
     * @param source the source vertex.
     * @param target the target vertex.
     * @return a set of all augmenting paths between the specific source and target vertices in the graph.
     */
    public Set<Subgraph> getAugmentingPaths(Graph graph, int source, int target) {
        List<Deque<Edge>> allOutgoingEdges = graph.getOutgoingEdges();
        return bft(allOutgoingEdges, source, target);
    }

    private Set<Subgraph> bft(List<Deque<Edge>> allOutgoingEdges, int source, int target){
        Deque<Deque<Edge>> queue = new ArrayDeque<>();
        Deque<Edge> path = new ArrayDeque<>();
        Set<Subgraph> allPaths = new HashSet<>();
        int last = source;
        queue.add(path);
        while(!queue.isEmpty()) {
            path = queue.poll();
            if(!path.isEmpty()) last = path.peekLast().getTarget();
            if(last == target) allPaths.add(toSubgraph(path)); // gatcha
            Deque<Edge> currOutgoingEdges = allOutgoingEdges.get(last);
            for(Edge e: currOutgoingEdges) {
                int neighborIdx = e.getTarget();
                if(visited(path, neighborIdx) || visited(path, source)) continue; // loop
                Deque<Edge> newPath = new ArrayDeque<>(path);
                newPath.add(e);
                queue.add(newPath);
            }
        }
        return allPaths;
    }

    private Subgraph toSubgraph(Deque<Edge> path) {
        Subgraph sub = new Subgraph();
        for (Edge e: path)
            sub.addEdge(e);
        return sub;
    }

    private boolean visited(Deque<Edge> path, int idx) {
        for(Edge e: path) {
            if(e.getTarget() == idx) return true;
        }
        return false;
    }
}
