package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;

import java.util.*;

public class MSTAllHW implements MSTAll {

    interface OnDeal {
        void deal(Deque<Integer> path);
    }
    public void combine(int n, int k, OnDeal onDeal) {
        Deque<Integer> path = new ArrayDeque<>();
        dfs(n, k, 1, path, onDeal);
    }

    private void dfs(int n, int k, int index, Deque<Integer> path, OnDeal onDeal) {
        if (path.size() == k) {
            onDeal.deal(path);
            return;
        }

        for (int i = index; i <= n - (k - path.size()) + 1; i++) {
            path.addLast(i);
            dfs(n, k, i + 1, path, onDeal);
            path.removeLast();
        }
    }

    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        MSTPrim mstPrim = new MSTPrim();
        SpanningTree spanningTree = mstPrim.getMinimumSpanningTree(graph);

        double miniSpanningTreeTotalWeight = spanningTree.getTotalWeight();

        List<Edge> allEdges = new ArrayList<>();
        for (Edge edge : graph.getAllEdges()) {
            if (edge.getSource() > edge.getTarget())
                continue;
            allEdges.add(edge);
        }

        Set<Integer> nodeSet = new HashSet<>();
        List<SpanningTree> result = new ArrayList<>();

        combine(allEdges.size(), graph.size() - 1, path -> {
            List<Edge> edges = new ArrayList<>();
            for (int index : path) {
                edges.add(allEdges.get(index - 1));
            }

            double weights = 0;
            nodeSet.clear();
            for(Edge edge : edges) {
                nodeSet.add(edge.getSource());
                nodeSet.add(edge.getTarget());
                weights += edge.getWeight();
                if (weights > miniSpanningTreeTotalWeight)
                    break;
            }

            if (nodeSet.size() == graph.size() && weights == miniSpanningTreeTotalWeight) {
                SpanningTree st = new SpanningTree();
                for(Edge edge : edges) {
                    st.addEdge(edge);
                }
                result.add(st);
            }
        });
        return result;
    }
}
