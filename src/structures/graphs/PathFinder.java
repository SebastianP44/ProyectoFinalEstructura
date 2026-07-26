package structures.graphs;
//Interface
//-Contiene la logica interna
//Define los metodos 
//-No se permite instanciar 

import java.nio.file.Path;

public interface PathFinder<T> {
    PathResult<T>find(Graph <T> graph, T start, T end);
        

}
