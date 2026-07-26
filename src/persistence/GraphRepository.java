package persistence;

import models.MapPoint;
import structures.graphs.Graph;

public interface GraphRepository {

    void save(Graph<MapPoint> graph, String filePath);

    Graph<MapPoint> load(String filePath);
}
