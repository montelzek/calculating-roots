package edu.lab;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CSquareRoot extends CRoot {


    public CSquareRoot(double number, double precision, JTextArea textArea) {
        super(number, precision, textArea);
    }

    @Override
    public double calculate() {

        double x = number / 2;
        double y = 1.0;
        int step = 0;

        do {
            x = (x + y) / 2;
            y = number / x;
            step++;
            int precision = 15;
            if (log) {
                Double truncatedX = BigDecimal.valueOf(x)
                        .setScale(15, RoundingMode.HALF_UP)
                        .doubleValue();
                String formattedX = String.format("%." + precision + "f", truncatedX);
                textArea.append("\n- step: " + step + ", value: " + formattedX);
            }
        } while (Math.abs(x - y) > precision);

        return x;
    }
}
