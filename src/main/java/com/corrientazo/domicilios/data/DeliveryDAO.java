package com.corrientazo.domicilios.data;

import com.corrientazo.domicilios.model.Delivery;

import java.util.List;

public interface DeliveryDAO {
    List<Delivery> getDeliveriesByDroneId(String id);
}
