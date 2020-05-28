package oo;

public class ShortAnswer extends AbstractStatement {
    public ShortAnswer(String text, String correctAnswer) {
        super(text, correctAnswer);
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
    public <T> boolean checkAnswer(T t) {
        return correctAnswer.equals(t);
    }
}
