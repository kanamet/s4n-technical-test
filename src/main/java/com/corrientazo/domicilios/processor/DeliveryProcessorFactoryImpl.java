package com.corrientazo.domicilios.processor;

import com.corrientazo.domicilios.model.Delivery;
import com.corrientazo.domicilios.model.Drone;

import java.util.List;

public class DeliveryProcessorFactoryImpl implements DeliveryProcessorFactory {
    @Override
    public DeliveryProcessor create(Drone drone, List<Delivery> deliveries) {
        return new DeliveryProcessorImpl(drone, deliveries);
    }
}
