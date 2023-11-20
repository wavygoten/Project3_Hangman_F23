import java.io.Serializable;
import java.util.ArrayList;

public class GameMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Integer> positions;
    private boolean isLetterInWord;
    private int guessCount;

    public GameMessage(ArrayList<Integer> p, int gc, boolean inWord) {
        positions = p;
        guessCount = gc;
        isLetterInWord = inWord;
    }

    public ArrayList<Integer> getPositions() {
        return positions;
    }

    public int getGuessCount() {
        return guessCount;
    }

    public boolean isInWord() {
        return isLetterInWord;
    }
}
