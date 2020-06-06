package oo.Questions;

/**
 * Parent class for all question types
 */
public abstract class AbstractStatement<T> implements Statement<T> {
    protected String text;
    protected T correctAnswer;

    public AbstractStatement(String text, T correctAnswer) {
        this.text = text;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getCorrectAnswer() {
        return (String) correctAnswer;
    }
}
