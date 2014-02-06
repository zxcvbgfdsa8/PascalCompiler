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

/**
 *
 * @author jamey
 */
public class CompoundStatementParser extends StatementParser {
    public CompoundStatementParser(PascalParserTD parent)
    {
        super(parent);
    }
    
    public ICodeNode parse(Token token) throws Exception
    {
        token = nextToken();

        ICodeNode compoundNode = ICodeFactory.createICodeNode(COMPOUND);

        StatementParser statementParser = new StatementParser(this);
        statementParser.parseList(token, compoundNode, END, MISSING_END);

        return compoundNode;
    }
}
