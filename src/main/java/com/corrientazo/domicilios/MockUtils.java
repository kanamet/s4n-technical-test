package com.corrientazo.domicilios;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MockUtils {
    private static Random random = new Random();

    public static void cleanOutputFolder() {
        Path path = Paths.get("outputs");
        for (File file : path.toFile().listFiles()) {
            if (file != null && !file.isDirectory()) {
                file.delete();
            }
        }
    }

    public static void populateFiles(int numberOfFiles) {
        for (int i = 2; i <= numberOfFiles; i++) {

            try {
                Path path = Paths.get("inputs", String.format("in%02d.txt", i));
                if (Files.exists(path)) {
                    Files.delete(path);
                }

                List<String> deliveries = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    deliveries.add(createDelivery());
                }

                Files.createFile(path);
                Files.write(path, deliveries);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String createDelivery() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            builder.append(getRandomMovement());
        }

        return builder.toString();
    }

    private static String getRandomMovement() {
        switch (random.nextInt(3)) {
            case 0:
                return "A";
            case 1:
                return "D";
            default:
                return "I";
        }
    }
}
