package com.corrientazo.domicilios.model;

public enum Movement {
    FORWARD("Move Forward"),
    ROTATE_LEFT("Rotate 90 degrees to Left"),
    ROTATE_RIGHT("Rotate 90 degrees to Right");

    String description;

    Movement(String description) {
        this.description = description;
    }
}
