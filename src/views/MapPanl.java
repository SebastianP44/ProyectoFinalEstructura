package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import models.MapPoint;

public class MapPanl extends JPanel {

    private BufferedImage mapaImagen;
    private List<MapPoint> listaPuntos = new ArrayList<>(); 
    private List<MapPoint> rutaCalculada = new ArrayList<>();
    private List<MapPoint[]> conexiones = new ArrayList<>(); // 🕸️ Guarda las aristas del grafo

    public MapPanl(String rutaImagen) {
        try {
            this.mapaImagen = ImageIO.read(new File(rutaImagen));
        } catch (IOException e) {
            System.err.println("Error crítico: No se pudo cargar la imagen del mapa en: " + rutaImagen);
        }
    }

    //  Actualiza la lista interna, ruta y conexiones Y LLAMA A REPAINT 
    public void actualizarDatosDibujo(List<MapPoint> nuevosPuntos, List<MapPoint> nuevaRuta, List<MapPoint[]> nuevasConexiones) {
        this.listaPuntos = (nuevosPuntos != null) ? new ArrayList<>(nuevosPuntos) : new ArrayList<>();
        this.rutaCalculada = (nuevaRuta != null) ? new ArrayList<>(nuevaRuta) : new ArrayList<>();
        this.conexiones = (nuevasConexiones != null) ? new ArrayList<>(nuevasConexiones) : new ArrayList<>();
        
        repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        Graphics2D g2 = (Graphics2D) g;

        // Anti-aliasing para que todo se dibuje suave 
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Dibujar Imagen de Fondo 🖼️
        if (mapaImagen != null) {
            g2.drawImage(mapaImagen, 0, 0, getWidth(), getHeight(), this);
        }

        //  Dibujar LÍNEAS DE CONEXIÓN ENTRE NODOS 
        if (conexiones != null && !conexiones.isEmpty()) {
            g2.setColor(new Color(120, 120, 120, 180));
            g2.setStroke(new BasicStroke(2));

            for (MapPoint[] par : conexiones) {
                MapPoint p1 = par[0];
                MapPoint p2 = par[1];
                if (p1 != null && p2 != null) {
                    g2.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                }
            }
        }

        // Dibujar la Ruta Calculada (Línea Azul Estilo Google Maps
        if (rutaCalculada != null && rutaCalculada.size() > 1) {
            g2.setColor(new Color(30, 144, 255, 220));
            g2.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            for (int i = 0; i < rutaCalculada.size() - 1; i++) {
                MapPoint p1 = rutaCalculada.get(i);
                MapPoint p2 = rutaCalculada.get(i + 1);
                g2.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            }
        }

        // Dibujar los Nodos (Círculos Rojos )
        if (listaPuntos != null) {
            int radio = 10; 

            for (MapPoint p : listaPuntos) {
                // Relleno Rojo Intenso
                g2.setColor(new Color(231, 76, 60));
                g2.fillOval(p.getX() - radio, p.getY() - radio, radio * 2, radio * 2);

                // Borde Blanco Estilizado
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(p.getX() - radio, p.getY() - radio, radio * 2, radio * 2);

                // Etiqueta del ID
                g2.setColor(Color.BLACK);
                g2.drawString(p.getId(), p.getX() + radio + 3, p.getY() + 5);
            }
        }
    }
}