import java.io.Serializable;

public class GameMessage implements Serializable {
    private String message;
    private char letter;
    private boolean isCorrect;

    public GameMessage(String message, char letter, boolean isCorrect) {
        this.message = message;
        this.letter = letter;
        this.isCorrect = isCorrect;
    }

    public String getMessage() {
        return message;
    }

    public char getLetter() {
        return letter;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
