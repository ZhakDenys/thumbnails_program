package com.company;


import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Gui gui = new Gui();
            }
        });
    }
}
