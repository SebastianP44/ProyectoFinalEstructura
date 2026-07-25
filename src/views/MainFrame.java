package views;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainFrame {

    // Constructor de la clase donde vive la lógica de inicialización 🏗️
    public MainFrame() {
        // 1. Instanciación de componentes 🧱
        JFrame frame = new JFrame("Mapa Interactivo");
        MapPanl mapPanel = new MapPanl("map.png");

        JMenu menu = new JMenu("Opciones");
        JMenuItem menuItem = new JMenuItem("Salir");
        JMenuItem agregarUnaArista = new JMenuItem("Agregar una arista");
        JMenuItem agregarDosAristas = new JMenuItem("Agregar dos aristas");
        JMenuItem buscarBfs = new JMenuItem("Buscar en amplitud");
        JMenuItem buscarDfs = new JMenuItem("Buscar en profundidad");
        JMenuBar menuBar = new JMenuBar();
        JMenuItem borrarNodo = new JMenuItem("Borrar nodo");

        // 2. Agregar ítems al menú (corrigiendo mayúsculas) 📥
        menu.add(agregarUnaArista);
        menu.add(agregarDosAristas); // 👈 Corregido: la 'A' mayúscula
        menu.add(buscarBfs);
        menu.add(buscarDfs);
        menu.add(menuItem);
        menu.add(borrarNodo);

        // 3. Ensamblar la barra de menú y el panel en el frame 🧩
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
        frame.add(mapPanel);

        // 4. Propiedades finales de la ventana 🚀
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        menuItem.addActionListener(e->{
            System.exit(0);
        });
    }
}