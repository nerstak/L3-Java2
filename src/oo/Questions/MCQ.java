package oo.Questions;

import org.json.JSONObject;

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

    public MCQ(JSONObject json) throws IllegalStateException {
        super(json.getString("text"), (T) json.getString("correctAnswer"));

        this.answers = new Vector<>();
        for (int i = 0; i < 3; i++) {
            answers.add(json.getJSONArray("answers").getString(i));
        }
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

    @Override
    public int getType() {return 1;}
}
