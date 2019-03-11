package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Date;

public class QuizLayout {
    private JPanel panQuizLayout;
    private JTabbedPane tabQuiz;
    private JLabel lblGreeting;
    private JLabel lblQuizName;
    private JLabel lblQuizInstruction;
    private JButton btnBack;
    private JButton btnLogOut;
    private JButton btnSubmitQuiz;
    private JTextArea txtReviewQuiz;
    private JPanel panTakeQuiz;
    private JPanel panReviewQuiz;
    private JPanel panQuiz;
    private JButton btnAddQuizToCou;
    private JButton btnRmQuizFrCou;
    private JPanel panSubmit;
    private JPanel panStart;
    private JButton btnStartQuiz;
    private JLabel lblTimeLeft;
    private JButton btnSaveProgress;
    private JLabel lblSaved;
    private JPanel panQuestion;

    private Admin admin;
    private JFrame frame;
    private Quiz quiz;
    private User user;
    private Date today;

    public static void main(Admin admin, JFrame frame){
        QuizLayout quizLayout = new QuizLayout(admin,frame);
        frame.setContentPane(quizLayout.getPanQuizLayout());

        quizLayout.lblGreeting.setText("Hi " + admin.getCurrentUser().getFirstName() + "!");
    }

    public JPanel getPanQuizLayout(){
        return panQuizLayout;
    }

    public QuizLayout(Admin ad,JFrame fr) {
        admin = ad;
        frame = fr;
        quiz = admin.getCurrentQuiz();
        user = admin.getCurrentUser();
        today = new Date();

        panTakeQuiz.setVisible(false);
        panReviewQuiz.setVisible(false);
        panQuestion.setVisible(false);

        lblTimeLeft.setVisible(false);
        btnSubmitQuiz.setVisible(false);
        btnSaveProgress.setVisible(false);
        lblSaved.setVisible(false);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: codes to switch to CourseLayout form
                CourseLayout.main(admin,frame);
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
            btnAddQuizToCou.setVisible(true);
            btnRmQuizFrCou.setVisible(true);
        } else {
            btnAddQuizToCou.setVisible(false);
            btnRmQuizFrCou.setVisible(false);
        }

        lblQuizName.setText(quiz.getQuizName());

        lblQuizInstruction.setText(quiz.getDeadlineInstruction());

        btnAddQuizToCou.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog prompt = new JDialog();
                prompt.setSize(200,100);

                JPanel p1 = new JPanel();
                p1.setLayout(new FlowLayout());
                p1.add(new JLabel("Which course do you want to add this quiz to?"));
                JTextField text = new JTextField("Ex: CIS 313 01");
                p1.add(text);
                JLabel status = new JLabel();
                p1.add(status);

                JPanel p2 = new JPanel();
                p2.setLayout(new FlowLayout());
                JButton btnAdd = new JButton("Add");
                btnAdd.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        admin.addQuizToCourse(text.getText(), quiz.getQuizId());
                        status.setText("Successfully added!");
                    }
                });
                p2.add(btnAdd);
                JButton btnClose = new JButton("Close");
                btnClose.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        prompt.dispose();
                    }
                });
                p2.add(btnClose);

                prompt.add(p1);
                prompt.add(p2);
            }
        });

        btnRmQuizFrCou.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: go back to CourseLayout (similar to btnBack)
                admin.rmQuizFrCourse(admin.getCurrentCourse().getCourseId(), quiz.getQuizId());
                CourseLayout.main(admin,frame);
                frame.revalidate();
                frame.repaint();
            }
        });

        btnStartQuiz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnStartQuiz.setVisible(false);
                lblTimeLeft.setVisible(true);
                btnSaveProgress.setVisible(true);
                btnSubmitQuiz.setVisible(true);
                panQuestion.setVisible(true);
                user.startQuiz(quiz.getQuizId());
                Timer t = new Timer(1000, new ActionListener() {
                    int time = quiz.getTime();
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        time--;
                        lblTimeLeft.setText(format(time / 3600) + ":" + format((time % 3600) / 60) + ":" + format((time % 3600) % 60));
                        if (time == 0) {
                            Timer timer = (Timer) e.getSource();
                            timer.stop();
                            btnSubmitQuiz.isSelected();
                        }
                    }
                });
                t.start();
                panTakeQuiz.setVisible(true);
                panQuestion.setVisible(true);
            }

            private String format(int i) {
                String result = String.valueOf(i);
                if (result.length() == 1) {
                    result = "0" + result;
                }
                return result;
            }

        });



        if(quiz.getDeadline().after(today)){
            panTakeQuiz.setVisible(true);
            if (!user.isSubmittedQuiz(quiz.getQuizId())) {
                //take quiz

                ArrayList<Option> answers;
                answers = user.loadProgress(quiz.getQuizId());

                for(Question question: quiz.getQuestions()){

                    panQuestion.add(new JLabel(question.toString()));

                    if(question.getNumCorrectOption() == 1) {
                        ButtonGroup group = new ButtonGroup();

                        for (Option op : question.getOptions()) {
                            JRadioButton rd = new JRadioButton(op.getText());
                            if (answers.contains(op)) {
                                rd.setSelected(true);
                            }
                            rd.setActionCommand(op.getText());
                            panQuestion.add(rd);
                            group.add(rd);
                            rd.addItemListener(new ItemListener() {
                                @Override
                                public void itemStateChanged(ItemEvent e) {
                                    if (e.getStateChange() == ItemEvent.SELECTED){
                                        answers.add(op);
                                    } else if (e.getStateChange() == ItemEvent.DESELECTED){
                                        answers.remove(op);
                                    }
                                }
                            });
                        }
                    } else if (question.getNumCorrectOption() > 1){

                        for (Option op : question.getOptions()) {
                            JCheckBox cb = new JCheckBox(op.getText());
                            cb.setActionCommand(op.getText());
                            if (answers.contains(op)) {
                                cb.setSelected(true);
                            }
                            panQuestion.add(cb);
                            cb.addItemListener(new ItemListener() {
                                @Override
                                public void itemStateChanged(ItemEvent e) {
                                    if (e.getStateChange() == ItemEvent.SELECTED){
                                        answers.add(op);
                                    } else if (e.getStateChange() == ItemEvent.DESELECTED){
                                        answers.remove(op);
                                    }
                                }
                            });
                        }
                    }
                }

                btnSaveProgress.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        user.saveProgress(quiz.getQuizId());
                        lblSaved.setVisible(true);
                        ActionListener save = new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                lblSaved.setVisible(false);
                            }
                        };
                        Timer timer = new Timer(2000, save);
                        timer.setRepeats(false);
                        timer.start();
                    }
                });

                btnSubmitQuiz.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(btnSubmitQuiz.isSelected()) {
                            user.submitQuiz(quiz.getQuizId(), answers);
                            panQuestion.add(new JLabel("You have submitted this quiz."));
                            txtReviewQuiz.setText("You can review this quiz after " + quiz.getDeadline());
                        }
                    }
                });

            } else {
                //cannot take quiz again or review quiz yet
                panQuestion.add(new JLabel("You have submitted this quiz."));
            }
        } else {
            //review quiz
            panReviewQuiz.setVisible(true);
            if(user.isSubmittedQuiz(quiz.getQuizId())) {
                txtReviewQuiz.setText(user.reviewQuiz(quiz.getQuizId()));
            } else {
                txtReviewQuiz.setText("This quiz is not submitted.");
            }
        }

    }
}


