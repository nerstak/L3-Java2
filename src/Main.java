import oo.Game.Difficulty;
import oo.Questions.MCQ;
import oo.Questions.Question;
import oo.Questions.Themes;


public class Main {
    public static void main(String[] args) {
        MCQ<String> m = new MCQ<>("lol", "mo", "kp", "jo", "mo");
        m.display();
        System.out.println(m.checkAnswer("mo"));
        Question<?> q = new Question(m, "mdr", Difficulty.easy);
        q.display();

        Themes t = new Themes();
        t.display();
    }
}
