package oo.Questions;

import org.json.JSONObject;

public class TrueFalse<T extends Boolean> extends AbstractStatement<T> {
    public TrueFalse(String text, T correctAnswer) {
        super(text, correctAnswer);
    }

    public TrueFalse(JSONObject json) throws IllegalStateException {
        super(json.getString("text"), (T) Boolean.valueOf(json.getString("correctAnswer")));
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
        try {
            return t == correctAnswer;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int getType() {return 3;}
}
