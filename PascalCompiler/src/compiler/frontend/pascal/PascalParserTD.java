/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.frontend.pascal;

import java.util.EnumSet;

import compiler.frontend.*;
import compiler.frontend.pascal.parsers.*;
import compiler.intermediate.*;
import compiler.message.Message;

import static compiler.frontend.pascal.PascalTokenType.*;
import static compiler.frontend.pascal.PascalErrorCode.*;
import static compiler.message.MessageType.*;
import static compiler.intermediate.symtabimpl.SymTabKeyImpl.*;
/**
 *
 * @author jamey
 */
public class PascalParserTD extends Parser {
    protected static PascalErrorHandler errorHandler = new PascalErrorHandler();
    
    public PascalParserTD(Scanner scanner) {
        super(scanner);
    }
    @Override
    public void parse() throws Exception {
        
        long startTime = System.currentTimeMillis();
        iCode = ICodeFactory.createICode();
        
        try {
            Token token = nextToken();
            ICodeNode rootNode = null;
           
            TokenType tokenType = token.getType();
            
            //Look for the start of the program
            if(token.getType() == BEGIN) {
                StatementParser statementParser = new StatementParser(this);
                rootNode = statementParser.parse(token);
                token = currentToken();
            } else {
                errorHandler.flag(token, UNEXPECTED_TOKEN, this);
            }
            //Look for the end of the program
            if (token.getType() != DOT) {
                errorHandler.flag(token, MISSING_PERIOD, this);
            }
            token = currentToken();
            if (rootNode != null) {
                iCode.setRoot(rootNode);
            }
                // Send the parser summary message.
            float elapsedTime = (System.currentTimeMillis() - startTime)/1000f;
            sendMessage(new Message(PARSER_SUMMARY,
                                    new Number[] {token.getLineNumber(),
                                                  getErrorCount(),
                                                  elapsedTime}));
        }
        catch (java.io.IOException ex) {
            errorHandler.abortTranslation(IO_ERROR, this);
        }
    }
    @Override
    public int getErrorCount() {
        return errorHandler.getErrorCount();
    }
    public Token synchronize(EnumSet syncSet) throws Exception {
        Token token = currentToken();
        
        if (!syncSet.contains(token.getType())) {
            errorHandler.flag(token, UNEXPECTED_TOKEN, this);
            do {
                token = nextToken();
            } while (!(token instanceof EofToken) && !syncSet.contains(token.getType()));            
        }
        return token;
    }
}
