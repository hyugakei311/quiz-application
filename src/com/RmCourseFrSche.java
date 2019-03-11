package com;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class RmCourseFrSche {
    private JPanel panRmCourseFrSche;
    private JLabel lblRmCourse;
    private JButton btnBack;
    private JButton btnLogOut;
    private JLabel lblGreeting;
    private JButton btnRemoveCourse;
    private JLabel lblStatus;
    private JPanel panCourse;

    private Admin admin;
    private User user;
    private JFrame frame;
    private ArrayList<String> choice;

    public static void main(Admin admin, JFrame frame){
        RmCourseFrSche rmCourseFrSche = new RmCourseFrSche(admin,frame);
        frame.setContentPane(rmCourseFrSche.getPanRmCourseFrSche());

        rmCourseFrSche.lblGreeting.setText("Hi " + admin.getCurrentUser().getFirstName() + "!");
    }

    public JPanel getPanRmCourseFrSche() {
        return panRmCourseFrSche;
    }

    public RmCourseFrSche(Admin ad, JFrame fr) {
        admin = ad;
        frame = fr;
        user = admin.getCurrentUser();
        choice = new ArrayList<>();
        lblStatus.setVisible(false);
        panCourse.setLayout(new BoxLayout(panCourse, BoxLayout.Y_AXIS));

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: codes to switch to MainPage form
                MainPage.main(admin,frame);
                frame.revalidate();
                frame.repaint();
            }
        });

        btnLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.logOut();
                //TODO: code to switch to LogInSignUp form
                LogInSignUp.main(admin,frame);
                frame.revalidate();
                frame.repaint();
            }
        });


        for (String courseId : user.getCourseIds()) {
            JCheckBox cb = new JCheckBox(admin.getCourse(courseId).toString());
            panCourse.add(cb);
            cb.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    lblStatus.setVisible(false);
                    if (e.getStateChange() == ItemEvent.SELECTED){
                        choice.add(courseId);
                    } else if (e.getStateChange() == ItemEvent.DESELECTED){
                        choice.remove(courseId);
                    }
                }
            });
        }

        btnRemoveCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(String courseId : choice){
                    if(user.getStatus().equalsIgnoreCase("professor")) {
                        if(admin.removeCourse(courseId)){
                            user.removeCourseFromSchedule(courseId);
                            lblStatus.setVisible(true);
                            admin.save();
                        }
                    } else {
                        if(user.removeCourseFromSchedule(courseId)){
                            lblStatus.setVisible(true);
                            admin.save();
                        }
                    }
                }
                panCourse.updateUI();
            }
        });

    }

}
