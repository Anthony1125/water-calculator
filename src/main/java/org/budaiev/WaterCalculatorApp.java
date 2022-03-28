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

        while (cavityLeftColumn < landscape.length - 2) {
            int cavityRightColumn = getFirstGreaterOrMaxColumn(landscape, cavityLeftColumn);
            if (cavityRightColumn == -1) {
                break;
            }

            totalWaterAmount += calculateWaterBetweenColumns(landscape, cavityLeftColumn,
                    cavityRightColumn);
            cavityLeftColumn = cavityRightColumn;
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

    private static int getFirstGreaterOrMaxColumn(int[] landscape, int startIndex) {
        int maxColumnIndex = -1;
        for (int i = startIndex + 1; i < landscape.length; i++) {
            if (landscape[i] > landscape[startIndex]) {
                return i;
            }
            if (maxColumnIndex == -1) {
                maxColumnIndex = i;
            } else if (landscape[maxColumnIndex] <= landscape[i]) {
                maxColumnIndex = i;
            }
        }
        return maxColumnIndex;
    }

    private static long calculateWaterBetweenColumns(int[] landscape, int left, int right) {
        int minBorderColumnHeight = Math.min(landscape[left], landscape[right]);

        int blocksInCavityCount = 0;
        for (int i = left + 1; i < right; i++) {
            blocksInCavityCount += Math.min(minBorderColumnHeight, landscape[i]);
        }

        return (long) minBorderColumnHeight * getDistanceBetweenColumns(left, right)
                - blocksInCavityCount;
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
