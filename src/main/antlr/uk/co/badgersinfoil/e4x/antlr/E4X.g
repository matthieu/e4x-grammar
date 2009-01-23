grammar E4X;

options {
output=AST;
}

tokens {
	XML_EMPTY_ELEMENT;
	XML_ELEMENT;
	XML_ATTRIBUTE;
	XML_LIST;
	
	VIRTUAL_PLACEHOLDER;
}

@parser::header {
package uk.co.badgersinfoil.e4x.antlr;
import uk.co.badgersinfoil.e4x.E4XExpressionParser;
}
@lexer::header {
package uk.co.badgersinfoil.e4x.antlr;
}

// disable standard error handling; be strict
@rulecatch { }

@parser::members {
    public static final int CHANNEL_PLACEHOLDER = 999;
    
    // disable standard error handling; be strict    
    protected void mismatch(IntStream input, int ttype, BitSet follow)
        throws RecognitionException {
        throw new MismatchedTokenException(ttype, input);
    }

    private E4XLexer lexer;
    private CharStream cs;

    public void setInput(E4XLexer lexer, CharStream cs) {
        this.lexer = lexer;
        this.cs = cs;    
    }

    /** Delegates back to the main grammar (or any other) for expressions. */
    private E4XExpressionParser expressionParser;

    public void setExpressionParser(E4XExpressionParser p) {
        expressionParser = p;
    }

    private LinkedListTree parseSubExpression() throws RecognitionException {
        return expressionParser.parseSubExpression(lexer, cs, (LinkedListTokenStream)input);
    }

    /**
     * Returns the input left unconsumed after the last parse operation.
     * Because of lookahead in the parser, there is no guarantee that the
     * lexer has not consumed input ahead of the current parse-point for
     * any abritrary rule. This method is only intended to grab the
     * remaining input after recognising 'xmlPrimary'.
     */
    public String getInputTail() {
        return cs.substring(cs.index()-1, cs.size()-1);
    }
}

// see http://www.ecma-international.org/publications/standards/Ecma-357.htm


xmlMarkup
	:	XML_COMMENT
	|	XML_CDATA
	|	XML_PI
	;


xmlPrimary
	:	xmlInitialiser
	|	xmlListInitialiser
	;

xmlInitialiser
	:	xmlMarkup
	|	xmlElement
	;

xmlElement
	:	(XML_LCHEVRON xmlTagContent XML_WS? -> xmlTagContent)
		(
			'/>'
			-> ^(XML_EMPTY_ELEMENT $xmlElement)

		|	 '>' xmlElementContent* '</' xmlTagName XML_WS? '>'
			-> ^(XML_ELEMENT $xmlElement xmlElementContent*)
		)
	;

xmlTagContent
	:	xmlTagName xmlAttributes?
	;

xmlEmbeddedExpression
@init { LinkedListTree expr = null; }
	    // We have to have the LT in the outer grammar for lookahead
		// in here to be able to predict that the expression rule
		// should be used.
	:	'{' { expr=parseSubExpression(); } -> { expr };

xmlText
	:	XML_TEXT | XML_NAME | XML_WS
	;

xmlTagName
	:	xmlEmbeddedExpression
	|	XML_NAME
	;

xmlAttributes
	:	(
			XML_WS xmlEmbeddedExpression
		|	xmlAttribute
		)+
	;

xmlAttribute
	:	XML_WS XML_NAME XML_WS? '=' XML_WS? xmlAttributeValue
		-> ^(XML_ATTRIBUTE XML_NAME xmlAttributeValue)
	;

xmlAttributeValue
	:	xmlEmbeddedExpression
	|	XML_ATTRIBUTE_VALUE
	;

xmlElementContent
	:	xmlEmbeddedExpression
	|	xmlMarkup
	|	xmlText
	|	xmlElement
	;

xmlListInitialiser
	:	'<>' xmlElementContent* '</>'
		-> ^(XML_LIST xmlElementContent*)
	;

XML_LCHEVRON		:	'<';

XML_WS			:	(' ' | '\t' | '\n' | '\r')+;

XML_NAME		:	XML_NAME_START XML_NAME_PART*;

XML_ATTRIBUTE_VALUE
	:	'\'' ( options {greedy=false;} : . )* '\''
	|	'"' ( options {greedy=false;} : . )* '"'
	;

XML_PI			:	'<?' ( options {greedy=false;} : . )* '?>';

// TODO: exclude the sequence '--' from being allowed,
XML_COMMENT		:	'<!--' ( options {greedy=false;} : . )* '-->';

XML_CDATA		:	'<![CDATA[' ( options {greedy=false;} : . )* ']]>';

fragment XML_NAME_START	:	UNICODE_LETTER | '_' | ':';
fragment XML_NAME_PART	:	UNICODE_LETTER | UNICODE_DIGIT | '.' | '-' | '_' | ':';

// TODO: and the rest of unicode?
fragment UNICODE_LETTER	:	'a'..'z' | 'A'..'Z';
fragment UNICODE_DIGIT	:	'0'..'9';

XML_TEXT
	:	(~(XML_LCHEVRON | '{'))
	;
