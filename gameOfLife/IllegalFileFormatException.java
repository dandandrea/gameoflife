package gameOfLife;

import java.lang.Exception;

public class IllegalFileFormatException extends GameOfLifeException {
    public IllegalFileFormatException(String message) {
        super(message);
    }
}
