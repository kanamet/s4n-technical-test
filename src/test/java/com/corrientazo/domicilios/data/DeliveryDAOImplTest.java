package com.corrientazo.domicilios.data;

import com.corrientazo.domicilios.exception.ApplicationException;
import com.corrientazo.domicilios.model.Delivery;
import com.corrientazo.domicilios.model.Movement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeliveryDAOImplTest {

    private DeliveryDAOImpl deliveryDAO;
    private Path tempDir;


    @BeforeEach
    public void setUp() throws Exception {
        tempDir = Files.createTempDirectory("domicilios_test");
        deliveryDAO = new DeliveryDAOImpl(tempDir);
    }

    @Test
    public void testGetDeliveriesByDroneIdTest() {
        createDeliveryFile(tempDir.resolve("in01.txt"), Arrays.asList("AAAAIAA", "DDDAIAD", "AAIADAD"));
        List<Movement> firstDeliveryRoute = Arrays.asList(
                Movement.ROTATE_RIGHT,
                Movement.ROTATE_RIGHT,
                Movement.ROTATE_RIGHT,
                Movement.FORWARD,
                Movement.ROTATE_LEFT,
                Movement.FORWARD,
                Movement.ROTATE_RIGHT
        );

        List<Delivery> deliveries = deliveryDAO.getDeliveriesByDroneId("01");

        assertEquals(3, deliveries.size());
        Delivery firstDelivery = deliveries.get(1);
        assertEquals( 7, firstDelivery.getRoute().size());
        assertEquals(firstDeliveryRoute, firstDelivery.getRoute());
    }

    @Test
    public void testGetDeliveriesByDroneIdEmptyFileTest() {
        createDeliveryFile(tempDir.resolve("in02.txt"), Collections.emptyList());
        List<Delivery> deliveries = deliveryDAO.getDeliveriesByDroneId("02");
        assertEquals(0, deliveries.size());
    }

    @Test
    public void testGetDeliveriesByDroneIdNotFoundFileTest() {
        assertThrows(ApplicationException.class, ()  -> {deliveryDAO.getDeliveriesByDroneId("03");});
    }

    private void createDeliveryFile(Path filePath, List<String> lines) {
        try {
            Files.write(filePath, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}