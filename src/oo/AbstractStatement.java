package oo;

/**
 * Parent class for all question types
 */
public abstract class AbstractStatement<T> implements Statement {
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
}
