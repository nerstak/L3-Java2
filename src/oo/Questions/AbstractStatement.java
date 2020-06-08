package oo.Questions;

/**
 * Parent class for all question types
 */
public abstract class AbstractStatement<T> implements Statement<T> {
    protected String text;
    protected T correctAnswer;
    protected int type;
    protected String instance;

    public AbstractStatement(String text, T correctAnswer) {
        this.text = text;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getCorrectAnswer() {return (String) correctAnswer.toString();}

    @Override
    public int getType() {return type;}

    @Override
    public String getInstance() {return instance;}
}
