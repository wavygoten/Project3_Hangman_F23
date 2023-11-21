import java.io.Serializable;
import java.util.ArrayList;

public class GameMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Integer> positions;
    private boolean isLetterInWord;
    private int guessCount, letterCount, wordGuessCount;

    public GameMessage(ArrayList<Integer> p, int gc, boolean inWord, int lc, int wgc) {
        positions = p;
        guessCount = gc;
        isLetterInWord = inWord;

        letterCount = lc;
        wordGuessCount = wgc;
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

    public int getWordGuessCount() {
        return wordGuessCount;
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

    public void setWordGuessCount(int wordGuessCount) {
        this.wordGuessCount = wordGuessCount;
    }
}
