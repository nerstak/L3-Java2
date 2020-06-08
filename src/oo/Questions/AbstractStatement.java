package oo.Questions;

/**
 * Parent class for all question types
 */
public abstract class AbstractStatement<T> implements Statement<T> {
    protected String text;
    protected T correctAnswer;
    protected int type;

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
        return correctAnswer.toString();
    }

    @Override
    public String getInstance() {
        if (this instanceof MCQ) {
            return "MCQ";
        } else if(this instanceof ShortAnswer) {
            return "ShortAnswer";
        } else {
            return "TrueFalse";
        }
    }
}
