package com.corrientazo.domicilios.data;

import com.corrientazo.domicilios.exception.ApplicationException;
import com.corrientazo.domicilios.model.Coordinates;
import com.corrientazo.domicilios.model.Drone;
import com.corrientazo.domicilios.model.Orientation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InformDAOImplTest {

    private InformDAOImpl informDAOimpl;
    private Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("domicilios_test");
        informDAOimpl = new InformDAOImpl(tempDir);
    }

    @Test
    public void testSaveDronePositionTest() throws IOException {
        Drone drone = new Drone("01", Orientation.NORTH, new Coordinates(2, 4));
        informDAOimpl.saveDronePosition(drone);

        List<String> lines = Files.readAllLines(tempDir.resolve("out01.txt"));

        assertNotNull(lines);
        assertEquals(1, lines.size());
        assertEquals("(2, 4) orientation NORTH", lines.get(0));
    }

    @Test
    public void testSaveDronePositionErrorSavingTest() {
        informDAOimpl = new InformDAOImpl(Paths.get("invalid_directory"));

        Drone drone = new Drone("01", Orientation.NORTH, new Coordinates(2, 4));
        assertThrows(ApplicationException.class, () -> {informDAOimpl.saveDronePosition(drone);});
    }

}