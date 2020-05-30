package oo;

public class Question<T extends AbstractStatement> {
    private int number;
    private String theme;
    private Difficulty difficulty;
    private T statement;

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
        return "Question{" +
                "number=" + number +
                ", theme='" + theme + '\'' +
                ", difficulty=" + difficulty +
                "\n statement=" + statement.toString() +
                '}';
    }

    public void display() {
        System.out.println(this.toString());
    }

    // TODO: input() ??? No idea what it means
}
