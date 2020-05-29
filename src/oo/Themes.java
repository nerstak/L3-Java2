package oo;

import java.util.HashMap;
import java.util.Map;

public class Themes {
    private HashMap<String, Boolean> listThemes;

    public Themes() {
        listThemes = new HashMap<>();
    }

    /**
     * Replace theme by another
     *
     * @param oldTheme Theme to replace
     * @param newTheme Theme to add
     * @return Integrity of the operation
     */
    public boolean alterTheme(String oldTheme, String newTheme) {
        if (listThemes.containsKey(oldTheme)) {
            listThemes.remove(oldTheme);
            listThemes.put(newTheme, false);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String s = "";
        for (Map.Entry<String, Boolean> entry : listThemes.entrySet()) {
            s += entry.getKey() + ": " + entry.getValue() + "\n";
        }
        return s;
    }

    public void display() {
        System.out.println(this.toString());
    }

    //TODO: SelectTheme(); SelectFiveThemes()

}
