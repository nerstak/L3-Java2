package oo;

/**
 * Parent class for all question types
 */
public abstract class AbstractType {
    protected String text;

    public AbstractType(String text) {
        this.text = text;
    }

    protected String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
