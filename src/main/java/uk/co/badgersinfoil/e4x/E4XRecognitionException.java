package uk.co.badgersinfoil.e4x;

import org.antlr.runtime.RecognitionException;

public class E4XRecognitionException extends RecognitionException {
    public String message;

    public E4XRecognitionException(int line, int column, String message) {
        this.line = line;
        this.charPositionInLine = column;
        this.message = message;
    }
}
