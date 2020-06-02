package oo.Game;

public enum Difficulty {
    easy,
    medium,
    hard;

    public static Difficulty fromInteger(int x) {
        switch (x) {
            case 0:
            case 1:
                return easy;
            case 2:
                return medium;
            case 3:
            default:
                return hard;
        }
    }
}
