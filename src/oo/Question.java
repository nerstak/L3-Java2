package oo;

public class Question<T extends AbstractStatement> {
    private int number;
    //TODO: add theme
    private int difficulty;
    private T statement;
}
