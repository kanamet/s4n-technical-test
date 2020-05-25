package com.corrientazo.domicilios.processor;

import com.corrientazo.domicilios.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeliveryProcessorFactoryImplTest {

    private DeliveryProcessorFactoryImpl deliveryProcessorFactory;

    @BeforeEach
    public void setUp() {
        deliveryProcessorFactory = new DeliveryProcessorFactoryImpl();
    }

    @Test
    public void testCreate() {
        Drone drone = new Drone("01", Orientation.SOUTH, new Coordinates(4, 6));
        Delivery delivery = new Delivery();
        delivery.setRoute(Arrays.asList(
                Movement.ROTATE_RIGHT,
                Movement.ROTATE_RIGHT
        ));

        DeliveryProcessor deliveryProcessor = deliveryProcessorFactory.create(drone, Collections.singletonList(delivery));
        assertNotNull(deliveryProcessor);
    }
}