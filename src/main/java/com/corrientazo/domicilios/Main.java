package com.corrientazo.domicilios;

import com.corrientazo.domicilios.data.DeliveryDAO;
import com.corrientazo.domicilios.data.DeliveryDAOImpl;
import com.corrientazo.domicilios.data.InformDAO;
import com.corrientazo.domicilios.data.InformDAOImpl;
import com.corrientazo.domicilios.model.Coordinates;
import com.corrientazo.domicilios.model.Drone;
import com.corrientazo.domicilios.model.Orientation;
import com.corrientazo.domicilios.processor.DeliveryProcessorFactory;
import com.corrientazo.domicilios.processor.DeliveryProcessorFactoryImpl;
import com.corrientazo.domicilios.service.DeliveryService;
import com.corrientazo.domicilios.service.DeliveryServiceImpl;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final int NUMBER_OF_DRONES = 20;
    private static final String INPUT_DIRECTORY = "inputs";
    private static final String OUTPUT_DIRECTORY = "outputs";

    private void executeApplication() {
        MockUtils.cleanOutputFolder();
/*
        MockUtils.populateFiles(NUMBER_OF_DRONES);
*/

        DeliveryDAO deliveryDAO = new DeliveryDAOImpl(Paths.get(INPUT_DIRECTORY));
        InformDAO informDAO = new InformDAOImpl(Paths.get(OUTPUT_DIRECTORY));

        List<Drone> drones = initializeDrones(NUMBER_OF_DRONES);

        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(processors);

        DeliveryProcessorFactory deliveryProcessorFactory = new DeliveryProcessorFactoryImpl();
        DeliveryService deliveryService = new DeliveryServiceImpl(deliveryProcessorFactory, deliveryDAO, informDAO, drones, executor);
        deliveryService.processDeliveries();
    }

    private List<Drone> initializeDrones(int numberOfDrones) {
        List<Drone> drones = new ArrayList<>();
        for (int i = 1; i <= numberOfDrones; i++) {
            String id = String.format("%02d", i);
            drones.add(new Drone(id, Orientation.NORTH, new Coordinates(0, 0)));
        }

        return drones;
    }

    public static void main(String[] args) {
        new Main().executeApplication();
    }

}
