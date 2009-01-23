package uk.co.badgersinfoil.e4x;

import uk.co.badgersinfoil.e4x.antlr.LinkedListTree;
import uk.co.badgersinfoil.e4x.antlr.LinkedListTokenStream;
import org.antlr.runtime.TokenSource;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.RecognitionException;

/**
 * Interface to be implemented by an expression parser the E4X grammar will delegate to when
 * it encounters embedded { ... } expressions.
 */
public interface E4XExpressionParser {

    LinkedListTree parseSubExpression(TokenSource lexer, CharStream cs, LinkedListTokenStream stream)
            throws RecognitionException;
}
