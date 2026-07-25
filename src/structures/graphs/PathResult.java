package structures.graphs;

import java.util.List;
import java.util.Set;

public class PathResult<T> {

    private final Set<T> visitados;
    private final List<T> path; // 👈 Cambiado a List<T> para preservar el ORDEN del camino 🛣️

    // Constructor 🏗️
    public PathResult(Set<T> visitados, List<T> path) {
        this.visitados = visitados;
        this.path = path;
    }

    // Getters 🔑
    public Set<T> getVisitados() {
        return visitados;
    }

    public List<T> getPath() {
        return path;
    }

    // Representación visual en consola 🖨️✨
    @Override
    public String toString() {
        boolean hayCamino = (path != null && !path.isEmpty());
        
        return "PathResult:" +
               "\n  • Visitados: " + visitados +
               "\n  • Resultado: " + (hayCamino ? "path = " + path : "No se encontró camino entre los nodos 🛑");
    }
}
