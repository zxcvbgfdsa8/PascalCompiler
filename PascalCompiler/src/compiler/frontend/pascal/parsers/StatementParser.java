/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.frontend.pascal.parsers;

import java.util.EnumSet;

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
    protected static final EnumSet<PascalTokenType> STMT_START_SET = EnumSet.of(BEGIN, CASE, FOR, PascalTokenType.IF, REPEAT, WHILE, IDENTIFIER, SEMICOLON);
    protected static final EnumSet<PascalTokenType> STMT_FOLLOW_SET = EnumSet.of(SEMICOLON, END, ELSE, UNTIL, DOT);
    
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
            case REPEAT: {
                RepeatStatementParser repeatParser = new RepeatStatementParser(this);
                statementNode = repeatParser.parse(token);
                break;
            }

            case WHILE: {
                WhileStatementParser whileParser = new WhileStatementParser(this);
                statementNode = whileParser.parse(token);
                break;
            }                
            /*case FOR: {
                ForStatementParser forParser = new ForStatementParser(this);
                statementNode = forParser.parse(token);
                break;
            }
            case IF: {
                IfStatementParser ifParser = new IfStatementParser(this);
                statementNode = ifParser.parse(token);
                break;
            }
            case CASE: {
                CaseStatementParser caseParser = new CaseStatementParser(this);
                statementNode = caseParser.parse(token);
                break;
            }*/
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
        EnumSet<PascalTokenType> terminatorSet = STMT_START_SET.clone();
        terminatorSet.add(terminator);
        
        while (!(token instanceof EofToken) && (token.getType() != terminator)) {
            ICodeNode statementNode = parse(token);
            parentNode.addChild(statementNode);

            token = currentToken();
            TokenType tokenType = token.getType();

            if (tokenType == SEMICOLON) {
                token = nextToken();  // consume the ;
            }
            else if (STMT_START_SET.contains(tokenType)) {
                errorHandler.flag(token, MISSING_SEMICOLON, this);
            }
            token = synchronize(terminatorSet);
        }

        if (token.getType() == terminator) {
            token = nextToken();  // consume the terminator token
        }
        else {
            errorHandler.flag(token, errorCode, this);
        }
    }
    
}
