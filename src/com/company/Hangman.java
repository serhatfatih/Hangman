package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Hangman extends JFrame implements ActionListener {
    private JButton[] letters;
    private JButton neww;
    private JLabel text, score;
    private String[] words = {
            "fjord", "gazebo", "gypsy", "haiku", "haphazard",
            "hyphen", "ivory", "jazzy", "jiffy", "jinx",
            "jukebox", "kayak", "kiosk", "klutz", "memento"
    };
    private boolean[] usedWords = new boolean[words.length];
    private Random rand = new Random();
    private int numFound = 0, SCORE = 0, index = rand.nextInt(words.length);
    static int GUESS = 6;
    private String currWord = order(words[index]);
    private String temp = "";


    private Hangman() {
        setTitle("Hangman");
        getContentPane().setBackground(Color.YELLOW);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        add(new Canvas(), BorderLayout.NORTH);
        setVisible(true);

        for (int i = 0 ; i < usedWords.length ; i++)
            usedWords[i] = false;

        usedWords[index] = true;

        JPanel[] p = new JPanel[3];

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        p[0] = new JPanel(new FlowLayout()); // text
        p[1] = new JPanel(new GridLayout(3, 9)); // buttons
        p[2] = new JPanel(new FlowLayout());

        letters = new JButton[26];

        for (int i = 0 ; i < letters.length ; i++) {
            letters[i] = new JButton((char)('a' + i) + "");
            letters[i].addActionListener(this);
            p[1].add(letters[i]);
        }

        JButton reset = new JButton("reset");
        neww = new JButton("new");
        JButton exit = new JButton("exit");

        reset.addActionListener(this);
        neww.addActionListener(this);
        exit.addActionListener(e -> System.exit(0));

        p[1].add(neww);

        text = new JLabel();
        score = new JLabel("Score: " + SCORE);
        JLabel trash = new JLabel();
        reset();

        JPanel p1 = new JPanel(new GridLayout(2, 1));
        JPanel p2 = new JPanel();
        p2.setBackground(Color.WHITE);
        p1.add(p[0]);
        p1.add(p2);

        JPanel pp = new JPanel(new FlowLayout());
        pp.setBackground(Color.WHITE);
        pp.add(reset);
        pp.add(exit);

        p[0].add(text);
        p[0].add(trash);
        p[0].add(score);

        p[0].setBackground(Color.WHITE);
        p[1].setBackground(Color.WHITE);
        p[2].setBackground(Color.WHITE);

        panel.add(p1, BorderLayout.NORTH);
        panel.add(p[1], BorderLayout.CENTER);
        panel.add(pp, BorderLayout.SOUTH);
        setBackground(Color.WHITE);
        add(panel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        for (JButton letter : letters)
            if (letter.getActionCommand().equals(s))
                letter.setEnabled(false);

        if (s.equals("reset")) {
            SCORE = 0;
            index = rand.nextInt(words.length);

            for (int i = 0 ; i < usedWords.length ; i++)
                usedWords[i] = false;

            usedWords[index] = true;
            neww.setEnabled(true);
            update();
        }
        else if (s.equals("new")) {
            update(false);
            index = rand.nextInt(words.length);

            if (usedWords[index])
                while (usedWords[index])
                    index = rand.nextInt(words.length);

            usedWords[index] = true;
            update();
        }
        else {
            if (currWord.contains(s)) {
                for (int i = 0; i < currWord.length(); i++)
                    if (currWord.charAt(i) == s.charAt(0)) {
                        numFound++;
                        temp = temp.substring(0, i) + s.charAt(0) + temp.substring(i + 1);
                    }

                text.setText(temp);

                if (numFound == currWord.length() - (currWord.length() / 2)) {
                    SCORE++;
                    score.setText("Score : " + SCORE);
                    update(false);
                    if (endGame()) {
                        JOptionPane.showMessageDialog(this, "Congratulations...\nYou win...\nScore: " + SCORE);
                        System.exit(0);
                    }
                }
            }
            else {
                GUESS--;
                repaint();
            }

            if (GUESS == 0) {
                update(false);
                neww.setEnabled(false);
            }

        }
    }

    private String order(String s) {
        String temp = "";
        for (int i = 0 ; i < s.length()-1 ; i++)
            temp += s.charAt(i) + " ";

        return temp + s.charAt(s.length()-1);
    }

    private boolean endGame() {
        for (boolean usedWord : usedWords)
            if (!usedWord)
                return false;

        return true;
    }

    private void update() {
        score.setText("Score : " + SCORE);
        currWord = order(words[index]);
        numFound = 0;
        GUESS = 6;
        repaint();
        reset();

    }

    private void update(boolean b) {
        for (JButton letter : letters)
            letter.setEnabled(b);
    }

    private void reset() {
        temp = "";

        for (int i = 0 ; i < currWord.length() ; i++) {
            if (currWord.charAt(i) == ' ')
                temp += " ";
            else temp += "_";
        }

        text.setText(temp);

        update(true);
    }

    public static void main(String[] args) {
        new Hangman();
    }
}
