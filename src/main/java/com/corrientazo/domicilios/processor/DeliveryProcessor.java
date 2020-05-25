package com.corrientazo.domicilios.processor;

import com.corrientazo.domicilios.model.Coordinates;
import com.corrientazo.domicilios.model.Drone;

public abstract class DeliveryProcessor {
    private DeliveryObserver deliveryObserver = null;

    public abstract void execute();

    public void setDeliveryObserver(DeliveryObserver deliveryObserver) {
        this.deliveryObserver = deliveryObserver;
    }

    protected void notifyDeliveryCompleted(Drone drone) {
        if (deliveryObserver != null) {
            Coordinates coordinates = new Coordinates(drone.getCoordinates().getX(), drone.getCoordinates().getY());
            Drone notifiedDrone = new Drone(drone.getId(), drone.getOrientation(), coordinates);
            deliveryObserver.onDeliveryCompleted(notifiedDrone);
        }
    }

    protected void notifyDeliveryError(Drone drone, String errorMessage) {
        if (deliveryObserver != null) {
            Coordinates coordinates = new Coordinates(drone.getCoordinates().getX(), drone.getCoordinates().getY());
            Drone notifiedDrone = new Drone(drone.getId(), drone.getOrientation(), coordinates);
            deliveryObserver.onDeliveryError(notifiedDrone, errorMessage);
        }
    }

}
