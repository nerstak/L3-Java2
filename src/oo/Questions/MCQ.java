package oo.Questions;

import java.util.Vector;

public class MCQ<T extends String> extends AbstractStatement<T> {
    private final Vector<String> answers;

    public MCQ(String text, String answer1, String answer2, String answer3, T correctAnswer) {
        super(text, correctAnswer);
        this.answers = new Vector<>() {{
            add(answer1);
            add(answer2);
            add(answer3);
        }};
    }

    public Vector<String> getAnswers() {
        return answers; // Might return copy?
    }

    @Override
    public String toString() {
        String string = "MQC: " + text + ":\n";
        for (String s : answers) {
            string += "   " + s + "\n";
        }
        string += "Correct answer: " + correctAnswer;
        return string;
    }

    @Override
    public void display() {
        System.out.println(this.toString());
    }

    @Override
    public boolean checkAnswer(T t) {
        return correctAnswer.equals(t);
    }
}
