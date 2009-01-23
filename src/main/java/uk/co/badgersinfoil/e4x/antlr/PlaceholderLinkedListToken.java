/*
 * Licensed under the MIT license.
 * Copyright (c) 2007 David Holroyd
 */
package uk.co.badgersinfoil.e4x.antlr;

public class PlaceholderLinkedListToken extends LinkedListToken {
	private LinkedListTree held;

	public PlaceholderLinkedListToken(LinkedListTree held) {
		super(E4XParser.VIRTUAL_PLACEHOLDER, "");
		setChannel(E4XParser.CHANNEL_PLACEHOLDER);
		this.held = held;
		held.setStartToken(this);
		held.setStopToken(this);
	}

	public LinkedListTree getHeld() {
		return held;
	}
}
