package controllers;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import structures.graphs.Graph;
import structures.graphs.PathFinder;
import structures.graphs.PathResult;
import structures.node.Node;

public class DFSPathFinder<T> implements PathFinder<T> {

    @Override
    public PathResult<T> find(Graph<T> graph, T start, T end) {
        Set<T> visited = new LinkedHashSet<>();
        List<T> path = new ArrayList<>(); // Cambiado a List para manejar el índice en el backtracking 

        boolean encontrado = dfs(graph, start, end, visited, path);

        if (!encontrado) {
            path.clear(); // Si no hay ruta, dejamos el path vacío 
        }

        return new PathResult<>(visited, path);
    }

    private boolean dfs(Graph<T> graph, T current, T end, Set<T> visited, List<T> path) {
        visited.add(current);
        path.add(current); // Agregamos el nodo actual al camino explorado

        // Caso base: si llegamos al destino 
        Node<T> nC = new Node<>(current);
        Node<T> nE = new Node<>(end);
        if (nC.equals(nE)) {
            return true;
        }

        // Llamada recursiva para cada vecino no visitado 
        for (Node<T> vecino : graph.getVecinos(current)) {
            T valVecino = vecino.getValue();
            if (!visited.contains(valVecino)) {
                boolean encontrado = dfs(graph, valVecino, end, visited, path);
                if (encontrado) {
                    return true; //  Si alguna rama encontró el destino, propagamos el éxito hacia arriba
                }
            }
        }

        // 🔙 Backtracking: si ningún vecino llevó al destino, sacamos el nodo actual del camino
        path.remove(path.size() - 1);
        return false;
    }
}