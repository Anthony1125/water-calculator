package org.budaiev;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Objects;

public class WaterCalculatorApp {

    static final int MAX_LANDSCAPE_SIZE = 32000;

    public static boolean isLandscapeValid(int[] landscape) {
        Objects.requireNonNull(landscape);

        if (landscape.length > MAX_LANDSCAPE_SIZE) {
            return false;
        }

        for (int positionHeight : landscape) {
            if (positionHeight < 0 || positionHeight >= MAX_LANDSCAPE_SIZE) {
                return false;
            }
        }
        return true;
    }

    public static long calculateWaterAmount(int[] landscape) {
        if (!isLandscapeValid(landscape)) {
            throw new IllegalArgumentException("Landscape is invalid");
        }

        int totalWaterAmount = 0;

        int cavityLeftColumn = getFirstNonZeroColumnIndex(landscape);
        if (cavityLeftColumn == -1) {
            return 0;
        }

        //from left to right
        int blocksInCavityCount = 0;
        for (int i = cavityLeftColumn + 1; i < landscape.length; i++) {
            if (landscape[i] > landscape[cavityLeftColumn]) {
                int distanceBetweenColumns = getDistanceBetweenColumns(cavityLeftColumn, i);
                totalWaterAmount += (long) landscape[cavityLeftColumn] * distanceBetweenColumns
                        - blocksInCavityCount;
                blocksInCavityCount = 0;
                cavityLeftColumn = i;
            } else {
                blocksInCavityCount += landscape[i];
            }
        }

        int cavityRightColumn = getLastNonZeroColumnIndex(landscape);
        if (cavityRightColumn == -1) {
            return 0;
        }

        // from right to left
        blocksInCavityCount = 0;
        for (int i = cavityRightColumn - 1; i >= 0; i--) {
            if (landscape[i] >= landscape[cavityRightColumn]) {
                int distanceBetweenColumns = getDistanceBetweenColumns(i, cavityRightColumn);
                totalWaterAmount += (long) landscape[cavityRightColumn] * distanceBetweenColumns
                        - blocksInCavityCount;
                blocksInCavityCount = 0;
                cavityRightColumn = i;
            } else {
                blocksInCavityCount += landscape[i];
            }
        }

        return totalWaterAmount;
    }

    private static int getFirstNonZeroColumnIndex(int[] landscape) {
        for (int i = 0; i < landscape.length; i++) {
            if (landscape[i] > 0) {
                return i;
            }
        }
        return -1;
    }

    private static int getLastNonZeroColumnIndex(int[] landscape) {
        for (int i = landscape.length - 1; i >= 0; i--) {
            if (landscape[i] > 0) {
                return i;
            }
        }
        return -1;
    }

    private static int getDistanceBetweenColumns(int column1, int column2) {
        return column2 - column1 - 1;
    }

    public static void main(String[] args) throws IOException {
        int[] landscape = readDataFromConsole();
        long l = calculateWaterAmount(landscape);
        printResultToConsole(l);
    }

    private static int[] readDataFromConsole() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            return Arrays.stream(reader.readLine().strip().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
    }

    private static void printResultToConsole(long l) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write("Amount of water: " + l);
        writer.newLine();
        writer.flush();
    }
}
