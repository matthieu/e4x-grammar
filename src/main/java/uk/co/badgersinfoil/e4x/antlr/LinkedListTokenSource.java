/*
 * Licensed under the MIT license.
 * Copyright (c) 2007 David Holroyd
 */
package uk.co.badgersinfoil.e4x.antlr;

import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;


public class LinkedListTokenSource implements TokenSource {
	private TokenSource delegate;
	private LinkedListToken last = null;

	public LinkedListTokenSource(TokenSource delegate) {
		this.delegate = delegate;
	}

	public Token nextToken() {
		LinkedListToken curr = createToken(delegate.nextToken());
		if (last != null) {
			last.setNext(curr);
		}
		curr.setPrev(last);
		last = curr;
		return curr;
	}

	private LinkedListToken createToken(Token tok) {
		LinkedListToken result = new LinkedListToken(tok.getType(), tok.getText());
		result.setLine(tok.getLine());
		result.setCharPositionInLine(tok.getCharPositionInLine());
		result.setChannel(tok.getChannel());
		result.setTokenIndex(tok.getTokenIndex());
		return result;
	}

	/**
	 * Redefines the TokenSource to which this object delagates the task of
	 * token creation.  This can be used to switch Lexers when an island
	 * grammar is required, for instance.
	 */
	public void setDelegate(TokenSource delegate) {
		this.delegate = delegate;
	}

	/**
	 * Overrides the 'last' token which this object is remembering in order
	 * to build next/previous links.
	 */
	public void setLast(LinkedListToken tok) {
	}
}