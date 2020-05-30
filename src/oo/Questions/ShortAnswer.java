package oo.Questions;

public class ShortAnswer<T extends String> extends AbstractStatement<T> {
    public ShortAnswer(String text, T correctAnswer) {
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
    public boolean checkAnswer(T t) {
        return correctAnswer.equals(t);
    }
}
