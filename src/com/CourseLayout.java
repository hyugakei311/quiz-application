package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CourseLayout {
    private JButton btnBack;
    private JButton btnLogOut;
    private JLabel lblGreeting;
    private JPanel panQuizList;
    private JLabel lblQuizList;
    private JPanel panCourseLayout;
    private JButton btnRmStudent;
    private JTextArea txtCourseInfo;

    private Admin admin;
    private JFrame frame;
    private User user;
    private Course course;
    private ArrayList<String> removingStu = new ArrayList<>();

    public static void main(Admin admin, JFrame frame){
        CourseLayout courseLayout = new CourseLayout(admin,frame);
        frame.setContentPane(courseLayout.getPanCourseLayout());

        courseLayout.lblGreeting.setText("Hi " + admin.getCurrentUser().getFirstName() + "!");
        courseLayout.txtCourseInfo.setText(admin.getCurrentCourse().toString(admin.getProfessor(admin.getCurrentCourse().getProfessorId())));
        courseLayout.txtCourseInfo.setEditable(false);
    }


    public JPanel getPanCourseLayout(){
        return panCourseLayout;
    }

    public CourseLayout(Admin ad, JFrame fr) {
        admin = ad;
        frame = fr;
        user = admin.getCurrentUser();
        course = admin.getCurrentCourse();
        panQuizList.setLayout(new FlowLayout());

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
                admin.save();
                //TODO: code to switch to SignUpLogIn form
                LogInSignUp.main(admin,frame);
                frame.revalidate();
                frame.repaint();
            }
        });

        if(user.getStatus().equalsIgnoreCase("professor")){
            btnRmStudent.setVisible(true);
        } else {
            btnRmStudent.setVisible(false);
        }

        btnRmStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RmStuFrCou.main(admin, frame);
                frame.revalidate();
                frame.repaint();
            }
        });

        for(String quizId: course.getQuizzes()){
            JButton quiz = new JButton(admin.getQuiz(quizId).getQuizName());
            quiz.setFont(new Font("Courier New", Font.BOLD, 12));
            quiz.setBorderPainted(false);

            quiz.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    admin.setCurrentQuiz(admin.getQuiz(quizId));
                    QuizLayout.main(admin,frame);
                    frame.revalidate();
                    frame.repaint();
                }
            });

            panQuizList.add(quiz);
        }

    }
}
