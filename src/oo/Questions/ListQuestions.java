package oo.Questions;

import Project.Main;
import ProjectUtilities.JSONParser;
import oo.Game.Difficulty;
import oo.Game.PhaseEnum;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.*;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
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
            try {
                switch (tmp.getString("type")) {
                    case "MCQ":
                        s = new MCQ(tmp);
                        break;

                    case "ShortAnswer":
                        s = new ShortAnswer(tmp);
                        break;

                    case "TrueFalse":
                        s = new TrueFalse(tmp);
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + tmp.getString("type"));
                }
            } catch (Exception e) {
                e.printStackTrace();
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
        System.out.println(this.toString());   //When in display, "ShortAnswer" is printed as "TrueFalse", but it doesn't not seems to impact the list
    }

    public static class NoQuestionForDesiredPhaseException extends Exception {
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

        // We use Math.random as we need a different seed every time
        int index = (int) (Math.random() * filteredQuestions.size());
        return filteredQuestions.get(index);
    }

    public void writeJson(String theme) {
        JSONObject object = new JSONObject();
        JSONArray questions = new JSONArray();
        for (Question<?> question : this.listQuestions) {
            JSONObject obj = new JSONObject();
            obj.put("text", question.getStatement().getText());
            obj.put("correctAnswer", question.getStatement().getCorrectAnswer());

            switch (question.getDifficulty()) {
                case easy:
                    obj.put("difficulty", 1);
                    break;
                case medium:
                    obj.put("difficulty", 2);
                    break;
                default:
                    obj.put("difficulty", 3);
                    break;
            }

            if (question.getStatement() instanceof MCQ) {
                JSONArray answers = new JSONArray();
                obj.put("answers", answers);
                obj.put("type", "MCQ");
                for (int i = 0; i < 3; i ++) {
                    answers.put(( (MCQ<?>) question.getStatement()).getAnswers().get(i));
                }
            } else if (question.getStatement() instanceof ShortAnswer) {
                obj.put("type", "ShortAnswer");
            } else if (question.getStatement() instanceof TrueFalse) {
                obj.put("type", "TrueFalse");
            }
            questions.put(obj);
        }

        object.put("questions", questions);
        JSONParser.writeFile(object,theme);
    }

    public LinkedList<Question<?>> getList() {
        return this.listQuestions;
    }
}
