package oo;

/**
 * Parent class for all question types
 */
public abstract class AbstractType<T> implements Type {
    protected String text;
    protected T correctAnswer;

    public AbstractType(String text, T correctAnswer) {
        this.text = text;
        this.correctAnswer = correctAnswer;
    }
}
