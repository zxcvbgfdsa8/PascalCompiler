/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.frontend.pascal.parsers;

import java.util.EnumSet;

import compiler.frontend.*;
import compiler.frontend.pascal.*;
import compiler.intermediate.*;
import compiler.intermediate.icodeimpl.*;

import static compiler.frontend.pascal.PascalTokenType.*;
import static compiler.frontend.pascal.PascalErrorCode.*;
import static compiler.intermediate.icodeimpl.ICodeNodeTypeImpl.*;
import static compiler.intermediate.icodeimpl.ICodeKeyImpl.*;



/**
 *
 * @author jamey
 */
public class IfStatementParser extends StatementParser {
    public IfStatementParser(PascalParserTD parent) {
        super(parent);
    }
    private static final EnumSet<PascalTokenType> THEN_SET = StatementParser.STMT_START_SET.clone();
    static {
        THEN_SET.add(THEN);
        THEN_SET.addAll(StatementParser.STMT_FOLLOW_SET);
    }
    public ICodeNode parse(Token token) throws Exception {
        token = nextToken();
        
        ICodeNode ifNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.IF);
        
        ExpressionParser expressionParser = new ExpressionParser(this);
        ifNode.addChild(expressionParser.parse(token));
        
        token = synchronize(THEN_SET);
        if (token.getType() == THEN) {
            token = nextToken();
        }
        else {
            errorHandler.flag(token, MISSING_THEN, this);
        }
        
        StatementParser statementParser = new StatementParser(this);
        ifNode.addChild(statementParser.parse(token));
        token = currentToken();
        
        if (token.getType() == ELSE) {
            token = nextToken();
            ifNode.addChild(statementParser.parse(token));
        }

        return ifNode;        
    }
}
