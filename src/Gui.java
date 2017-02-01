package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;


public class Gui implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private JLabel label1, label2, label3, label4;
    private JTextField filesList, newFormat, width, height;
    private JButton bAdd, bClear, bObtain;
    private ArrayList<File> list;

    public Gui() {
        buildGUI();
        list = new ArrayList<File>();
    }

    public void buildGUI() {
        frame = new JFrame("Thumbnails converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.setLayout(null);

        label1 = new JLabel("Input files for converting: ");
        label1.setBounds(5, 2, 145, 27);
        label1.setForeground(Color.lightGray);
        panel.add(label1);

        filesList = new JTextField();
        filesList.setBounds(5, 30, 520, 27);
        panel.add(filesList);

        bAdd = new JButton("Add file(s) for operation");
        bAdd.setBounds(180, 65, 170, 27);
        bAdd.addActionListener(this);
        panel.add(bAdd);

        label2 = new JLabel("Enter the new format: ");
        label2.setBounds(5, 100, 130, 27);
        label2.setForeground(Color.lightGray);
        panel.add(label2);

        newFormat = new JTextField();
        newFormat.setBounds(130, 100, 70, 27);
        panel.add(newFormat);

        label3 = new JLabel("Width: ");
        label3.setBounds(230, 100, 70, 27);
        label3.setForeground(Color.lightGray);
        panel.add(label3);

        width = new JTextField();
        width.setBounds(270, 100, 70, 27);
        panel.add(width);

        label4 = new JLabel("Height: ");
        label4.setBounds(370, 100, 130, 27);
        label4.setForeground(Color.lightGray);
        panel.add(label4);

        height = new JTextField();
        height.setBounds(413, 100, 70, 27);
        panel.add(height);

        bClear = new JButton("Clear the file list");
        bClear.setBounds(70, 140, 170, 35);
        bClear.addActionListener(this);
        panel.add(bClear);

        bObtain = new JButton("Obtain thumbnails");
        bObtain.setBounds(290, 140, 170, 35);
        bObtain.addActionListener(this);
        panel.add(bObtain);

        frame.setResizable(false);
        frame.getContentPane().add(panel);
        frame.setSize(550, 225);
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        // Adds files to the list and updates the text field.
        if (e.getSource() == bAdd) {
            JFileChooser fileOpen = new JFileChooser();
            fileOpen.setMultiSelectionEnabled(true);
            int ret = fileOpen.showDialog(null, "Add image(s)");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File[] files = fileOpen.getSelectedFiles();
                for (int i = 0; i < files.length; i++){
                    list.add(files[i]);
                    filesList.setText(filesList.getText().concat(files[i].getName() + ", "));
                }
            }
        }

        // Clear the text field of any listed files.
        if (e.getSource() == bClear) {
            list.clear();
            filesList.setText("");
        }

        // Creates thew ne thumbnails to the folder which contains original image.
        if (e.getSource() == bObtain) {
            int w = Integer.parseInt(width.getText());
            int h = Integer.parseInt(height.getText());
            String fileType = newFormat.getText();
            File[] listOfFiles = new File[list.size()];
            listOfFiles = list.toArray(listOfFiles);
            Converter converter = new Converter(listOfFiles, fileType, w, h);
            TransformationThread thread = new TransformationThread(converter);
            thread.start();
        }
        }

    // The conversion thread which handles converting images.
    public class TransformationThread extends Thread{
        Converter converter;

        public TransformationThread(Converter converter){
            this.converter = converter;
        }

        public void run(){
            String message = converter.convertTo();
            JOptionPane.showMessageDialog(null, message);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
    }
