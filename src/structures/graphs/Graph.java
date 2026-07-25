package structures.graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import structures.node.Node;

public class Graph<T> {

    private final Map<Node<T>, Set<Node<T>>> graph;

    public Graph() {
        this.graph = new HashMap<>();
    }

    // Agregar un nodo individual 📍
    public void add(T data) {
        Node<T> node = new Node<>(data);
        graph.putIfAbsent(node, new HashSet<>());
    }

    // Arista Bidireccional (Grafo No Dirigido) 🔄
    public void addEdge(T v1, T v2) {
        Node<T> nv1 = new Node<>(v1);
        Node<T> nv2 = new Node<>(v2);
        add(v1);
        add(v2);
        graph.get(nv1).add(nv2);
        graph.get(nv2).add(nv1);
    }

    // Arista Unidireccional (Grafo Dirigido) ➡️ (Nombre corregido a camelCase)
    public void addEdgeUni(T v1, T v2) {
        Node<T> nv1 = new Node<>(v1);
        Node<T> nv2 = new Node<>(v2);
        add(v1);
        add(v2);
        graph.get(nv1).add(nv2);
    }

    // Obtener los vecinos de un nodo dado 👥
    public Set<Node<T>> getVecinos(T data) {
        return graph.getOrDefault(
                new Node<>(data),
                new HashSet<>()
        );
    }

    // Método para obtener todos los nodos (útil para iteraciones o BFS/DFS) 🗺️
    public Set<Node<T>> getNodos() {
        return graph.keySet();
    }

    // Imprimir representación del grafo 🖨️
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