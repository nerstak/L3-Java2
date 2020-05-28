import oo.Player;

public class Main {
    public static void main(String[] args) {
        Player p1 = new Player();
        Player p2 = new Player();

        System.out.println(p1.getStatus() + " " + p1.getNumber());
        System.out.println(p2.getStatus() + " " + p2.getNumber());
    }
}
