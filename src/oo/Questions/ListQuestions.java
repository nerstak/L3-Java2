package oo.Questions;

import Project.Main;
import ProjectUtilities.JSONParser;
import oo.Game.Difficulty;
import oo.Game.PhaseEnum;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ListQuestions implements Serializable {
    private final LinkedList<Question<?>> listQuestions;
    private final int selected = -1;

    public ListQuestions(String theme) {
        listQuestions = new LinkedList<>();
        JSONObject json = JSONParser.parseFile(theme + ".json");

        if (json != null) {
            readQuestionJSON(json, theme);
        }
    }

    public ListQuestions() {
        listQuestions = new LinkedList<>();
    }

    /**
     * Read a JSON object storing questions
     *
     * @param json  JSON object
     * @param theme Theme loaded
     */
    private void readQuestionJSON(JSONObject json, String theme) {
        // Reading the JSON object by object
        // Note that using try-catch statements allows to skip a question with syntax errors
        for (Object o : json.getJSONArray("questions")) {
            JSONObject tmp = (JSONObject) o;
            AbstractStatement<?> s = null;

            // Creating the Statement
            switch (tmp.getString("type")) {
                case "MCQ": {
                    try {
                        s = new MCQ(tmp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "ShortAnswer": {
                    try {
                        s = new ShortAnswer(tmp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "TrueFalse": {
                    try {
                        s = new TrueFalse(tmp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                default:
                    throw new IllegalStateException("Unexpected value: " + tmp.getString("type"));
            }

            if (s != null) {
                // Creating the question
                try {
                    listQuestions.add(new Question(
                            s,
                            theme,
                            Difficulty.fromInteger(tmp.getInt("difficulty"))
                    ));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * Add a question
     *
     * @param q Question to add
     */
    public void addQuestion(Question<?> q) {
        listQuestions.add(q);
    }

    /**
     * Get the size of the list of questions
     *
     * @return the size of the list of the questions
     */
    public int size() {
        return listQuestions.size();
    }

    /**
     * Get the question at a desired index
     *
     * @param i the index of the question
     * @return the question at the desired index
     */
    public Question get (int i) {
        return listQuestions.get(i);
    }

    /**
     * Remove a question at specified index
     *
     * @param n Index
     */
    public void deleteQuestion(int n) {
        listQuestions.remove(n);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("List of Questions: \n");
        for (Question<?> q : listQuestions) {
            s.append(q.toString());
            s.append("\n\n");
        }
        return s.toString();
    }

    /**
     * Display list of questions
     */
    public void display() {
        System.out.println(this.toString());
    }

    public class NoQuestionForDesiredPhaseException extends Exception {
        public NoQuestionForDesiredPhaseException (PhaseEnum desiredPhase) {
            super("Couldn't find a question for the phase '" + desiredPhase + "' and according difficulty in question list");
        }
    }

    public Question<?> selectQuestion(PhaseEnum phaseEnum) throws NoQuestionForDesiredPhaseException {
        List<Question<?>> filteredQuestions = switch (phaseEnum) {
            // TODO : utiliser la méthode round robin pour la première phase
            case Phase1 ->
                    listQuestions
                    .stream()
                    .filter(q -> q.getDifficulty() == Difficulty.easy)
                    .collect(Collectors.toList());
            case Phase2 -> listQuestions
                    .stream()
                    .filter(q -> q.getDifficulty() == Difficulty.medium)
                    .collect(Collectors.toList());
            default -> listQuestions;
        };

        if (filteredQuestions.size() == 0)
            throw new NoQuestionForDesiredPhaseException(Main.game.getCurrentPhase());

        Random random = new Random();
        int index = random.nextInt(filteredQuestions.size());
        return filteredQuestions.get(index);
    }
}
