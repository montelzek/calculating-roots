package edu.lab;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Locale;
import java.util.Scanner;

public class CMainForm extends JFrame {
    private JTextField numberTextField;
    private JTextField precisionTextField;
    private JRadioButton pierwiastek2GoStopniaRadioButton;
    private JRadioButton pierwiastek3GoStopniaRadioButton;
    private JCheckBox logStepsCheckBox;
    private JButton OBLICZButton;
    private JButton saveParamsButton;
    private JButton readParamsButton;
    private JTextArea textArea1;
    private JButton zapiszRaportZObliczeńButton;
    private JPanel MainPanel;

    public CMainForm(String title) throws HeadlessException {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MainPanel);
        this.pack();
        this.setLocationRelativeTo(null);

        OBLICZButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                button1Click();

            }
        });
        saveParamsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button3Click();
            }
        });
        readParamsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button2Click();
            }
        });
        zapiszRaportZObliczeńButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button4Click();
            }
        });
    }

    private void button1Click() {

        double N, E;

        try {
            N = Double.parseDouble(numberTextField.getText().replaceAll(",", "."));
            E = Double.parseDouble(precisionTextField.getText().replaceAll(",", "."));
        } catch (NumberFormatException ee) {
            JOptionPane.showMessageDialog(this, "\nBłędna wartość parametru! \nKomunikat: " + ee.getMessage());
            return;
        }

        CRoot root = null;
        double libRes = 0.0;
        if (pierwiastek2GoStopniaRadioButton.isSelected()) {
            if (logStepsCheckBox.isSelected())
                root = new CSquareRoot(N, E, textArea1);
            else root = new CSquareRoot(N, E, null);
            libRes = Math.sqrt(N);
            textArea1.append(String.format(Locale.US, "\nPierwiastek kwadratowy z %f", N));
        } else {

            if (logStepsCheckBox.isSelected())
                root = new CCubicRoot(N, E, textArea1);
            else root = new CCubicRoot(N, E, null);
            libRes = Math.cbrt(N);
            textArea1.append(String.format(Locale.US, "\nPierwiastek sześcienny z %f", N));

        }

        if (root != null) {
            textArea1.append(String.format(Locale.US, "\nWymagana dokładność: %.15f\n", E));
            double result = root.calculate();

            textArea1.append(String.format(Locale.US, "\n\nWYNIK OBLICZONY:   %.15f", result));
            textArea1.append(String.format(Locale.US, "\nWYNIK (klasa MATH):   %.15f\n", libRes));
        }

    }

    private void button2Click() {
        String fName = new File(".").getAbsolutePath();
        fName = fName.substring(0, fName.length() - 1) + "params.data";
        try (Scanner fileScanner = new Scanner(new File(fName))) {
            String L;
            numberTextField.setText(fileScanner.nextLine());
            precisionTextField.setText(fileScanner.nextLine());
            L = fileScanner.nextLine();
            pierwiastek2GoStopniaRadioButton.setSelected(true);
            if (L.compareTo("3") == 0) pierwiastek3GoStopniaRadioButton.setSelected(true);
            L = fileScanner.nextLine();
            logStepsCheckBox.setSelected(L.compareTo("1") == 0);
            JOptionPane.showMessageDialog(this, "\nParametry wczytano z pliku\n" + fName);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "\nNieudany oczyt parametrów z pliku\n" + fName);
            e.printStackTrace();
        }


    }

    private void button3Click() {
        String fName = new File(".").getAbsolutePath();
        fName = fName.substring(0, fName.length() - 1) + "params.data";
        File aFile = new File(fName);

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(aFile))){
            bw.write(numberTextField.getText()+'\n');
            bw.write(precisionTextField.getText()+'\n');
            if (pierwiastek2GoStopniaRadioButton.isSelected()) bw.write("2\n");
            else bw.write("3\n");
            if (logStepsCheckBox.isSelected()) bw.write("1\n");
            else bw.write("0\n");
            JOptionPane.showMessageDialog(this, "Parametry zapisano do pliku\n"+fName);
        } catch (IOException e){
            JOptionPane.showMessageDialog(this, "Nieudany zapis parametrów do pliku\n"+fName);
            e.printStackTrace();
        }

    }

    private void button4Click() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooser.setDialogTitle("Nazwa pliku raportu");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Plik tesktowy", "txt"));
        int answer = fileChooser.showSaveDialog(null);
        if(answer == JFileChooser.APPROVE_OPTION){
            String fname = fileChooser.getSelectedFile().toString();
            if (!fname.endsWith(".txt")) fname += ".txt";
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fname)))){
                textArea1.write(bw);
                JOptionPane.showMessageDialog(this, "Zapisano plik raportu:\n + fname");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Nieudany zapis pliku raportu:\n" +fname);
            }
        }
    }


}
