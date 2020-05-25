package com.corrientazo.domicilios.processor;

import com.corrientazo.domicilios.model.Drone;

public interface DeliveryObserver {
    void onDeliveryCompleted(Drone drone);

    void onDeliveryError(Drone drone, String errorMessage);
}
