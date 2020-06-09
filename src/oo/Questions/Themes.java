package oo.Questions;

import ProjectUtilities.JSONParser;
import oo.Game.PhaseEnum;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class holding themes
 */
public class Themes implements Serializable {
    private final ArrayList<String> listThemes;
    private int indicator = -1;

    public Themes() {
        listThemes = new ArrayList<>();
    }

    public int getSize() {
        return listThemes.size();
    }

    /**
     * Read list of every themes
     */
    public void readThemes() {
        JSONObject themes = JSONParser.parseFile("themes.json");
        if (themes != null) {
            listThemes.clear();
            for (Object j : themes.getJSONArray("themes")) {
                listThemes.add(String.valueOf(j));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(listThemes);
    }

    public int getIndicator() {
        return indicator;
    }

    public void add(String t) {
        listThemes.add(t);
    }

    public String remove(int index) {
        return listThemes.remove(index);
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
     * Select a theme
     *
     * @return Index of the theme selected
     */
    public int selectTheme(PhaseEnum phase) {
        if (phase == PhaseEnum.Phase1 || phase == PhaseEnum.Phase3) {
            if (indicator < 0) {
                indicator = 0;
            } else {
                indicator = ++indicator % listThemes.size();
            }
        } else if (phase == PhaseEnum.Phase2) {
            // The game should not choose the theme for phase 2
            indicator = -1;
        }

        return indicator;
    }

    /**
     * Select 5 different random themes. Not used as wrong
     *
     * @return 5 index of selected themes
     */
    public ArrayList<Integer> selectFiveRandomThemes() {
        ArrayList<Integer> themesIndex = new ArrayList<>();
        int newIndex;
        for (int i = 0; i < 5; i++) {
            do {
                newIndex = (int) (Math.random() * listThemes.size());
            } while (themesIndex.contains(newIndex));
            themesIndex.add(newIndex);
        }
        return themesIndex;
    }

    /**
     * Select 6 different random themes
     *
     * @return 6 index of selected themes
     */
    public ArrayList<Integer> selectSixRandomThemes() {
        ArrayList<Integer> themesIndex = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            int newIndex;
            do {
                newIndex = (int) (Math.random() * listThemes.size());
            } while (themesIndex.contains(newIndex));
            themesIndex.add(newIndex);
        }
        return themesIndex;
    }
}
