package com.corrientazo.domicilios.model;

import java.util.List;

public class Delivery {
    private List<Movement> route;

    public Delivery() {
    }

    public Delivery(List<Movement> route) {
        this.route = route;
    }

    public List<Movement> getRoute() {
        return route;
    }

    public void setRoute(List<Movement> route) {
        this.route = route;
    }
}
