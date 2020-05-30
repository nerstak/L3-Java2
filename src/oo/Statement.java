package oo;

public interface Statement {
    void display();

    <T> boolean checkAnswer(T t);

    String getText();
}
