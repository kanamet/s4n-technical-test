package com.corrientazo.domicilios.processor;

import com.corrientazo.domicilios.exception.ApplicationException;
import com.corrientazo.domicilios.model.Delivery;
import com.corrientazo.domicilios.model.Drone;
import com.corrientazo.domicilios.model.Movement;
import com.corrientazo.domicilios.model.Orientation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DeliveryProcessorImpl extends DeliveryProcessor {

    private static final Logger logger = LogManager.getLogger(DeliveryProcessorImpl.class);

    private static final int MAX_COORDINATE_X = 10;
    private static final int MIN_COORDINATE_X = -10;
    private static final int MAX_COORDINATE_Y = 10;
    private static final int MIN_COORDINATE_Y = -10;

    private static final int MAX_DRONE_CAPACITY = 3;

    private final Drone drone;
    private final List<Delivery> deliveries;

    public DeliveryProcessorImpl(Drone drone, List<Delivery> deliveries) {
        this.drone = drone;
        this.deliveries = deliveries;
    }

    @Override
    public void execute() {
        if (deliveries.size() > MAX_DRONE_CAPACITY) {
            throw new ApplicationException("Delivery capacity exceeded. Max Capacity = "
                    + MAX_DRONE_CAPACITY + ", but actual is " + deliveries.size()
            );
        }

        try {
            for (Delivery delivery : deliveries) {
                for (Movement movement : delivery.getRoute()) {
                    switch (movement) {
                        case FORWARD:
                            moveForwardFrom();
                            break;

                        case ROTATE_RIGHT:
                            rotateRightFrom();
                            break;

                        case ROTATE_LEFT:
                            rotateLeftFrom();
                            break;
                    }
                }

                notifyDeliveryCompleted(drone);
            }

        } catch (ApplicationException e) {
            String message = "There was an error processing deliveries: Drone ID = " + drone.getId();
            logger.error(message, e);

            notifyDeliveryError(drone, e.getMessage());
        }
    }

    private void moveForwardFrom() {
        int x = drone.getCoordinates().getX();
        int y = drone.getCoordinates().getY();

        switch (drone.getOrientation()) {
            case NORTH:
                y = y + 1;
                break;

            case EAST:
                x = x + 1;
                break;

            case SOUTH:
                y = y - 1;
                break;

            case WEST:
                x = x - 1;
                break;
        }

        if (isValidCoordinates(x, y)) {
            drone.getCoordinates().setX(x);
            drone.getCoordinates().setY(y);
        } else {
            throw new ApplicationException(String.format("Coordinates are out of limits: (%d,%d)", x, y));
        }
    }

    private boolean isValidCoordinates(int x, int y) {
        boolean validCoordinateX = (x >= MIN_COORDINATE_X && x <= MAX_COORDINATE_X);
        boolean validCoordinateY = (y >= MIN_COORDINATE_Y && y <= MAX_COORDINATE_Y);

        return validCoordinateX && validCoordinateY;
    }


    private void rotateRightFrom() {
        switch (drone.getOrientation()) {
            case NORTH:
                drone.setOrientation(Orientation.EAST);
                break;

            case EAST:
                drone.setOrientation(Orientation.SOUTH);
                break;

            case SOUTH:
                drone.setOrientation(Orientation.WEST);
                break;

            case WEST:
                drone.setOrientation(Orientation.NORTH);
                break;
        }
    }

    private void rotateLeftFrom() {
        switch (drone.getOrientation()) {
            case NORTH:
                drone.setOrientation(Orientation.WEST);
                break;

            case EAST:
                drone.setOrientation(Orientation.NORTH);
                break;

            case SOUTH:
                drone.setOrientation(Orientation.EAST);
                break;

            case WEST:
                drone.setOrientation(Orientation.SOUTH);
                break;
        }
    }

}
