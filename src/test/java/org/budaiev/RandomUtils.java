package org.budaiev;

public final class RandomUtils {

    private RandomUtils() {
    }

    public static int getRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
