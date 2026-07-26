package controllers;

import java.util.*;
import structures.graphs.Graph;
import structures.graphs.PathFinder;
import structures.graphs.PathResult;
import structures.node.Node;

public class BFSPathFinder<T> implements PathFinder<T> {

    @Override
    public PathResult<T> find(Graph<T> graph, T start, T end) {
        Queue<T> queue = new LinkedList<>();
        Set<T> visitados = new LinkedHashSet<>();
        Map<Node<T>, Node<T>> parent = new HashMap<>();

        queue.add(start);
        visitados.add(start);
        parent.put(new Node<>(start), null);

        while (!queue.isEmpty()) {
            T current = queue.poll();

            if (current.equals(end)) {
                return new PathResult<>(visitados, buildPath(parent, end));
            }

            for (Node<T> vecino : graph.getVecinos(current)) {
                T valVecino = vecino.getValue();
                if (!visitados.contains(valVecino)) {
                    visitados.add(valVecino);
                    parent.put(vecino, new Node<>(current));
                    queue.add(valVecino);
                }
            }
        }

        // Si no encontró camino, retorna los visitados y una lista vacía 
        return new PathResult<>(visitados, new ArrayList<>());
    }

    private List<T> buildPath(Map<Node<T>, Node<T>> parent, T end) {
        List<T> path = new ArrayList<>();
        Node<T> nEnd = new Node<>(end);

        // Reconstrucción del camino desde el final hacia el inicio 🔙
        for (Node<T> at = nEnd; at != null; at = parent.get(at)) {
            path.add(at.getValue());
        }

        // Invertimos para que quede desde el Inicio -> Fin 
        Collections.reverse(path);
        return path;
    }
}
