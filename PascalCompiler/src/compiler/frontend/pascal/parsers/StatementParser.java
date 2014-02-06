/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.frontend.pascal.parsers;

import compiler.frontend.*;
import compiler.frontend.pascal.*;
import compiler.intermediate.*;

import static compiler.frontend.pascal.PascalTokenType.*;
import static compiler.frontend.pascal.PascalErrorCode.*;
import static compiler.intermediate.icodeimpl.ICodeNodeTypeImpl.*;
import static compiler.intermediate.icodeimpl.ICodeKeyImpl.*;

/**
 *
 * @author jamey
 */
public class StatementParser extends PascalParserTD {
    public StatementParser(PascalParserTD parent)
    {
        super(parent.getScanner());
    }
    
    public ICodeNode parse(Token token) throws Exception {
        ICodeNode statementNode = null;
        
        switch((PascalTokenType) token.getType()) {
            case BEGIN:{
                CompoundStatementParser compoundParser = new CompoundStatementParser(this);
                statementNode= compoundParser.parse(token);
                break;
            }
            case IDENTIFIER: {
                AssignmentStatementParser assignmentParser = new AssignmentStatementParser(this);
                statementNode = assignmentParser.parse(token);
                break;
            }
            default: {
                statementNode = ICodeFactory.createICodeNode(NO_OP);
                break;
            }
        }
        
        setLineNumber(statementNode, token);
        return statementNode;
    }
    
    protected void setLineNumber(ICodeNode node, Token token)
    {
        if (node != null) {
            node.setAttribute(LINE, token.getLineNumber());
        }
    }
    protected void parseList(Token token, ICodeNode parentNode, PascalTokenType terminator, PascalErrorCode errorCode) throws Exception {
        while (!(token instanceof EofToken) && (token.getType() != terminator)) {
            ICodeNode statementNode = parse(token);
            parentNode.addChild(statementNode);

            token = currentToken();
            TokenType tokenType = token.getType();

            if (tokenType == SEMICOLON) {
                token = nextToken();  // consume the ;
            }
            else if (tokenType == IDENTIFIER) {
                errorHandler.flag(token, MISSING_SEMICOLON, this);
            }

            else if (tokenType != terminator) {
                errorHandler.flag(token, UNEXPECTED_TOKEN, this);
                token = nextToken();  // consume the unexpected token
            }
        }

        if (token.getType() == terminator) {
            token = nextToken();  // consume the terminator token
        }
        else {
            errorHandler.flag(token, errorCode, this);
        }
    }
    
}
