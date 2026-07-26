
import javax.swing.SwingUtilities;

import views.MainFrame;

public class App {
    public static void main(String[] args) {
        // Lanzar la interfaz gráfica en el hilo de Swing 
        SwingUtilities.invokeLater(() -> {
            new MainFrame();// Al instanciarlo, el constructor construye y muestra la ventana 
            
        });
    }
}
