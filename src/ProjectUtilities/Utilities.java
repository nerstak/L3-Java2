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

    public static String convertTimestampToString(long timestamp) {
        String seconds = Utilities.lengthTime(String.valueOf(timestamp / 1000 % 60));
        String minutes = Utilities.lengthTime(String.valueOf(timestamp / 1000000 % 60));
        String hours = Utilities.lengthTime(String.valueOf(timestamp / 1000000000));

        return hours + "h " + minutes + "m " + seconds + "s";
    }
}
