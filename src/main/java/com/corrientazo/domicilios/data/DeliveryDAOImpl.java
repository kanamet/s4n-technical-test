package com.corrientazo.domicilios.data;

import com.corrientazo.domicilios.exception.ApplicationException;
import com.corrientazo.domicilios.model.Delivery;
import com.corrientazo.domicilios.model.Movement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeliveryDAOImpl implements DeliveryDAO {

    private static final String FILENAME_FORMAT = "in%s.txt";

    private final Path inputPath;

    public DeliveryDAOImpl(Path inputPath) {
        this.inputPath = inputPath;
    }

    @Override
    public List<Delivery> getDeliveriesByDroneId(String id) {
        String filename = String.format(FILENAME_FORMAT, id);
        Path filePath = inputPath.resolve(filename);

        try {
            return Files.readAllLines(filePath)
                    .stream()
                    .map(this::mapToDelivery)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new ApplicationException("There was an error reading file: " + filePath.toAbsolutePath(), e);
        }
    }

    private Delivery mapToDelivery(String input) {
        if (input == null) {
            return null;
        }

        List<Movement> movements = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            switch (input.charAt(i)) {
                case 'A':
                    movements.add(Movement.FORWARD);
                    break;

                case 'D':
                    movements.add(Movement.ROTATE_RIGHT);
                    break;

                case 'I':
                    movements.add(Movement.ROTATE_LEFT);
                    break;
            }
        }

        Delivery delivery = new Delivery();
        delivery.setRoute(movements);

        return delivery;
    }

}
