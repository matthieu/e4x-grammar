package uk.co.badgersinfoil.e4x.antlr;

import org.antlr.runtime.tree.CommonErrorNode;
import org.antlr.runtime.tree.Tree;
import org.antlr.runtime.tree.BaseTree;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.RecognitionException;

import java.util.List;

public class LinkedListErrorNode extends LinkedListTree {

    private CommonErrorNode _errorNode;

    public LinkedListErrorNode(TokenStream tokenStream, Token start, Token end, RecognitionException e) {
        super();
        _errorNode = new CommonErrorNode(tokenStream, start, end, e);
    }

    public boolean isNil() {
        return _errorNode.isNil();
    }

    public int getType() {
        return _errorNode.getType();
    }

    public String getText() {
        return _errorNode.getText();
    }

    public String toString() {
        return _errorNode.toString();
    }

}
