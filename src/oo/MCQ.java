package oo;

import java.util.Vector;

public class MCQ extends AbstractType {
    private Vector<String> answers;
    private final String correctAnswer;

    public MCQ(String text, String answer1, String answer2, String answer3, String correctAnswer) {
        super(text);
        this.answers = new Vector<>() {{
            add(answer1);
            add(answer2);
            add(answer3);
        }};
        this.correctAnswer = correctAnswer;
    }

    public Vector<String> getAnswers() {
        return answers; // Might return copy?
    }

    public boolean checkAnswer(String s) {
        return s.equals(correctAnswer);
    }

    public boolean checkAnswer(int i) {
        return answers.get(i).equals(correctAnswer);
    }

    @Override
    public String toString() {
        String string = "MQC: " + text + ":\n";
        for (String s : answers) {
            string += "   " + s + "\n ";
        }
        string += "Correct answer";
        return string;
    }

    public void display() {
        System.out.println(this.toString());
    }
}
