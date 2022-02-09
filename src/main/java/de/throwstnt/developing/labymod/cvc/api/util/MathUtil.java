package de.throwstnt.developing.labymod.cvc.api.util;

public class MathUtil {

    /**
     * Clamp the given integer between min and max
     * 
     * @param min the minium value
     * @param max the maximum value
     * @param value the value to be clamped
     * @return the clamped value
     */
    public static int clampInt(int min, int max, int value) {
        return value > max ? max : value < min ? min : value;

    }

    /**
     * Clamp the given double between min and max
     * 
     * @param min the minium value
     * @param max the maximum value
     * @param value the value to be clamped
     * @return the clamped value
     */
    public static double clampDouble(double min, double max, double value) {
        return value > max ? max : value < min ? min : value;
    }
}
