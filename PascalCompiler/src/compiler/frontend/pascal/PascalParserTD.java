/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.frontend.pascal;
import compiler.frontend.*;
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
            // Loop over each token until the end of file.
            while (!((token = nextToken()) instanceof EofToken)) {
                TokenType tokenType = token.getType();

                // Cross reference only the identifiers.
                if (tokenType == IDENTIFIER) {
                    String name = token.getText().toLowerCase();

                    // If it's not already in the symbol table,
                    // create and enter a new entry for the identifier.
                    SymTabEntry entry = symTabStack.lookup(name);
                    if (entry == null) {
                        entry = symTabStack.enterLocal(name);
                    }

                    // Append the current line number to the entry.
                    entry.appendLineNumber(token.getLineNumber());
                }

                else if (tokenType == ERROR) {
                    errorHandler.flag(token, (PascalErrorCode) token.getValue(),
                                      this);
                }
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
}
