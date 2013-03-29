/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascalcompiler.frontend;

/**
 *
 * @author jamey
 */
public class Scanner {
    protected Source source;
    private Token currentToken;
    
    public Scanner(Source source) {
        this.source = source;        
    }
    public Token currentToken() {
        return currentToken;
    }
    public Token nextToken() throws Exception {
        currentToken = extractToken();
        return currentToken;
    }
    public abstract Token extractToken() throws Exception;
    public char currentChar() throws Exception {
        return source.currentChar();
    }
    public char nextChar() throws Exception {
        return source.nextChar();
    }
}
