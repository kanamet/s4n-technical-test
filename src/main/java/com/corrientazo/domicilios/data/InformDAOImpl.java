package com.corrientazo.domicilios.data;

import com.corrientazo.domicilios.exception.ApplicationException;
import com.corrientazo.domicilios.model.Drone;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class InformDAOImpl implements InformDAO {

    private final String DRONE_POSITION_FORMAT = "(%d, %d) orientation %s\n";
    private final String FILENAME_FORMAT = "out%s.txt";

    private final Path outputPath;

    public InformDAOImpl(Path outputPath) {
        this.outputPath = outputPath;
    }

    @Override
    public void saveDronePosition(Drone drone) {
        String line = String.format(DRONE_POSITION_FORMAT,
                drone.getCoordinates().getX(),
                drone.getCoordinates().getY(),
                drone.getOrientation()
        );

        String filename = String.format(FILENAME_FORMAT, drone.getId());
        Path filePath = outputPath.resolve(filename);

        try {
            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
            }

            Files.write(filePath, line.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new ApplicationException("There was an error writing file: " + filePath.toAbsolutePath(), e);
        }

    }
}
