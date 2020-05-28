package oo;

public class TrueFalse extends AbstractType {
    public TrueFalse(String text, boolean correctAnswer) {
        super(text, correctAnswer);
        this.correctAnswer = correctAnswer;
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
        try {
            return (boolean) t == (boolean) correctAnswer;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
