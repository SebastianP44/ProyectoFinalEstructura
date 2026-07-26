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
    private JLabel lblInformacion; // Label en el pie de página para mostrar Ruta y Visitados

    public MainFrame() {
        // Configuración de la Ventana 🪟
        setTitle("Mapa Interactivo - Grafos");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setVisible(true);

        // 1. Instanciación del Panel 🧱
        mapPanel = new MapPanl("map.png");
        add(mapPanel, BorderLayout.CENTER);

        // 2. Creación del Label Inferior 🏷️
        lblInformacion = new JLabel(" Selecciona una opción del menú para comenzar...");
        lblInformacion.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblInformacion.setOpaque(true);
        lblInformacion.setBackground(new Color(245, 245, 245));
        lblInformacion.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(lblInformacion, BorderLayout.SOUTH);

        // 3. Controlador 🎮
        controller = new MapController(this);

        // 4. Creación de Componentes del Menú 🎛️
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opciones");

        JMenuItem agregarUnaArista = new JMenuItem("Agregar una Nodo un Arista");
        JMenuItem agregarDosAristas = new JMenuItem("Agregar un Nodo con aristas de ida y vuelta");
        JMenuItem buscarBfs = new JMenuItem("Buscar en amplitud");
        JMenuItem buscarDfs = new JMenuItem("Buscar en profundidad");
        JMenuItem borrarNodo = new JMenuItem("Borrar nodo");
        JMenuItem menuItem = new JMenuItem("Salir");

        menu.add(agregarUnaArista);
        menu.add(agregarDosAristas);
        menu.add(buscarBfs);
        menu.add(buscarDfs);
        menu.add(borrarNodo);
        menu.addSeparator();
        menu.add(menuItem);
        menuBar.add(menu);

        setJMenuBar(menuBar);

        // 5. Conexión de Eventos del Menú ⚡
        agregarUnaArista.addActionListener(e -> controller.activarAgregarAristaUni());
        agregarDosAristas.addActionListener(e -> controller.activarAgregarAristaBi());
        buscarBfs.addActionListener(e -> controller.activarBusquedaBFS());
        buscarDfs.addActionListener(e -> controller.activarBusquedaDFS());
        borrarNodo.addActionListener(e -> controller.activarBorrarNodo());
        menuItem.addActionListener(e -> System.exit(0));

        // 6. Captura de Clics sobre el Mapa 🖱️📍
        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.manejarClicEnMapa(e.getX(), e.getY());
            }
        });

        setVisible(true);
    }

    // 🔑 Getters para el controlador
    public MapPanl getMapPanel() {
        return mapPanel;
    }

    public JLabel getLblInformacion() {
        return lblInformacion;
    }
}