package org.budaiev;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.budaiev.RandomUtils.getRandomInt;
import static org.budaiev.WaterCalculatorApp.MAX_LANDSCAPE_SIZE;
import static org.budaiev.WaterCalculatorApp.calculateWaterAmount;
import static org.budaiev.WaterCalculatorApp.isLandscapeValid;

class WaterCalculatorAppTest {

    private int getRandomLandScapeSize() {
        return getRandomInt(0, MAX_LANDSCAPE_SIZE);
    }

    private int[] getRandomLandScape(int landScapeWidth) {
        int[] landscape = new int[landScapeWidth];
        for (int i = 0; i < landscape.length; i++) {
            landscape[i] = getRandomInt(0, MAX_LANDSCAPE_SIZE);
        }
        return landscape;
    }

    @Test
    void isLandscapeValid_ValidLandscape_True() {
        int[] landscape = getRandomLandScape(getRandomLandScapeSize());
        Assertions.assertTrue(isLandscapeValid(landscape));
    }

    @Test
    void isLandscapeValid_ColumnsCountMoreThanMaxSize_False() {
        int[] landscape = getRandomLandScape(MAX_LANDSCAPE_SIZE + 1);
        Assertions.assertFalse(isLandscapeValid(landscape));
    }

    @Test
    void isLandscapeValid_ColumnHeightGreaterThanMax_False() {
        int[] landscape = getRandomLandScape(getRandomLandScapeSize());
        landscape[getRandomInt(0, landscape.length)] = MAX_LANDSCAPE_SIZE + 1;
        Assertions.assertFalse(isLandscapeValid(landscape));
    }

    @Test
    void isLandscapeValid_NegativeColumnHeight_False() {
        int[] landscape = getRandomLandScape(getRandomLandScapeSize());
        landscape[getRandomInt(0, landscape.length)] = -1;
        Assertions.assertFalse(isLandscapeValid(landscape));
    }

    @Test
    void calculateWaterAmount_9() {
        int[] landscape = {5, 2, 3, 4, 5, 4, 0, 3, 1};
        Assertions.assertEquals(9, calculateWaterAmount(landscape));
    }

    @Test
    void calculateWaterAmount_7() {
        int[] landscape = {5, 2, 12, 4, 5, 4, 0, 3, 1};
        Assertions.assertEquals(7, calculateWaterAmount(landscape));
    }

    @Test
    void calculateWaterAmount_79() {
        int[] landscape = {5, 2, 12, 4, 5, 4, 0, 3, 1, 5, 2, 12, 4, 5, 4, 0, 3, 1};
        Assertions.assertEquals(79, calculateWaterAmount(landscape));
    }

    @Test
    void calculateWaterAmount_78() {
        int[] landscape = {5, 2, 12, 4, 5, 4, 0, 3, 1, 5, 2, 12, 111, 5, 4, 0, 3, 1};
        Assertions.assertEquals(78, calculateWaterAmount(landscape));
    }

    @Test
    void calculateWaterAmount_EmptyLandscape_0() {
        int[] landscape = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        Assertions.assertEquals(0, calculateWaterAmount(landscape));
    }

    @Test
    void calculateWaterAmount_EmptyLandscapeWidth1_0() {
        int[] landscape = {0};
        Assertions.assertEquals(0, calculateWaterAmount(landscape));
    }

    @Test
    void calculateWaterAmount_Width1_0() {
        int randomInt = getRandomInt(0, MAX_LANDSCAPE_SIZE);
        int[] landscape = {randomInt};
        Assertions.assertEquals(0, calculateWaterAmount(landscape));
    }

    @Test
    void calculateWaterAmount_TheSameHeightLevel_0() {
        int randomInt = getRandomInt(0, MAX_LANDSCAPE_SIZE);
        int[] landscape = {randomInt, randomInt, randomInt, randomInt};
        Assertions.assertEquals(0, calculateWaterAmount(landscape));
    }

    @Test
    void calculateWaterAmount_Pyramid_0() {
        int[] landscape = {0, 1, 2, 1, 0};
        Assertions.assertEquals(0, calculateWaterAmount(landscape));
    }

    @Test
    void calculateWaterAmount_Cavity_0() {
        int[] landscape = {3, 2, 1, 0, 1, 2, 3};
        Assertions.assertEquals(9, calculateWaterAmount(landscape));
    }
}