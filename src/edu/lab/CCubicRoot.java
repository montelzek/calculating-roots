package edu.lab;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CCubicRoot extends CRoot {
    public CCubicRoot(double number, double precision, JTextArea textArea) {
        super(number, precision, textArea);
    }

    @Override
    public double calculate() {
        double start = 0.0;
        double end = number;
        double mid = 0.5 * number;
        int step = 0;

        while (Math.abs(number - mid * mid * mid) > precision) {

            if (number < mid * mid * mid) {
                end = mid;
            } else
                start = mid;

            mid = (start + end) / 2;
            step++;

            int precision = 15;

            if (log) {
                Double truncatedMid = BigDecimal.valueOf(mid)
                        .setScale(15, RoundingMode.HALF_UP)
                        .doubleValue();
                String formattedMid = String.format("%." + precision + "f", truncatedMid);
                textArea.append("\n- step: " + step + ", value: " + formattedMid);
            }

        }

        return mid;
    }
}
