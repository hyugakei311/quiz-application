package com;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LogInSignUp {
    private JPanel pnlLISU;
    private JTabbedPane tabLISU;
    private JTextField txtEmailAddLI;
    private JButton btnLogIn;
    private JTextField txtFirstName;
    private JPasswordField pswPasswordLI;
    private JTextField txtLastName;
    private JTextField txtEmailAddSU;
    private JButton btnSignUp;
    private JPasswordField pswPasswordSU;
    private JLabel signUpStatus;
    private JLabel loginStatus;

    private Admin admin;
    private JFrame frame;

    public static void main(Admin admin,JFrame frame){
        LogInSignUp logInSignUp = new LogInSignUp(admin,frame);
        System.out.println("work1");
        logInSignUp.getPnlLISU().setOpaque(true);
        frame.setContentPane(logInSignUp.getPnlLISU());
        System.out.println("work2");
    }

    public JPanel getPnlLISU() { //Panel Log In Sign Up
        return pnlLISU;
    }

    public LogInSignUp(Admin ad, JFrame fr) {
        admin = ad;
        frame = fr;
        signUpStatus.setVisible(false);
        loginStatus.setVisible(false);

        btnLogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String psw = "";
                for (char ch:pswPasswordLI.getPassword()){
                    psw += ch;
                }
                try {
                    User user = admin.logIn(txtEmailAddLI.getText(), psw);
                    if (user == null) {
                        loginStatus.setText("Account not found");
                    } else {
                        loginStatus.setText("Successfully Log In!");
                        MainPage.main(admin,frame);
                        frame.revalidate();
                        frame.repaint();
                    }
                } catch (IllegalArgumentException exception) {
                    loginStatus.setText("Email format is not correct!");
                } finally {
                    loginStatus.setVisible(true);
                }
            }
        });

        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String psw = "";
                signUpStatus.setVisible(false);
                for (char ch : pswPasswordSU.getPassword()) {
                    psw += ch;
                }
                try {
                    admin.addUser(txtFirstName.getText(), txtLastName.getText(), txtEmailAddSU.getText(), psw);
                    admin.save();
                    signUpStatus.setText("Successfully Sign Up!");
                } catch (IllegalArgumentException exception) {
                    signUpStatus.setText("Email format is not correct!");
                } finally {
                    signUpStatus.setVisible(true);
                }
            }
        });

        //User logs by pressing enter after inputting password
        pswPasswordLI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String psw = "";
                for (char ch:pswPasswordLI.getPassword()){
                    psw += ch;
                }
                try {
                    User user = admin.logIn(txtEmailAddLI.getText(), psw);
                    if (user == null) {
                        loginStatus.setText("Account not found");
                    } else {
                        loginStatus.setText("Successfully Log In!");
                        MainPage.main(admin,frame);
                    }
                } catch (IllegalArgumentException exception) {
                    loginStatus.setText("Email format is not correct!");
                } finally {
                    loginStatus.setVisible(true);
                }
            }
        });
        txtFirstName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUpStatus.setVisible(false);
                txtLastName.requestFocus();
            }
        });
        txtLastName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtEmailAddSU.requestFocus();
            }
        });
        txtEmailAddSU.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pswPasswordSU.requestFocus();
            }
        });
        pswPasswordSU.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSignUp.requestFocus();
            }
        });
        txtEmailAddLI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginStatus.setVisible(false);
                pswPasswordLI.requestFocus();
            }
        });
        txtFirstName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtFirstName.setText("");
                txtLastName.setText("");
                txtEmailAddSU.setText("");
                pswPasswordSU.setText("");
            }
        });
    }
}
