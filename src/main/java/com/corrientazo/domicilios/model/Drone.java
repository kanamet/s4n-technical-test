package com.corrientazo.domicilios.model;

public class Drone {
    private String id;
    private Orientation orientation;
    private Coordinates coordinates;

    public Drone(String id, Orientation orientation, Coordinates coordinates) {
        this.id = id;
        this.orientation = orientation;
        this.coordinates = coordinates;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Drone{" +
                "id='" + id + '\'' +
                ", orientation=" + orientation +
                ", coordinates=" + coordinates +
                '}';
    }
}
