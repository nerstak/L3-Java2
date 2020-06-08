package oo.Questions;

public interface Statement<T> {
    void display();

    boolean checkAnswer(T u);

    String getText();

    String getCorrectAnswer();

    int getType();

    String getInstance();
}
