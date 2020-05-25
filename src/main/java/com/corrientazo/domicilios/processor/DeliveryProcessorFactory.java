package com.corrientazo.domicilios.processor;

import com.corrientazo.domicilios.model.Delivery;
import com.corrientazo.domicilios.model.Drone;

import java.util.List;

public interface DeliveryProcessorFactory {
    DeliveryProcessor create(Drone drone, List<Delivery> deliveries);
}
