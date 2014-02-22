/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.frontend.pascal.parsers;

import java.util.EnumSet;
import java.util.HashSet;

import compiler.frontend.*;
import compiler.frontend.pascal.*;
import compiler.intermediate.*;

import static compiler.frontend.pascal.PascalTokenType.*;
import static compiler.frontend.pascal.PascalErrorCode.*;
import static compiler.intermediate.symtabimpl.SymTabKeyImpl.*;
import static compiler.intermediate.icodeimpl.ICodeNodeTypeImpl.*;
import static compiler.intermediate.icodeimpl.ICodeKeyImpl.*;

/**
 *
 * @author jamey
 */
public class CaseStatementParser extends StatementParser {

    public CaseStatementParser(PascalParserTD parent) {
        super(parent);
    }
    private static final EnumSet<PascalTokenType> CONSTANT_START_SET = EnumSet.of(IDENTIFIER, INTEGER, PLUS, MINUS, STRING);
    private static final EnumSet<PascalTokenType> OF_SET = CONSTANT_START_SET.clone();

    static {
        OF_SET.add(OF);
        OF_SET.addAll(StatementParser.STMT_FOLLOW_SET);
    }

    public ICodeNode parse(Token token) throws Exception {
        token = nextToken();

        ICodeNode selectNode = ICodeFactory.createICodeNode(SELECT);

        ExpressionParser expressionParser = new ExpressionParser(this);
        selectNode.addChild(expressionParser.parse(token));  //Parse the CASE [Expression] Of
        token = synchronize(OF_SET);
        if (token.getType() == OF) {
            token = nextToken(); // consume the OF
        } else {
            errorHandler.flag(token, MISSING_OF, this);
        }

        HashSet<Object> constantSet = new HashSet<Object>();
        while (!(token instanceof EofToken) && (token.getType() != END)) {
            selectNode.addChild(parseBranch(token, constantSet));

            token = currentToken();
            TokenType tokenType = token.getType();

            if (tokenType == SEMICOLON) {
                token = nextToken();
            } else if (CONSTANT_START_SET.contains(tokenType)) {
                errorHandler.flag(token, MISSING_SEMICOLON, this);
            }
        }
        if (token.getType() == END) {
            token = nextToken();
        } else {
            errorHandler.flag(token, MISSING_END, this);
        }
        return selectNode;
    }

    private ICodeNode parseBranch(Token token, HashSet<Object> constantSet) throws Exception {
        ICodeNode branchNode = ICodeFactory.createICodeNode(SELECT_BRANCH);
        ICodeNode constantsNode = ICodeFactory.createICodeNode(SELECT_CONSTANTS);
        branchNode.addChild(constantsNode);

        parseConstantList(token, constantsNode, constantSet);

        token = currentToken();
        if (token.getType() == COLON) {
            token = nextToken();
        } else {
            errorHandler.flag(token, MISSING_COLON, this);
        }
        StatementParser statementParser = new StatementParser(this);
        branchNode.addChild(statementParser.parse(token));

        return branchNode;
    }
    private static final EnumSet<PascalTokenType> COMMA_SET = CONSTANT_START_SET.clone();

    static {
        COMMA_SET.add(COMMA);
        COMMA_SET.add(COLON);
        COMMA_SET.addAll(StatementParser.STMT_START_SET);
        COMMA_SET.addAll(StatementParser.STMT_FOLLOW_SET);
    }

    private void parseConstantList(Token token, ICodeNode constantsNode, HashSet<Object> constantSet) throws Exception {
        while (CONSTANT_START_SET.contains(token.getType())) {
            constantsNode.addChild(parseConstant(token, constantSet));
            token = synchronize(COMMA_SET);
            if (token.getType() == COMMA) {
                token = nextToken();  // consume the ,
            } // If at the start of the next constant, then missing a comma.
            else if (CONSTANT_START_SET.contains(token.getType())) {
                errorHandler.flag(token, MISSING_COMMA, this);
            }
        }
    }

    private ICodeNode parseConstant(Token token, HashSet<Object> constantSet) throws Exception {
        TokenType sign = null;
        ICodeNode constantNode = null;

        token = synchronize(CONSTANT_START_SET);
        TokenType tokenType = token.getType();

        if ((tokenType == PLUS) || (tokenType == MINUS)) {
            sign = tokenType;
            token = nextToken();
        }
        switch ((PascalTokenType) token.getType()) {
            case IDENTIFIER: {
                constantNode = parseIdentifierConstant(token, sign);
                break;
            }
            case INTEGER: {
                constantNode = parseIntegerConstant(token.getText(), sign);
                break;
            }
            case STRING: {
                constantNode = parseCharacterConstant(token, (String) token.getValue(), sign);
                break;
            }
            default: {
                errorHandler.flag(token, INVALID_CONSTANT, this);
                break;
            }
        }
        if (constantNode != null) {
            Object value = constantNode.getAttribute(VALUE);
            if (constantSet.contains(value)) {
                errorHandler.flag(token, CASE_CONSTANT_REUSED, this);
            } else {
                constantSet.add(value);
            }
        }
        nextToken();
        return constantNode;
    }

    private ICodeNode parseIdentifierConstant(Token token, TokenType sign) throws Exception {
        errorHandler.flag(token, INVALID_CONSTANT, this);
        return null;
    }

    private ICodeNode parseIntegerConstant(String value, TokenType sign) {
        ICodeNode constantNode = ICodeFactory.createICodeNode(INTEGER_CONSTANT);
        int intValue = Integer.parseInt(value);
        if (sign == MINUS) {
            intValue = -intValue;
        }
        constantNode.setAttribute(VALUE, intValue);
        return constantNode;
    }

    private ICodeNode parseCharacterConstant(Token token, String value, TokenType sign) {
        ICodeNode constantNode = null;
        if (sign != null) {
            errorHandler.flag(token, INVALID_CONSTANT, this);
        } else {
            if (value.length() == 1) {
                constantNode = ICodeFactory.createICodeNode(STRING_CONSTANT);
                constantNode.setAttribute(VALUE, value);
            } else {
                errorHandler.flag(token, INVALID_CONSTANT, this);
            }
        }
        return constantNode;
    }
}
