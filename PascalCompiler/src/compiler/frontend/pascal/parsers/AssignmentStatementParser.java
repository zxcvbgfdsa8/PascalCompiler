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
public class AssignmentStatementParser extends StatementParser{
    public AssignmentStatementParser(PascalParserTD parent)
    {
        super(parent);
    }
    
    public ICodeNode parse(Token token) throws Exception
    {
        ICodeNode assignNode = ICodeFactory.createICodeNode(ASSIGN);

        String targetName = token.getText().toLowerCase();
        SymTabEntry targetId = symTabStack.lookup(targetName);
        if (targetId == null) {
            targetId = symTabStack.enterLocal(targetName);
        }
        targetId.appendLineNumber(token.getLineNumber());

        token = nextToken();  // consume the identifier token

        ICodeNode variableNode = ICodeFactory.createICodeNode(VARIABLE);
        variableNode.setAttribute(ID, targetId);

        assignNode.addChild(variableNode);

        if (token.getType() == COLON_EQUALS) {
            token = nextToken();  // consume the :=
        }
        else {
            errorHandler.flag(token, MISSING_COLON_EQUALS, this);
        }

        ExpressionParser expressionParser = new ExpressionParser(this);
        assignNode.addChild(expressionParser.parse(token));

        return assignNode;
    }
}
