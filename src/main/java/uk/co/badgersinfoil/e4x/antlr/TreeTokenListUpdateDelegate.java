/*
 * Licensed under the MIT license.
 * Copyright (c) 2007 David Holroyd
 */
package uk.co.badgersinfoil.e4x.antlr;

public interface TreeTokenListUpdateDelegate {
	public void addedChild(LinkedListTree parent, LinkedListTree child);
	public void addedChild(LinkedListTree parent, int index, LinkedListTree child);
	public void appendToken(LinkedListTree parent, LinkedListToken append);
	public void addToken(LinkedListTree parent, int index, LinkedListToken append);
	public void deletedChild(LinkedListTree parent, int index, LinkedListTree child);
	public void replacedChild(LinkedListTree tree, int index, LinkedListTree child, LinkedListTree oldChild);
}
