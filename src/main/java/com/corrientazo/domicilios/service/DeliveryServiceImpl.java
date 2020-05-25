package com.corrientazo.domicilios.service;

import com.corrientazo.domicilios.data.DeliveryDAO;
import com.corrientazo.domicilios.data.InformDAO;
import com.corrientazo.domicilios.exception.ApplicationException;
import com.corrientazo.domicilios.model.Delivery;
import com.corrientazo.domicilios.model.Drone;
import com.corrientazo.domicilios.processor.DeliveryObserver;
import com.corrientazo.domicilios.processor.DeliveryProcessor;
import com.corrientazo.domicilios.processor.DeliveryProcessorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DeliveryServiceImpl implements DeliveryService {

    private static final Logger logger = LogManager.getLogger(DeliveryServiceImpl.class);

    private final DeliveryProcessorFactory deliveryProcessorFactory;
    private final DeliveryDAO deliveryDAO;
    private final InformDAO informDAO;
    private final List<Drone> drones;

    public DeliveryServiceImpl(DeliveryProcessorFactory deliveryProcessorFactory, DeliveryDAO deliveryDAO, InformDAO informDAO, List<Drone> drones) {
        this.deliveryProcessorFactory = deliveryProcessorFactory;
        this.deliveryDAO = deliveryDAO;
        this.informDAO = informDAO;
        this.drones = drones;
    }

    @Override
    public void processDeliveries() {
        for (Drone drone : drones) {
            try {
                List<Delivery> deliveries = deliveryDAO.getDeliveriesByDroneId(drone.getId());
                DeliveryProcessor deliveryProcessor = deliveryProcessorFactory.create(drone, deliveries);
                deliveryProcessor.setDeliveryObserver(deliveryObserver);
                deliveryProcessor.execute();
            } catch (ApplicationException e) {
                logger.error("Error processing deliveries for Drone: " + drone.toString(), e);
            }
        }
    }

    private final DeliveryObserver deliveryObserver = new DeliveryObserver() {
        @Override
        public void onDeliveryCompleted(Drone drone) {
            try {
                informDAO.saveDronePosition(drone);
                logger.info("Delivery completed: {}", drone);
            } catch (ApplicationException e) {
                logger.error(e);
            }
        }

        @Override
        public void onDeliveryError(Drone drone, String errorMessage) {
            logger.error("There was an error Delivering: {} - {}", drone, errorMessage);
        }
    };
}
