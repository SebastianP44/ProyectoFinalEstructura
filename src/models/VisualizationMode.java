package models;

public enum VisualizationMode {
    EXPLORATION,
    FINAL_PATH;

    @Override
    public String toString() {
        return this == EXPLORATION ? "Exploración completa" : "Ruta final";
    }

}
