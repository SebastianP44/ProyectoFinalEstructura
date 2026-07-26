package persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.MapPoint;
import structures.graphs.Graph;
import structures.node.Node;

public class FileGraphRepository implements GraphRepository {

    @Override
    public void save(
            Graph<MapPoint> graph,
            String filePath
    ) {
        try (
            PrintWriter writer =
                    new PrintWriter(
                            new FileWriter(filePath)
                    )
        ) {

            writer.println("NODES");

            for (
                Node<MapPoint> node :
                graph.getNodos()
            ) {

                MapPoint point =
                        node.getValue();

                writer.println(
                        point.getId()
                        + ","
                        + point.getX()
                        + ","
                        + point.getY()
                );
            }

            writer.println("EDGES");

            for (
                Node<MapPoint> node :
                graph.getNodos()
            ) {

                MapPoint origen =
                        node.getValue();

                for (
                    Node<MapPoint> vecino :
                    graph.getVecinos(origen)
                ) {

                    MapPoint destino =
                            vecino.getValue();

                    writer.println(
                            origen.getId()
                            + ","
                            + destino.getId()
                    );
                }
            }

        } catch (IOException e) {

            System.err.println(
                    "Error al guardar el grafo: "
                    + e.getMessage()
            );
        }
    }

    @Override
    public Graph<MapPoint> load(
            String filePath
    ) {

        Graph<MapPoint> graph =
                new Graph<>();

        Map<String, MapPoint> puntos =
                new HashMap<>();

        List<String[]> aristas =
                new ArrayList<>();

        try (
            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(filePath)
                    )
        ) {

            String line;

            boolean leyendoNodos =
                    false;

            boolean leyendoAristas =
                    false;

            while (
                (line = reader.readLine())
                != null
            ) {

                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                if (line.equals("NODES")) {

                    leyendoNodos = true;
                    leyendoAristas = false;

                    continue;
                }

                if (line.equals("EDGES")) {

                    leyendoNodos = false;
                    leyendoAristas = true;

                    continue;
                }

                String[] datos =
                        line.split(",");

                if (
                    leyendoNodos
                    && datos.length == 3
                ) {

                    String id =
                            datos[0];

                    int x =
                            Integer.parseInt(
                                    datos[1]
                            );

                    int y =
                            Integer.parseInt(
                                    datos[2]
                            );

                    MapPoint point =
                            new MapPoint(
                                    id,
                                    x,
                                    y
                            );

                    puntos.put(
                            id,
                            point
                    );

                    graph.add(point);
                }

                if (
                    leyendoAristas
                    && datos.length == 2
                ) {

                    aristas.add(
                            datos
                    );
                }
            }

            for (
                String[] arista :
                aristas
            ) {

                MapPoint origen =
                        puntos.get(
                                arista[0]
                        );

                MapPoint destino =
                        puntos.get(
                                arista[1]
                        );

                if (
                    origen != null
                    && destino != null
                ) {

                    graph.addEdge(
                            origen,
                            destino
                    );
                }
            }

        } catch (
            IOException
            | NumberFormatException e
        ) {

            System.err.println(
                    "Error al cargar el grafo: "
                    + e.getMessage()
            );
        }

        return graph;
    }
}