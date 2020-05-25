package com.corrientazo.domicilios.service;

import com.corrientazo.domicilios.data.DeliveryDAO;
import com.corrientazo.domicilios.data.InformDAO;
import com.corrientazo.domicilios.model.*;
import com.corrientazo.domicilios.processor.DeliveryProcessor;
import com.corrientazo.domicilios.processor.DeliveryProcessorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DeliveryServiceImplTest {

    private DeliveryServiceImpl deliveryServiceImpl;
    private DeliveryProcessorFactory deliveryProcessorFactory;
    private DeliveryDAO deliveryDAO;
    private InformDAO informDAO;

    @BeforeEach
    void setUp() {
        deliveryProcessorFactory = Mockito.mock(DeliveryProcessorFactory.class);
        deliveryDAO = Mockito.mock(DeliveryDAO.class);
        informDAO = Mockito.mock(InformDAO.class);
    }

    @Test
    void processDeliveriesTest() {
        List<Drone> drones = Collections.singletonList(
                new Drone("01", Orientation.SOUTH, new Coordinates(2, 4))
        );

        List<Delivery> deliveries = Collections.singletonList(
                new Delivery(Arrays.asList(Movement.FORWARD, Movement.FORWARD))
        );

        DeliveryProcessor deliveryProcessor = Mockito.mock(DeliveryProcessor.class);
        when(deliveryProcessorFactory.create(any(), any())).thenReturn(deliveryProcessor);
        when(deliveryDAO.getDeliveriesByDroneId(any())).thenReturn(deliveries);

        deliveryServiceImpl = new DeliveryServiceImpl(deliveryProcessorFactory, deliveryDAO, informDAO, drones);
        deliveryServiceImpl.processDeliveries();

        verify(deliveryProcessorFactory).create(any(), any());
        verify(deliveryDAO).getDeliveriesByDroneId(any());

    }
}