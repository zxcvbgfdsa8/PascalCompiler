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
public class RepeatStatementParser extends StatementParser {
    public RepeatStatementParser(PascalParserTD parent) {
        super(parent);
    }
    public ICodeNode parse(Token token) throws Exception {
        token = nextToken();
        ICodeNode loopNode = ICodeFactory.createICodeNode(LOOP);
        ICodeNode testNode = ICodeFactory.createICodeNode(TEST);
        
        StatementParser statementParser = new StatementParser(this);
        statementParser.parseList(token, loopNode, UNTIL, MISSING_UNTIL);
        token = currentToken();
        
        ExpressionParser expressionParser = new ExpressionParser(this);
        testNode.addChild(expressionParser.parse(token));
        loopNode.addChild(testNode);
        
        return loopNode;                        
    }
}
