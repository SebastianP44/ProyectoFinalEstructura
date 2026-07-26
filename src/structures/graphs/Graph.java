package structures.graphs;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import structures.node.Node;

public class Graph<T> {

    private final Map<Node<T>, Set<Node<T>>> graph;

    public Graph() {
        this.graph = new LinkedHashMap<>();
    }

    public void add(T data) {
        Node<T> node = new Node<>(data);
        graph.putIfAbsent(node, new LinkedHashSet<>());
    }

    public void addEdge(T v1, T v2) {
        Node<T> nv1 = new Node<>(v1);
        Node<T> nv2 = new Node<>(v2);

        add(v1);
        add(v2);

        graph.get(nv1).add(nv2);
        graph.get(nv2).add(nv1);
    }

    public void addEdgeUni(T v1, T v2) {
        Node<T> nv1 = new Node<>(v1);
        Node<T> nv2 = new Node<>(v2);

        add(v1);
        add(v2);

        graph.get(nv1).add(nv2);
    }

    public Set<Node<T>> getVecinos(T data) {
        return graph.getOrDefault(
                new Node<>(data),
                new LinkedHashSet<>()
        );
    }

    public Set<Node<T>> getNodos() {
        return graph.keySet();
    }

    public void remove(T data) {
        Node<T> node = new Node<>(data);

        graph.remove(node);

        for (Set<Node<T>> vecinos : graph.values()) {
            vecinos.remove(node);
        }
    }

    public void removeEdge(T v1, T v2) {
        Node<T> n1 = new Node<>(v1);
        Node<T> n2 = new Node<>(v2);

        if (graph.containsKey(n1)) {
            graph.get(n1).remove(n2);
        }

        if (graph.containsKey(n2)) {
            graph.get(n2).remove(n1);
        }
    }

    public void removeEdgeUni(T v1, T v2) {
        Node<T> n1 = new Node<>(v1);
        Node<T> n2 = new Node<>(v2);

        if (graph.containsKey(n1)) {
            graph.get(n1).remove(n2);
        }
    }

    public void printGraph() {
        for (Map.Entry<Node<T>, Set<Node<T>>> entry : graph.entrySet()) {
            System.out.print(entry.getKey() + " -> ");

            for (Node<T> neighbor : entry.getValue()) {
                System.out.print(neighbor + " ");
            }

            System.out.println();
        }
    }
}