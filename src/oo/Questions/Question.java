package oo.Questions;

import oo.Game.Difficulty;

import java.io.Serializable;

public class Question<T extends AbstractStatement<T>> implements Serializable {
    private final int number;
    private final String theme;
    private final Difficulty difficulty;
    private final T statement;

    private static int questionNumber = 0;

    public Question(T statement, String theme, Difficulty difficulty) {
        this.difficulty = difficulty;
        this.theme = theme;
        this.statement = statement;
        this.number = questionNumber++;
    }

    public int getNumber() {
        return number;
    }

    public String getTheme() {
        return theme;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public T getStatement() {
        return statement;
    }

    @Override
    public String toString() {
        return "Question: " +
                "number = " + number +
                ", theme = '" + theme + '\'' +
                ", difficulty = " + difficulty +
                "\n statement = \n" + statement.toString();
    }

    public void display() {
        System.out.println(this.toString());
    }

    // TODO: input() ??? No idea what it means
}
