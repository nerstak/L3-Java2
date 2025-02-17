package oo.Questions;

import org.json.JSONObject;

import java.util.Vector;

/**
 * Class for multiple choice question
 *
 * @param <T> String
 */
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
        return answers;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("MQC: " + text + ":\n");
        for (String s : answers) {
            string.append("   ").append(s).append("\n");
        }
        string.append("Correct answer: ").append(correctAnswer);
        return string.toString();
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
