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
import static compiler.intermediate.icodeimpl.ICodeNodeTypeImpl.*;

/**
 *
 * @author jamey
 */
public class WhileStatementParser extends StatementParser {
    public WhileStatementParser(PascalParserTD parent) {
        super(parent);
    }
    
    private static final EnumSet<PascalTokenType> DO_SET = StatementParser.STMT_START_SET.clone();
    static {
        DO_SET.add(DO);
        DO_SET.addAll(StatementParser.STMT_FOLLOW_SET);
    }
    
    public ICodeNode parse(Token token) throws Exception {
        token = nextToken();
        ICodeNode loopNode = ICodeFactory.createICodeNode(LOOP);
        ICodeNode breakNode = ICodeFactory.createICodeNode(TEST);
        ICodeNode notNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.NOT);
        
        loopNode.addChild(breakNode);
        breakNode.addChild(notNode);
        
        ExpressionParser expressionParser = new ExpressionParser(this);
        notNode.addChild(expressionParser.parse(token));
        
        token = synchronize(DO_SET);
        if (token.getType() == DO) {
            token = nextToken();
        }
        else {
            errorHandler.flag(token, MISSING_DO, this);
        }
        
        StatementParser statementParser = new StatementParser(this);
        loopNode.addChild(statementParser.parse(token));
         
        return loopNode;                        
    }
}
