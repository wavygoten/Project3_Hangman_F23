import java.io.Serializable;
import java.util.ArrayList;

public class GameMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Integer> positions;
    private boolean isLetterInWord;
    private int guessCount, letterCount;

    public GameMessage(ArrayList<Integer> p, int gc, boolean inWord, int lc) {
        positions = p;
        guessCount = gc;
        isLetterInWord = inWord;

        letterCount = lc;
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

    public int getLetterCount() {
        return letterCount;
    }

    public void setPositions(ArrayList<Integer> positions) {
        this.positions = positions;
    }

    public void setGuessCount(int guessCount) {
        this.guessCount = guessCount;
    }

    public void setLetterInWord(boolean isLetterInWord) {
        this.isLetterInWord = isLetterInWord;
    }

    public void setLetterCount(int letterCount) {
        this.letterCount = letterCount;
    }
}
