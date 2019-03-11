package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddQuiz {
    private JPanel panAddQuiz;
    private JButton btnAddQuestion;
    private JButton btnRemoveQuestion;
    private JButton btnSaveQuiz;
    private JButton btnBack;
    private JButton btnLogOut;
    private JLabel lblGreeting;
    private JLabel lblQuizName;
    private JLabel lblQuizDeadline;
    private JLabel lblQuizInstruction;
    private JTextField txtQuestion;
    private JTextField txtPoint;
    private JCheckBox chkCorrectOption;
    private JButton btnAddOption;

    private Admin admin;
    private JFrame frame;
    private JTextField txtOption;
    private JTextField txtDeadline;
    private JTextField txtQuizName;
    private JTextField txtInstruction;
    private JLabel lblTimeLimit;
    private JTextField txtTimeLimit;
    private JPanel panQuestions;
    private JLabel lblStatus;
    private User user;

    private ArrayList<Option> options = new ArrayList<>();
    private Quiz currQuiz;

    public static void main(Admin admin, JFrame frame){
        AddQuiz addQuiz = new AddQuiz(admin,frame);
        frame.setContentPane(addQuiz.getPanAddQuiz());

        addQuiz.lblGreeting.setText("Hi " + admin.getCurrentUser().getFirstName() + "!");
    }

    public JPanel getPanAddQuiz() {
        return panAddQuiz;
    }

    public AddQuiz(Admin ad, JFrame fr) {
        admin = ad;
        frame = fr;
        user = admin.getCurrentUser();
        currQuiz = new Quiz();
        lblStatus.setVisible(false);
        panQuestions.setLayout(new BoxLayout(panQuestions, BoxLayout.Y_AXIS));
        panQuestions.removeAll();
        panQuestions.updateUI();
        panQuestions.revalidate();
        panQuestions.repaint();

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //move to form MainPage
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
                //Go back to form LogInSignUp
                LogInSignUp.main(admin,frame);
                frame.revalidate();
                frame.repaint();
            }
        });

        btnAddOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Option option = collectOption();
                if(option != null){
                    options.add(option);
                }
                txtOption.setText("");
                chkCorrectOption.setSelected(false);
                lblStatus.setVisible(true);
            }
        });
        btnAddQuestion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblStatus.setVisible(true);
                //Input into system
                Question currQuestion = collectQuestion();
                currQuiz.addQuestion(currQuestion);
                txtQuestion.setText("");
                txtPoint.setText("");
                txtOption.setText("");
                chkCorrectOption.setSelected(false);
                //Output to list
                panQuestions.add(new JLabel(currQuestion.toString()));
                //ButtonGroup group = new ButtonGroup();
                if(currQuestion.getNumCorrectOption() == 1) {

                    for (Option op : currQuestion.getOptions()) {
                        JRadioButton rd = new JRadioButton(op.getText());
                        panQuestions.add(rd);
                        //group.add(rd);
                    }
                } else if (currQuestion.getNumCorrectOption() > 1){

                    for (Option op : currQuestion.getOptions()) {
                        JCheckBox cb = new JCheckBox(op.getText());
                        panQuestions.add(cb);
                        //group.add(cb);
                    }
                }
                panQuestions.revalidate();
                panQuestions.repaint();
            }
        });

        btnSaveQuiz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    currQuiz.setQuizName(txtQuizName.getText());
                    currQuiz.setDeadline((new SimpleDateFormat("hh:mm MM/dd/yyyy")).parse(txtDeadline.getText()));
                    currQuiz.setInstructions(txtInstruction.getText());
                    currQuiz.setTime(Integer.parseInt(txtTimeLimit.getText()));
                    admin.addQuiz(currQuiz);
                    admin.setCurrentQuiz(currQuiz);
                    admin.save();

                    CourseSearch.main(admin,frame,"addQuiz");
                    frame.revalidate();
                    frame.repaint();
                } catch (ParseException exception) {
                    lblStatus.setVisible(true);
                    lblStatus.setText("Deadline date format is not correct!");
                } catch (NumberFormatException exception) {
                    lblStatus.setVisible(true);
                    lblStatus.setText("Time limit is not a number!");
                }
            }
        });
    }

    private Option collectOption(){
        if(txtOption.getText().equals("")){
            return null;
        }
        return new Option(txtOption.getText(), chkCorrectOption.isSelected());
    }

    private Question collectQuestion(){
        Question question = new Question(txtQuestion.getText(), Double.parseDouble(txtPoint.getText()));
        for(Option op : options){
            question.addOption(op);
        }
        options.clear();
        return question;
    }
}
