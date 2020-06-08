package oo.Questions;

import ProjectUtilities.JSONParser;
import oo.Game.Difficulty;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Paths;
import java.util.LinkedList;

public class ListQuestions {
    private final LinkedList<Question<?>> listQuestions;
    private final int selected = -1;

    public ListQuestions(String theme) {
        listQuestions = new LinkedList<>();
        JSONObject json = JSONParser.parseFile(theme + ".json");

        if (json != null) {
            readQuestionJSON(json, theme);
        }
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

    // TODO: NOT compliant, should be changed to respect book of charges
    public Question<?> selectQuestion() {
        int index = (int) (Math.random() * listQuestions.size());
        return listQuestions.get(index);
    }

    public void writeJson(String theme) {
        JSONObject object = new JSONObject();
        JSONArray questions = new JSONArray();
        for(Question<?> question : this.listQuestions)
        {
            JSONObject obj = new JSONObject();

            // Difficulty
            switch (question.getDifficulty()) {
                case easy: {
                    obj.put("difficulty", 1); break;
                }
                case medium: {
                    obj.put("difficulty", 2); break;
                }
                default: {
                    obj.put("difficulty", 3); break;
                }
            }

            // Text
            obj.put("text", question.getStatement().getText());

            if(question.getStatement() instanceof MCQ)
            {
                obj.put("type", "MCQ");
                JSONArray answers = new JSONArray();
                answers.put(( (MCQ<?>) question.getStatement()).getAnswers().get(0));
                answers.put(( (MCQ<?>) question.getStatement()).getAnswers().get(1));
                answers.put(( (MCQ<?>) question.getStatement()).getAnswers().get(2));
                obj.put("answers", answers);
            } else if (question.getStatement() instanceof ShortAnswer) {
                obj.put("type", "ShortAnswer");
            } else if (question.getStatement() instanceof TrueFalse) {
                obj.put("type", "ShortAnswer");
            }
            obj.put("correctAnswer", question.getStatement().getCorrectAnswer());

            questions.put(obj);
        }
        object.put("questions", questions);

        JSONParser.writeFile(object,theme);
    }

    public LinkedList<Question<?>> getList() {return this.listQuestions;}
}
