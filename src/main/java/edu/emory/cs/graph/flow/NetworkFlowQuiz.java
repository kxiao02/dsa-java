
package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;

import java.util.*;

public class NetworkFlowQuiz {

    int source;
    private Set<Subgraph> allPaths;

    /**
     * Using depth-first traverse.
     * @param graph  a directed graph.
     * @param source the source vertex.
     * @param target the target vertex.
     * @return a set of all augmenting paths between the specific source and target vertices in the graph.
     */
    public Set<Subgraph> getAugmentingPaths(Graph graph, int source, int target) {
        allPaths = new HashSet<>();
        this.source = source;
        List<Deque<Edge>> allOutgoingEdges = graph.getOutgoingEdges();
        // <source, outgoingEdge>
        Map<Integer, Edge> path = new HashMap<>();
        dft(allOutgoingEdges, path, source, target);
        return allPaths;
    }

    private void dft(List<Deque<Edge>> allOutgoingEdges, Map<Integer, Edge> path, int currIdx, int target) {
        // gatcha
        if(currIdx == target) {
            allPaths.add(toSubgraph(path, target));
            return;
        }
        Deque<Edge> currOutGoingEdges = allOutgoingEdges.get(currIdx);
        for(Edge e : currOutGoingEdges) {
            int neighborIdx = e.getTarget();
            Set<Integer> keys = path.keySet();
            if(keys.contains(neighborIdx)) continue; // loop
            path.put(currIdx, e);
            dft(allOutgoingEdges, path, neighborIdx, target);
            path.remove(currIdx);
        }
    }

    private Subgraph toSubgraph(Map<Integer, Edge> path, int target) {
        Subgraph sub = new Subgraph();
        int tg, i = source;
        while(i < target) {
            Edge e = path.get(i);
            sub.addEdge(e);
            tg = e.getTarget();
            i = tg;
        }
        return sub;
    }
}
