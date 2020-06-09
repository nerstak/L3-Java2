package oo.Game;

/**
 * Enumeration for the difficulty of questions
 */
public enum Difficulty {
    easy,
    medium,
    hard;

    /**
     * Convert an integer to a difficulty
     *
     * @param x integer (1...3)
     * @return Difficulty or null
     */
    public static Difficulty fromInteger(int x) {
        return switch (x) {
            case 1 -> easy;
            case 2 -> medium;
            case 3 -> hard;
            default -> null;
        };
    }

    /**
     * Convert a string to a difficulty
     *
     * @param s string in low Case
     * @return Difficulty or null
     */
    public static Difficulty fromString(String s) {
        return switch (s) {
            case "easy" -> easy;
            case "medium" -> medium;
            case "hard" -> hard;
            default -> null;
        };
    }
}
