package views;

import java.awt.Graphics;      // Importación requerida para el contexto de gráficos 
import java.awt.Image;         // Importación requerida para el objeto imagen 
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MapPanl extends JPanel {

    private Image imagen;

    // El constructor debe llamarse igual que la clase: MapPanl 🏗️
    public MapPanl(String rutaImagen) {
        // Cargar la imagen y extraer el objeto Image
        this.imagen = new ImageIcon(rutaImagen).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Garantizar la limpieza y renderizado base del panel 
        super.paintComponent(g);

        // Validar existencia de la imagen antes de renderizar 
        if (imagen != null) {
            // Renderizar adaptando ancho y alto a las dimensiones actuales del panel 
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
}