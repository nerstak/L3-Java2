package oo.Questions;

import java.util.ArrayList;

public class Themes {
    private final ArrayList<String> listThemes;
    private int indicator = -1;

    public Themes() {
        listThemes = new ArrayList<>() {{
            add("lol");
            add("mdr");
        }};
    }

    public int getIndicator() {
        return indicator;
    }

    public String getAtIndex(int index) {
        if (index >= 0 && index < listThemes.size()) {
            return listThemes.get(index);
        }
        return "";
    }

    /**
     * Replace theme by another
     *
     * @param oldTheme Theme to replace
     * @param newTheme Theme to add
     * @return Integrity of the operation
     */
    public boolean alterTheme(String oldTheme, String newTheme) {
        for (int i = 0; i < listThemes.size(); i++) {
            if (listThemes.get(i).equals(oldTheme)) {
                listThemes.set(i, newTheme);
                return true;
            }
        }
        return false;
    }

    /**
     * Replace theme at specified index
     *
     * @param i        Index
     * @param newTheme Theme to replace
     * @return Integrity of the operation
     */
    public boolean alterTheme(int i, String newTheme) {
        try {
            listThemes.set(i, newTheme);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String toString() {
        String s = "Theme indicator = " + this.indicator + "\n";
        for (String t : listThemes) {
            s += t + "\n";
        }
        return s;
    }

    /**
     * Display list of themes
     */
    public void display() {
        System.out.println(this.toString());
    }

    /**
     * Select a theme (for the moment, only in sequential order
     *
     * @return Index of the theme selected
     */
    public int selectTheme() {
        //TODO: Should depends on the phase: 1 -> sequential; 2 -> Random; 3 -> Preselected
        // Phase1
        if (indicator < 0) {
            indicator = 0;
        } else {
            indicator = indicator++ % listThemes.size();
        }

        /* Phase2
        int newI;
        do {
            newI = (int) (Math.random() * listThemes.size());
        } while (indicator == newI);
        indicator = newI;
        */

        return indicator;
    }

    //TODO: SelectFiveThemes()

}
