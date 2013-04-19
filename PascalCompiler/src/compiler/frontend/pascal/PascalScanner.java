/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.frontend.pascal;

import compiler.frontend.*;

import static compiler.frontend.Source.EOF;

/**
 *
 * @author jamey
 */
public class PascalScanner extends Scanner {
    public PascalScanner(Source source) {
        super(source);
    }    
    @Override
    public Token extractToken() throws Exception {
        Token token;
        char currentChar = currentChar();
        
        if (currentChar == EOF) {
            token = new EofToken(source);
        } else {
            token = new Token(source);
        }
        return token;
    }
    
}
