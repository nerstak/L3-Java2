package oo.Questions;

import org.json.JSONObject;

/**
 * Class for Short Answer
 *
 * @param <T> String
 */
public class ShortAnswer<T extends String> extends AbstractStatement<T> {
    public ShortAnswer(String text, T correctAnswer) {
        super(text, correctAnswer);
    }

    public ShortAnswer(JSONObject json) throws IllegalStateException {
        super(json.getString("text"), (T) json.getString("correctAnswer"));
    }

    @Override
    public String toString() {
        String string = "TrueFalse: " + text + ":\n";
        string += "Correct answer: " + correctAnswer;
        return string;
    }

    @Override
    public void display() {
        System.out.println(this.toString());
    }

    @Override
    public boolean checkAnswer(T t) {
        return correctAnswer.toLowerCase().equals(t.toLowerCase());
    }
}
