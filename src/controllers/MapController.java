package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import models.MapPoint;
import persistence.FileGraphRepository;
import persistence.GraphRepository;
import structures.graphs.Graph;
import structures.graphs.PathResult;
import structures.node.Node;
import views.MainFrame;

public class MapController {

    public enum ModoAccion {
        NINGUNO,
        AGREGAR_ARISTA_UNI,
        AGREGAR_ARISTA_BI,
        SELECCIONAR_BFS,
        SELECCIONAR_DFS,
        BORRAR_NODO
    }

    private final Graph<MapPoint> grafo;
    private final BFSPathFinder<MapPoint> bfsPathFinder;
    private final DFSPathFinder<MapPoint> dfsPathFinder;
    private final MainFrame mainFrame;
    private final List<MapPoint> listaPuntos;
    private final List<MapPoint[]> conexiones;
    private final GraphRepository graphRepository;

    private ModoAccion modoActual;
    private MapPoint nodoSeleccionadoOrigen;

    private final String RUTA_ARCHIVO = "grafo.csv";

    public MapController(MainFrame mainFrame) {

        this.mainFrame = mainFrame;
        this.grafo = new Graph<>();
        this.bfsPathFinder = new BFSPathFinder<>();
        this.dfsPathFinder = new DFSPathFinder<>();
        this.listaPuntos = new ArrayList<>();
        this.conexiones = new ArrayList<>();
        this.graphRepository = new FileGraphRepository();
        this.modoActual = ModoAccion.NINGUNO;
    }

    public void activarAgregarAristaUni() {

        this.modoActual =
                ModoAccion.AGREGAR_ARISTA_UNI;

        this.nodoSeleccionadoOrigen = null;

        mainFrame.getLblInformacion()
                .setText(
                        "Modo activo: Agregar Arista Unidireccional"
                );
    }

    public void activarAgregarAristaBi() {

        this.modoActual =
                ModoAccion.AGREGAR_ARISTA_BI;

        this.nodoSeleccionadoOrigen = null;

        mainFrame.getLblInformacion()
                .setText(
                        "Modo activo: Agregar Arista Bidireccional"
                );
    }

    public void activarBusquedaBFS() {

        this.modoActual =
                ModoAccion.SELECCIONAR_BFS;

        this.nodoSeleccionadoOrigen = null;

        mainFrame.getLblInformacion()
                .setText(
                        "Modo activo: Buscar en amplitud. Selecciona origen y destino."
                );
    }

    public void activarBusquedaDFS() {

        this.modoActual =
                ModoAccion.SELECCIONAR_DFS;

        this.nodoSeleccionadoOrigen = null;

        mainFrame.getLblInformacion()
                .setText(
                        "Modo activo: Buscar en profundidad. Selecciona origen y destino."
                );
    }

    public void activarBorrarNodo() {

        this.modoActual =
                ModoAccion.BORRAR_NODO;

        this.nodoSeleccionadoOrigen = null;

        mainFrame.getLblInformacion()
                .setText(
                        "Modo activo: Borrar Nodo"
                );
    }

    public void manejarClicEnMapa(
            int x,
            int y
    ) {

        MapPoint puntoCercano =
                buscarNodoCercano(
                        x,
                        y,
                        20
                );

        switch (modoActual) {

            case AGREGAR_ARISTA_UNI:
            case AGREGAR_ARISTA_BI:

                procesarCreacionOConexion(
                        x,
                        y,
                        puntoCercano
                );

                break;

            case SELECCIONAR_BFS:
            case SELECCIONAR_DFS:

                procesarBusqueda(
                        puntoCercano
                );

                break;

            case BORRAR_NODO:

                if (puntoCercano != null) {

                    listaPuntos.remove(
                            puntoCercano
                    );

                    conexiones.removeIf(
                            par ->
                                    par[0].equals(
                                            puntoCercano
                                    )
                                    ||
                                    par[1].equals(
                                            puntoCercano
                                    )
                    );

                    grafo.remove(
                            puntoCercano
                    );

                    mainFrame
                            .getLblInformacion()
                            .setText(
                                    "Nodo eliminado: "
                                    + puntoCercano.getId()
                            );

                    actualizarMapa();
                }

                break;

            default:
                break;
        }
    }

    private void procesarCreacionOConexion(
            int x,
            int y,
            MapPoint puntoCercano
    ) {

        if (puntoCercano == null) {

            String id =
                    JOptionPane.showInputDialog(
                            mainFrame,
                            "Ingrese el ID/Nombre del Nodo:"
                    );

            if (
                    id != null
                    &&
                    !id.trim().isEmpty()
            ) {

                MapPoint nuevoPunto =
                        new MapPoint(
                                id.trim(),
                                x,
                                y
                        );

                listaPuntos.add(
                        nuevoPunto
                );

                grafo.add(
                        nuevoPunto
                );

                if (
                        nodoSeleccionadoOrigen
                        != null
                ) {

                    conectarNodos(
                            nodoSeleccionadoOrigen,
                            nuevoPunto
                    );
                }

                nodoSeleccionadoOrigen =
                        nuevoPunto;

                actualizarMapa();
            }

        } else {

            if (
                    nodoSeleccionadoOrigen
                    == null
            ) {

                nodoSeleccionadoOrigen =
                        puntoCercano;

                mainFrame
                        .getLblInformacion()
                        .setText(
                                "Origen: "
                                + puntoCercano.getId()
                                + ". Haz clic en otro nodo."
                        );

            } else if (
                    !nodoSeleccionadoOrigen
                            .equals(
                                    puntoCercano
                            )
            ) {

                conectarNodos(
                        nodoSeleccionadoOrigen,
                        puntoCercano
                );

                nodoSeleccionadoOrigen =
                        null;
            }

            actualizarMapa();
        }
    }

    private void conectarNodos(
            MapPoint origen,
            MapPoint destino
    ) {

        if (
                modoActual
                ==
                ModoAccion.AGREGAR_ARISTA_UNI
        ) {

            grafo.addEdgeUni(
                    origen,
                    destino
            );

            conexiones.add(
                    new MapPoint[]{
                        origen,
                        destino
                    }
            );

            mainFrame
                    .getLblInformacion()
                    .setText(
                            "Arista creada: "
                            + origen.getId()
                            + " → "
                            + destino.getId()
                    );

        } else if (
                modoActual
                ==
                ModoAccion.AGREGAR_ARISTA_BI
        ) {

            grafo.addEdge(
                    origen,
                    destino
            );

            conexiones.add(
                    new MapPoint[]{
                        origen,
                        destino
                    }
            );

            mainFrame
                    .getLblInformacion()
                    .setText(
                            "Arista creada: "
                            + origen.getId()
                            + " ↔ "
                            + destino.getId()
                    );
        }
    }

    private void procesarBusqueda(
            MapPoint puntoCercano
    ) {

        if (
                puntoCercano
                == null
        ) {
            return;
        }

        if (
                nodoSeleccionadoOrigen
                == null
        ) {

            nodoSeleccionadoOrigen =
                    puntoCercano;

            mainFrame
                    .getLblInformacion()
                    .setText(
                            "Origen: "
                            + puntoCercano.getId()
                            + ". Selecciona el destino."
                    );

        } else {

            MapPoint nodoDestino =
                    puntoCercano;

            PathResult<MapPoint> resultado;

            if (
                    modoActual
                    ==
                    ModoAccion.SELECCIONAR_BFS
            ) {

                resultado =
                        bfsPathFinder.find(
                                grafo,
                                nodoSeleccionadoOrigen,
                                nodoDestino
                        );

            } else {

                resultado =
                        dfsPathFinder.find(
                                grafo,
                                nodoSeleccionadoOrigen,
                                nodoDestino
                        );
            }

            if (
                    resultado
                    != null
            ) {

                mainFrame
                        .getLblInformacion()
                        .setText(
                                "<html><b>Ruta:</b> "
                                + resultado.getPath()
                                + " | <b>Visitados:</b> "
                                + resultado.getVisitados()
                                + "</html>"
                        );

                mainFrame
                        .getMapPanel()
                        .actualizarDatosDibujo(
                                listaPuntos,
                                resultado.getPath(),
                                conexiones
                        );
            }

            nodoSeleccionadoOrigen =
                    null;
        }
    }

    private MapPoint buscarNodoCercano(
            int x,
            int y,
            double radioTolerancia
    ) {

        for (
                MapPoint p
                : listaPuntos
        ) {

            double distancia =
                    Math.sqrt(
                            Math.pow(
                                    p.getX()
                                    - x,
                                    2
                            )
                            +
                            Math.pow(
                                    p.getY()
                                    - y,
                                    2
                            )
                    );

            if (
                    distancia
                    <= radioTolerancia
            ) {

                return p;
            }
        }

        return null;
    }

    public void guardarGrafo() {

        graphRepository.save(
                grafo,
                RUTA_ARCHIVO
        );

        mainFrame
                .getLblInformacion()
                .setText(
                        "Grafo guardado correctamente en grafo.csv"
                );
    }

    public void cargarGrafo() {

        Graph<MapPoint> grafoCargado =
                graphRepository.load(
                        RUTA_ARCHIVO
                );

        listaPuntos.clear();
        conexiones.clear();

        listaPuntos.addAll(
                grafoCargado
                        .getNodos()
                        .stream()
                        .map(
                                Node::getValue
                        )
                        .toList()
        );

        for (
                Node<MapPoint> nodo
                : grafoCargado.getNodos()
        ) {

            MapPoint origen =
                    nodo.getValue();

            for (
                    Node<MapPoint> vecino
                    : grafoCargado
                            .getVecinos(
                                    origen
                            )
            ) {

                MapPoint destino =
                        vecino.getValue();

                conexiones.add(
                        new MapPoint[]{
                            origen,
                            destino
                        }
                );
            }
        }

        actualizarMapa();

        mainFrame
                .getLblInformacion()
                .setText(
                        "Grafo cargado correctamente."
                );
    }

    private void actualizarMapa() {

        mainFrame
                .getMapPanel()
                .actualizarDatosDibujo(
                        listaPuntos,
                        null,
                        conexiones
                );
    }

    public Graph<MapPoint> getGrafo() {

        return grafo;
    }

    public List<MapPoint> getListaPuntos() {

        return listaPuntos;
    }
}