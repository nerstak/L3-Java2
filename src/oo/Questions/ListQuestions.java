package oo.Questions;

import ProjectUtilities.JSONParser;
import oo.Game.Difficulty;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

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
        System.out.println(this.toString());
    }

    // TODO: NOT compliant, should be changed to respect book of charges
    public Question<?> selectQuestion() {
        int index = (int) (Math.random() * listQuestions.size());
        return listQuestions.get(index);
    }

    public void writeJson(String theme) {

        String writing = "{\n   \"questions\": [\n      {\n";

        for(int i = 0; i < listQuestions.size(); i++)
        {
            Question question = listQuestions.get(i);

            writing += "            \"type\": \"" + question.getStatement().getType() + "\",\n";
            writing += "            \"difficulty\": \"";
            switch (question.getDifficulty())
            {
                case easy: {
                    writing += "1\",\n";
                    break;
                }
                case medium: {
                    writing += "2\",\n";
                    break;
                }
                default:{
                    writing += "3\",\n";
                    break;
                }
            }

            writing += "            \"text\": " + question.getStatement().getText() + ",\n";


            switch (question.getStatement().getType())
            {
                case "MCQ": {
                    writing += "            \"answers\": [\n";
                    writing += "                \"" + ((MCQ<?>) question.getStatement()).getAnswers().get(0) + "\",\n";
                    writing += "                \"" + ((MCQ<?>) question.getStatement()).getAnswers().get(1) + "\",\n";
                    writing += "                \"" + ((MCQ<?>) question.getStatement()).getAnswers().get(2) + "\"\n            ],\n";
                    writing += "            \"correctAnswer\": \"" + question.getStatement().getCorrectAnswer() + "\"\n        }";
                    break;
                }
/*
                case "TrueFalse": {
                    writing += "            \"correctAnswer\": ";

                    if(((TrueFalse<?>) question.getStatement()).getCorrectAnswer().equalsIgnoreCase("true"))
                        writing += "\"True\"";
                    else
                        writing += "\"False\"\n";

                    writing += "        }";

                    break;
                }

                case "ShortAnswer": {
                    writing += "            \"correctAnswer\": \"" + question.getStatement().getCorrectAnswer() + "\"\n        }";
                    break;

                }
*/

                default: {
                    break;
                }
            }

            if(i != listQuestions.size() - 2)   // if there is at least one more question
                writing += ",";

            writing += "\n";
        }
        writing += "    ]\n}";

/*
        JSONArray questions = new JSONArray();
        questions.put("questions :");
        for(Question question : this.listQuestions)
        {
            JSONObject obj = new JSONObject();
            obj.put("type", question.getStatement().getType());
            obj.put("difficulty", question.getDifficulty());
            obj.put("text", question.getStatement().getText());
            switch (question.getStatement().getType())
            {
                case "MCQ": {
                    JSONArray answers = new JSONArray();
                    answers.put(( (MCQ<?>) question.getStatement()).getAnswers().get(0));
                    answers.put(( (MCQ<?>) question.getStatement()).getAnswers().get(1));
                    answers.put(( (MCQ<?>) question.getStatement()).getAnswers().get(2));
                    obj.put("answers", answers);
                    break;
                }

                default: {
                    obj.put("correctAnswer", question.getStatement().getCorrectAnswer());
                    break;
                }

            }
            questions.put(obj);
        }
*/
        try {
            //FileWriter file = new FileWriter("../resources/json/" + theme + ".json");
            PrintWriter file = new PrintWriter("../resources/json/test.json");
            file.print(writing);
                    /*
            FileWriter file = new FileWriter("../resources/json/" + "test" + ".json");
            file.write(writing);
            file.close();*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
