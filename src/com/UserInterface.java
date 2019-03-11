package com;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Date;

public class UserInterface {

    private static Admin admin;
    private JPanel mainPanel;

    public static void main(String args[]) {
        UserInterface UI = new UserInterface();
        JFrame frame = new JFrame("Koala Quiz");
        LogInSignUp.main(admin,frame);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(650,500);
        frame.setVisible(true);
    }

//        public static void main(String args[]) {
//        JFrame frame = new JFrame("Quiz Layout");
//        UserInterface userInterface = new UserInterface();
//        QuizLayout.main(admin,frame);
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
//    }

    public UserInterface(){
        // TODO: this should be included in the constructor of the UserInterface.
        admin = loadAdmin();
        if (admin == null) {
            admin = new Admin();
        }

        // TODO: for testing only
//        Date date = new Date();
//        admin.logIn("nguyen.nguyen@my.columbiasc.edu", "123");
//        admin.setCurrentQuiz(new Quiz("How to sum to number", date, "Do not use calculator", 1000));
    }

    public Admin getAdmin() {
        return admin;
    }

    public static Admin loadAdmin() {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(Admin.SYSTEM_DATA_PATH + Admin.SYSTEM_FILE));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Admin) objectInputStream.readObject();
        } catch (Exception e) {
            System.out.println("No system file found!");
            return null;
        }
    }
}
