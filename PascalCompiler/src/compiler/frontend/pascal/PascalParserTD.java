/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.frontend.pascal;
import compiler.frontend.*;
import compiler.message.Message;

import static compiler.message.MessageType.PARSER_SUMMARY;
/**
 *
 * @author jamey
 */
public class PascalParserTD extends Parser{
    public PascalParserTD(Scanner scanner) {
        super(scanner);
    }
    @Override
    public void parse() throws Exception {
        Token token;
        
        long startTime = System.currentTimeMillis();
        while (!((token = nextToken()) instanceof EofToken)) {}
        float elapsedTime = (System.currentTimeMillis() - startTime)/1000f;
        sendMessage(new Message(PARSER_SUMMARY, new Number[] {token.getLineNumber(),
            getErrorCount(),
            elapsedTime}));
    }
    @Override
    public int getErrorCount() {
        return 0;
    }
}
