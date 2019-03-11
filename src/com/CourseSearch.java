package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CourseSearch {
    private JButton btnBack;
    private JTextField txtKeyword;
    private JButton btnSearch;
    private JButton btnLogOut;
    private JLabel lblGreeting;
    private JLabel lblKeyword;
    private JPanel panCourseSearch;
    private JButton btnAddCouToSche;
    private JButton btnAddCouToQuiz;
    private JLabel lblStatus;
    private JPanel panResult;

    private Admin admin;
    private User user;
    private JFrame frame;
    private ArrayList<String> result;
    private String previousPage;

    public static void main(Admin admin, JFrame frame, String previous){
        CourseSearch courseSearch = new CourseSearch(admin,frame,previous);
        frame.setContentPane(courseSearch.getPanCourseSearch());

        courseSearch.lblGreeting.setText("Hi " + admin.getCurrentUser().getFirstName() + "!");
    }

    public JPanel getPanCourseSearch(){
        return panCourseSearch;
    }

    public CourseSearch(Admin ad,JFrame fr, String previous){
        admin = ad;
        frame = fr;
        user = admin.getCurrentUser();
        result = new ArrayList<>();
        previousPage = previous;
        lblStatus.setVisible(false);
        panResult.setLayout(new BoxLayout(panResult, BoxLayout.Y_AXIS));

        if (previousPage.equals("addQuiz")) {
            btnAddCouToSche.setVisible(false);
        } else if(previousPage.equals("main")) {
            btnAddCouToQuiz.setVisible(false);
        }


        lblGreeting.setText("Hi " + user.getFirstName() + "!");

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
//
//        txtKeyword.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                txtKeyword.setText("");
//                lblStatus.setVisible(false);
//            }
//        });
//
//        txtKeyword.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                keyword = txtKeyword.getText();
//                txtKeyword.requestFocus();
//            }
//        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panResult.removeAll();
                panResult.updateUI();
                panResult.revalidate();
                panResult.repaint();
                String keyword = txtKeyword.getText();

                for(String courseId: admin.searchCourse(keyword)){
                    JCheckBox course = new JCheckBox(admin.getCourse(courseId).getCourseId() + "    " + admin.getCourse(courseId).getCourseName());
                    course.setFont(new Font("Courier New", Font.BOLD, 12));

                    course.addItemListener(new ItemListener() {
                        @Override
                        public void itemStateChanged(ItemEvent e) {
                            if (e.getStateChange() == ItemEvent.SELECTED){
                                result.add(courseId);
                            } else if (e.getStateChange() == ItemEvent.DESELECTED){
                                result.remove(courseId);
                            }
                        }
                    });
                    panResult.add(course);
                    panResult.revalidate();
                    panResult.repaint();
                }
            }

        });

        btnAddCouToQuiz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ans = JOptionPane.showConfirmDialog(frame, "Confirm add quiz to these courses?");
                if (ans == JOptionPane.YES_OPTION) {
                    for (String id : result) {
                        // TODO: implements add Quiz to Course
                        admin.getCourse(id).addQuiz(admin.getCurrentQuiz().getQuizId());
                    }
                    lblStatus.setVisible(true);
                    admin.save();
                    panResult.updateUI();
                } else{
                    CourseSearch.main(admin,frame,previousPage);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        btnAddCouToSche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ans = JOptionPane.showConfirmDialog(frame, "Confirm add courses to schedule?");
                if (ans == JOptionPane.YES_OPTION) {
                    for (String id : result) {
                        user.addCourseToSchedule(admin.getCourse(id));
                        admin.getCourse(id).addStudentToRoll(user.getUserId());
                    }
                    lblStatus.setVisible(true);
                    admin.save();
                    panResult.updateUI();
                } else{
                    CourseSearch.main(admin,frame,previousPage);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });


    }

}
