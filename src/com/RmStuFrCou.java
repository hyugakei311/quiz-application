package com;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class RmStuFrCou {
    private JPanel panRmStuFrCou;
    private JPanel panStudent;
    private JLabel lblGreeting;
    private JButton btnBack;
    private JButton btnLogOut;
    private JButton btnRemoveStudent;
    private JLabel lblStatus;
    private JLabel lblRmStudent;

    private Admin admin;
    private User user;
    private JFrame frame;
    private Course course;
    private ArrayList<String> choice;

    public static void main(Admin admin, JFrame frame){
        RmStuFrCou rmStuFrCou = new RmStuFrCou(admin,frame);
        frame.setContentPane(rmStuFrCou.getPanRmStuFrCou());

        rmStuFrCou.lblGreeting.setText("Hi " + admin.getCurrentUser().getFirstName() + "!");
    }

    public JPanel getPanRmStuFrCou() {
        return panRmStuFrCou;
    }

    public RmStuFrCou(Admin ad, JFrame fr) {
        admin = ad;
        frame = fr;
        user = admin.getCurrentUser();
        course = admin.getCurrentCourse();
        lblStatus.setVisible(false);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: codes to switch to MainPage form
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
                //TODO: code to switch to LogInSignUp form
                LogInSignUp.main(admin, frame);
                frame.revalidate();
                frame.repaint();
            }
        });

        for (String stuId : course.getRoll()) {
            String display = stuId + "\t" + admin.getStudent(stuId).getFirstName() + " " + admin.getStudent(stuId).getLastName();
            JCheckBox cb = new JCheckBox(display);
            panStudent.add(cb);
            cb.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    lblStatus.setVisible(false);
                    if (e.getStateChange() == ItemEvent.SELECTED){
                        choice.add(stuId);
                    } else if (e.getStateChange() == ItemEvent.DESELECTED){
                        choice.remove(stuId);
                    }
                }
            });
        }

        btnRemoveStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(String stuId : choice) {
                    if(course.removeStudent(stuId)){
                        lblStatus.setVisible(true);
                    }
                }
                panStudent.updateUI();
            }
        });
    }

}
