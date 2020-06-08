package oo.Game;

public enum Difficulty {
    easy,
    medium,
    hard;

    public static Difficulty fromInteger(int x) {
        switch (x) {
            case 1:
                return easy;
            case 2:
                return medium;
            case 3:
                return hard;
        }
        return null;
    }

    public static Difficulty fromString(String s) {
        switch (s) {
            case "easy":
                return easy;
            case "medium":
                return medium;
            case "hard":
                return hard;
        }
        return null;
    }
}
