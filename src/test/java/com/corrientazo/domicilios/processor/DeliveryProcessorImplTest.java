package com.corrientazo.domicilios.processor;

import com.corrientazo.domicilios.exception.ApplicationException;
import com.corrientazo.domicilios.model.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class DeliveryProcessorImplTest {

    private DeliveryProcessorImpl deliveryProcessorImpl;

    @Test
    public void testExecuteOnCompleted() {
        Drone drone = new Drone("01", Orientation.SOUTH, new Coordinates(4, 6));

        deliveryProcessorImpl = new DeliveryProcessorImpl(drone, getDeliveriesList());
        deliveryProcessorImpl.setDeliveryObserver(new DeliveryObserver() {
            @Override
            public void onDeliveryCompleted(Drone drone) {
                assertEquals("01", drone.getId());
                assertEquals(Orientation.SOUTH, drone.getOrientation());
                assertEquals(4, drone.getCoordinates().getX());
                assertEquals(6, drone.getCoordinates().getY());
            }

            @Override
            public void onDeliveryError(Drone drone, String errorMessage) {
            }
        });

        deliveryProcessorImpl.execute();
    }

    @Test
    public void testExecuteOnError() {
        Drone drone = new Drone("01", Orientation.EAST, new Coordinates(9, 9));
        Delivery delivery = new Delivery();
        delivery.setRoute(Arrays.asList(Movement.FORWARD, Movement.FORWARD));

        deliveryProcessorImpl = new DeliveryProcessorImpl(drone, Collections.singletonList(delivery));
        deliveryProcessorImpl.setDeliveryObserver(new DeliveryObserver() {
            @Override
            public void onDeliveryCompleted(Drone drone) {
            }

            @Override
            public void onDeliveryError(Drone drone, String errorMessage) {
                assertEquals("01", drone.getId());
                assertEquals(Orientation.EAST, drone.getOrientation());
                assertNotNull(errorMessage);
            }
        });

        deliveryProcessorImpl.execute();
    }

    @Test
    public void testCapacityExceeded() {
        Drone drone = new Drone("01", Orientation.EAST, new Coordinates(9, 9));
        List<Delivery> deliveries = Arrays.asList(new Delivery(), new Delivery(), new Delivery(), new Delivery());

        deliveryProcessorImpl = new DeliveryProcessorImpl(drone, deliveries);

        assertThrows(ApplicationException.class, () -> deliveryProcessorImpl.execute());
    }

    private List<Delivery> getDeliveriesList() {
        return Collections.singletonList(
                new Delivery(
                        Arrays.asList(
                                Movement.ROTATE_RIGHT,
                                Movement.FORWARD,
                                Movement.ROTATE_RIGHT,
                                Movement.FORWARD,
                                Movement.ROTATE_RIGHT,
                                Movement.FORWARD,
                                Movement.ROTATE_RIGHT,
                                Movement.FORWARD,
                                Movement.ROTATE_LEFT,
                                Movement.FORWARD,
                                Movement.ROTATE_LEFT,
                                Movement.FORWARD,
                                Movement.ROTATE_LEFT,
                                Movement.FORWARD,
                                Movement.ROTATE_LEFT,
                                Movement.FORWARD
                        )
                )
        );
    }
}