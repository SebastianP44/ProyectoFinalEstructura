package views;

import controllers.MapController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainFrame extends JFrame {

    private MapPanl mapPanel;
    private MapController controller;
    private JLabel lblInformacion;

    public MainFrame() {

        setTitle(
                "Mapa Interactivo - Grafos"
        );

        setSize(
                1000,
                750
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(
                null
        );

        setLayout(
                new BorderLayout()
        );

        mapPanel =
                new MapPanl(
                        "map.png"
                );

        add(
                mapPanel,
                BorderLayout.CENTER
        );

        lblInformacion =
                new JLabel(
                        "Selecciona una opción del menú para comenzar..."
                );

        lblInformacion.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        lblInformacion.setOpaque(
                true
        );

        lblInformacion.setBackground(
                new Color(
                        245,
                        245,
                        245
                )
        );

        lblInformacion.setBorder(
                BorderFactory.createEmptyBorder(
                        8,
                        12,
                        8,
                        12
                )
        );

        add(
                lblInformacion,
                BorderLayout.SOUTH
        );

        controller =
                new MapController(
                        this
                );

        JMenuBar menuBar =
                new JMenuBar();

        JMenu menu =
                new JMenu(
                        "Opciones"
                );

        JMenuItem agregarUnaArista =
                new JMenuItem(
                        "Agregar Nodo y Arista Unidireccional"
                );

        JMenuItem agregarDosAristas =
                new JMenuItem(
                        "Agregar Nodo y Arista Bidireccional"
                );

        JMenuItem buscarBfs =
                new JMenuItem(
                        "Buscar en amplitud BFS"
                );

        JMenuItem buscarDfs =
                new JMenuItem(
                        "Buscar en profundidad DFS"
                );

        JMenuItem borrarNodo =
                new JMenuItem(
                        "Borrar nodo"
                );

        JMenuItem guardarGrafo =
                new JMenuItem(
                        "Guardar grafo"
                );

        JMenuItem cargarGrafo =
                new JMenuItem(
                        "Cargar grafo"
                );

        JMenuItem salir =
                new JMenuItem(
                        "Salir"
                );

        menu.add(
                agregarUnaArista
        );

        menu.add(
                agregarDosAristas
        );

        menu.add(
                buscarBfs
        );

        menu.add(
                buscarDfs
        );

        menu.add(
                borrarNodo
        );

        menu.addSeparator();

        menu.add(
                guardarGrafo
        );

        menu.add(
                cargarGrafo
        );

        menu.addSeparator();

        menu.add(
                salir
        );

        menuBar.add(
                menu
        );

        setJMenuBar(
                menuBar
        );

        agregarUnaArista.addActionListener(
                e ->
                        controller
                                .activarAgregarAristaUni()
        );

        agregarDosAristas.addActionListener(
                e ->
                        controller
                                .activarAgregarAristaBi()
        );

        buscarBfs.addActionListener(
                e ->
                        controller
                                .activarBusquedaBFS()
        );

        buscarDfs.addActionListener(
                e ->
                        controller
                                .activarBusquedaDFS()
        );

        borrarNodo.addActionListener(
                e ->
                        controller
                                .activarBorrarNodo()
        );

        guardarGrafo.addActionListener(
                e ->
                        controller
                                .guardarGrafo()
        );

        cargarGrafo.addActionListener(
                e ->
                        controller
                                .cargarGrafo()
        );

        salir.addActionListener(
                e ->
                        System.exit(
                                0
                        )
        );

        mapPanel.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        controller
                                .manejarClicEnMapa(
                                        e.getX(),
                                        e.getY()
                                );
                    }
                }
        );

        setVisible(
                true
        );
    }

    public MapPanl getMapPanel() {

        return mapPanel;
    }

    public JLabel getLblInformacion() {

        return lblInformacion;
    }
}