/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.frontend.pascal;
import compiler.frontend.*;
import compiler.message.Message;

import static compiler.frontend.pascal.PascalTokenType.*;
import static compiler.frontend.pascal.PascalErrorCode.*;
import static compiler.message.MessageType.*;
/**
 *
 * @author jamey
 */
public class PascalParserTD extends Parser{
    protected static PascalErrorHandler errorHandler = new PascalErrorHandler();
    
    public PascalParserTD(Scanner scanner) {
        super(scanner);
    }
    @Override
    public void parse() throws Exception {
        Token token;
        
        long startTime = System.currentTimeMillis();
        try {
            while (!((token = nextToken()) instanceof EofToken)) {
                TokenType tokenType = token.getType();
                if (tokenType != ERROR) {
                    sendMessage(new Message(TOKEN, 
                                            new Object[] {token.getLineNumber(),
                                                          token.getPosition(),
                                                          tokenType,
                                                          token.getText(),
                                                          token.getValue()}));
                } else {
                    errorHandler.flag(token, (PascalErrorCode) token.getValue(), this);
                }
            }
            float elapsedTime = (System.currentTimeMillis() - startTime)/1000f;
            sendMessage(new Message(PARSER_SUMMARY, new Number[] {token.getLineNumber(),
                getErrorCount(),
                elapsedTime}));
        } catch (java.io.IOException ex) {
            errorHandler.abortTranslation(IO_ERROR, this);
        }        
    }
    @Override
    public int getErrorCount() {
        return errorHandler.getErrorCount();
    }
}
