package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainPage {
    private JPanel panMain;
    private JButton btnLogOut;
    private JButton btnAddCourse;
    private JButton btnRmCourse;
    private JButton btnCreateQuiz;
    private JLabel lblGreeting;
    private JPanel panSchedule;
    private JLabel lblSchedule;
    private JButton btnCreateCourse;
    private JTextArea txtUserInfo;

    private Admin admin;
    private User user;
    private JFrame frame;


    public static void main(Admin admin, JFrame frame){
        MainPage mainPage = new MainPage(admin,frame);
        frame.setContentPane(mainPage.getPanMain());

        mainPage.lblGreeting.setText("Welcome " + admin.getCurrentUser().getFirstName() + "!");
        mainPage.txtUserInfo.setText(admin.getCurrentUser().toString());
        mainPage.panSchedule.setLayout(new BoxLayout(mainPage.panSchedule, BoxLayout.Y_AXIS));
    }

    public JPanel getPanMain(){
        return panMain;
    }

    public MainPage(Admin ad, JFrame fr) {
        admin = ad;
        frame = fr;
        user = admin.getCurrentUser();
        panSchedule.setLayout(new FlowLayout());

        if(user.getStatus().equalsIgnoreCase("professor")){
            btnCreateQuiz.setVisible(true);
            btnCreateCourse.setVisible(true);
        } else {
            btnCreateQuiz.setVisible(false);
            btnCreateCourse.setVisible(false);
        }

        btnLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.logOut();
                admin.save();
                //TODO: code to switch to LogInSignUp form
                LogInSignUp.main(admin,frame);
                frame.revalidate();
                frame.repaint();
            }
        });

        for(String courseId: user.getCourseIds()){

            JButton course = new JButton(admin.getCourse(courseId).toString() + "\n");
            course.setFont(new Font("Courier New", Font.BOLD, 12));
            course.setBorderPainted(false);

            course.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    admin.setCurrentCourse(admin.getCourse(courseId));
                    CourseLayout.main(admin,frame);
                    frame.revalidate();
                    frame.repaint();
                }
            });

            panSchedule.add(course);
        }

        btnAddCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: open CourseSearch
                CourseSearch.main(admin,frame,"main");
                frame.revalidate();
                frame.repaint();
            }
        });

        btnRmCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: show RmCourseFrSche
                RmCourseFrSche.main(admin,frame);
                frame.revalidate();
                frame.repaint();
            }
        });

        btnCreateCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateCourse.main(admin,frame);
                frame.revalidate();
                frame.repaint();
            }
        });

        btnCreateQuiz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Quiz quiz = new Quiz();
                admin.setCurrentQuiz(quiz);
                AddQuiz.main(admin,frame);
                frame.revalidate();
                frame.repaint();
            }
        });
    }

}
