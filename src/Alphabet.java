import java.util.ArrayList;

public class Alphabet {
    private static final ArrayList<Character> alphabet = new ArrayList<Character>();

    private Alphabet() {}

    private static void initializationAlphabet() {
        addStartEnd(33, 126);
        addStartEnd(1040, 1103);
    }

    private static void addStartEnd(int start, int end) {
        char ch = (char) start;
        while (ch <= end) {
            alphabet.add(ch);
            ch++;
        }
    }

    public static ArrayList<Character> getAlphabet() {
        initializationAlphabet();
        return alphabet;
    }


}
