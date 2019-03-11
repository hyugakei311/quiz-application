package com;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CreateCourse {

    private JPanel panCreateCourse;
    private JLabel lblGreeting;
    private JButton btnBack;
    private JButton btnLogOut;
    private JButton btnCreateCourse;
    private JLabel lblCourseName;
    private JLabel lblCourseId;
    private JTextField txtCourseName;
    private JTextField txtCourseId;
    private JLabel lblStatus;


    private Admin admin;
    private User user;
    private JFrame frame;
    private String currCourseName;
    private String currCourseId;

    public static void main(Admin admin, JFrame frame){
        CreateCourse createCourse = new CreateCourse(admin,frame);
        frame.setContentPane(createCourse.getPanCreateCourse());

        createCourse.lblGreeting.setText("Hi " + admin.getCurrentUser().getFirstName() + "!");
    }

    public JPanel getPanCreateCourse() {
        return panCreateCourse;
    }

    public CreateCourse(Admin ad, JFrame fr) {
        admin = ad;
        frame = fr;
        user = admin.getCurrentUser();
        lblStatus.setVisible(false);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPage.main(admin, frame);
                frame.revalidate();
                frame.repaint();
            }
        });

        btnLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.logOut();
                admin.save();
                LogInSignUp.main(admin, frame);
                frame.revalidate();
                frame.repaint();
            }
        });

        btnCreateCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Course course = ((Professor) user).createCourse(txtCourseName.getText(), txtCourseId.getText());
                admin.addCourse(course);
                admin.save();
                lblStatus.setVisible(true);
            }
        });
    }
}
