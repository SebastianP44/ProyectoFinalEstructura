package models;

import java.util.Objects;

public class MapPoint {
    private String id;
    private int x;
    private int y;

    public MapPoint(String id, int x, int y) { // Constructor 
        this.id = id;
        this.x = x;
        this.y = y;

    }
     public String getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

     @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MapPoint)) return false;
        MapPoint mapPoint = (MapPoint) o;
        return Objects.equals(id, mapPoint.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return id;
        
    }

    

}
