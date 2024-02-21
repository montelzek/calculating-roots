package edu.lab;

import javax.swing.*;

public abstract class CRoot {

    protected double number;
    protected double precision;
    protected boolean log;
    protected JTextArea textArea;

    public CRoot(double number, double precision, JTextArea textArea) {
        this.number = number;
        this.precision = precision;
        this.textArea = textArea;
        this.log = this.textArea != null;
    }

    abstract public double calculate();
}
