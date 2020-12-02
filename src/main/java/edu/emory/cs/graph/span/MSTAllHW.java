
package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;

import java.util.*;

public class MSTAllHW implements MSTAll {

    interface OnDeal {
        void deal(Deque<Integer> path);
    }

    // find all combinations of V-1 edges using dft
    public void dft(int n, int k, OnDeal onDeal) {
        Deque<Integer> path = new ArrayDeque<>();
        dftAux(n, k, 1, path, onDeal);
    }

    private void dftAux(int n, int k, int index, Deque<Integer> path, OnDeal onDeal) {
        if (path.size() == k) {
            onDeal.deal(path);
            return;
        }
        for (int i = index; i <= n - (k - path.size()) + 1; i++) {
            path.add(i);
            dftAux(n, k, i + 1, path, onDeal);
            path.removeLast();
        }
    }

    private boolean isValidST(Graph graph) {
        Queue<Integer> queue = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();

        queue.add(0);
        while (!queue.isEmpty()) {
            int target = queue.poll();
            visited.add(target);
            for (Edge edge : graph.getIncomingEdges(target)) {
                if (!visited.contains(edge.getSource())) queue.add(edge.getSource());
            }
        }
        return visited.size() == graph.size();
    }

    // randomly pick V-1 edges from E edges
    // check if the V-1 edges makes a valid ST
    // if valid ST, check total weight
    // if equal, count++;
    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        MSTPrim mstPrim = new MSTPrim();
        SpanningTree spanningTree = mstPrim.getMinimumSpanningTree(graph);
        // get the minimum total weight with Prim/Kruskal
        double MSTTotalWeight = spanningTree.getTotalWeight();
        // get all edges pointing from small to large as the undirected edges
        List<Edge> allEdges = new ArrayList<>();
        for (Edge edge : graph.getAllEdges()) {
            if (edge.getSource() > edge.getTarget()) continue;
            allEdges.add(edge);
        }

        List<SpanningTree> result = new ArrayList<>();

        dft(allEdges.size(), graph.size() - 1, path -> {
            // check if ST is valid
            double weights = 0;
            Graph tmpGraph = new Graph(graph.size());
            for (int index : path) {
                tmpGraph.setUndirectedEdge(allEdges.get(index - 1).getSource(),
                        allEdges.get(index - 1).getTarget(), allEdges.get(index - 1).getWeight());
                weights += allEdges.get(index - 1).getWeight();
                if (weights > MSTTotalWeight) break;
            }

            // if valid ST && total weight == MSTTotalWeight, add the ST to result
            if (isValidST(tmpGraph) && weights == MSTTotalWeight) {
                SpanningTree st = new SpanningTree();
                for (int index : path) st.addEdge(allEdges.get(index - 1));
                result.add(st);
            }
        });
        return result;
    }
}
