/*
 * Licensed under the MIT license.
 * Copyright (c) 2007 David Holroyd
 */
package uk.co.badgersinfoil.e4x.antlr;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.BaseTreeAdaptor;
import org.antlr.runtime.tree.Tree;


public class LinkedListTreeAdaptor extends BaseTreeAdaptor {
    private Factory factory = new Factory() {
        private BasicListUpdateDelegate delegate = new BasicListUpdateDelegate();
        public TreeTokenListUpdateDelegate create(LinkedListTree payload) {
            return delegate;
        }
    };

    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    public Token createToken(int tokenType, String text) {
        return new CommonToken(tokenType, text);
    }

    public Token createToken(Token fromToken) {
        return new CommonToken(fromToken);
    }

    public Object create(Token payload) {
        LinkedListTree result = new LinkedListTree(payload);
        result.setTokenListUpdater(factory.create(result));
        if (payload instanceof LinkedListToken) {
            result.setStartToken((LinkedListToken)payload);
            result.setStopToken((LinkedListToken)payload);
        }
        return result;
    }

    public Object dupNode(Object treeNode) {
        return ((Tree)treeNode).dupNode();
    }

    /**
     * Only works with LinkedListTree nodes
     */
    public void setTokenBoundaries(Object t, Token startToken, Token stopToken) {
        if ( t==null ) {
            return;
        }
        LinkedListTree tree = (LinkedListTree)t;
        tree.setStartToken((LinkedListToken)startToken);
        tree.setStopToken((LinkedListToken)stopToken);
    }

    public int getTokenStartIndex(Object arg0) {
        // TODO: what's appropriate here?
        return -1;
    }

    public int getTokenStopIndex(Object arg0) {
        // TODO: what's appropriate here?
        return -1;
    }

    public String getText(Object t) {
        return ((Tree)t).getText();
    }

    public int getType(Object t) {
        return ((Tree)t).getType();
    }

    public interface Factory {
        TreeTokenListUpdateDelegate create(LinkedListTree payload);
    }

    public Token getToken(Object t) {
        return ((LinkedListTree)t).getToken();
    }

    public Object getParent(Object o) {
        return ((LinkedListTree)o).getParent();
    }

    public void setParent(Object t, Object parent) {
        ((LinkedListTree)t).setParent((LinkedListTree)parent);
    }

    public int getChildIndex(Object t) {
        return ((LinkedListTree)t).getChildIndex((LinkedListTree)t);
    }

    public void setChildIndex(Object t, int index) {
        ((LinkedListTree)t).setChildIndex(index);
    }

    public void replaceChildren(Object parent, int startChildIndex, int stopChildIndex, Object t) {
        if (parent != null ) {
            ((LinkedListTree)parent).replaceChildren(startChildIndex, stopChildIndex, t);
        }
    }
}
