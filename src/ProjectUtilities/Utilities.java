package ProjectUtilities;

public class Utilities {

    /**
     * Add 0 to time if it is only one digit
     *
     * @param s Time in string
     * @return Time with correct length
     */
    public static String lengthTime(String s) {
        if (s.length() == 1) {
            return "0" + s;
        }

        return s;
    }
}
