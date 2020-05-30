import oo.Themes;

public class Main {
    public static void main(String[] args) {
        Themes t = new Themes();
        t.display();
        t.alterTheme(3, "ptdr");
        t.display();
    }
}
