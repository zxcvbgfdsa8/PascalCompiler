/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.frontend.pascal.parsers;

import java.util.EnumSet;
import java.util.HashMap;

import compiler.frontend.*;
import compiler.frontend.pascal.*;
import compiler.intermediate.*;
import compiler.intermediate.icodeimpl.*;

import static compiler.frontend.pascal.PascalTokenType.*;
import static compiler.frontend.pascal.PascalTokenType.NOT;
import static compiler.frontend.pascal.PascalErrorCode.*;
import static compiler.intermediate.icodeimpl.ICodeNodeTypeImpl.*;
import static compiler.intermediate.icodeimpl.ICodeKeyImpl.*;


/**
 *
 * @author jamey
 */
public class ExpressionParser extends StatementParser {
    public ExpressionParser(PascalParserTD parent)
    {
        super(parent);
    }
    static final EnumSet<PascalTokenType> EXPR_START_SET = EnumSet.of(PLUS, MINUS, IDENTIFIER, INTEGER, REAL, STRING, PascalTokenType.NOT, LEFT_PAREN);
    public ICodeNode parse(Token token) throws Exception
    {
        return parseExpression(token);
    }
    private static final EnumSet<PascalTokenType> REL_OPS = EnumSet.of(EQUALS, NOT_EQUALS, LESS_THAN, LESS_EQUALS, GREATER_THAN, GREATER_EQUALS);
    private static final HashMap<PascalTokenType, ICodeNodeType> REL_OPS_MAP = new HashMap<PascalTokenType, ICodeNodeType>();
    static {
        REL_OPS_MAP.put(EQUALS, EQ);
        REL_OPS_MAP.put(NOT_EQUALS, NE);
        REL_OPS_MAP.put(LESS_THAN, LT);
        REL_OPS_MAP.put(LESS_EQUALS, LE);
        REL_OPS_MAP.put(GREATER_THAN, GT);
        REL_OPS_MAP.put(GREATER_EQUALS, GE);
    };    
    private ICodeNode parseExpression(Token token) throws Exception
    {
        ICodeNode rootNode = parseSimpleExpression(token);

        token = currentToken();
        TokenType tokenType = token.getType();

        if (REL_OPS.contains(tokenType)) {

            ICodeNodeType nodeType = REL_OPS_MAP.get(tokenType);
            ICodeNode opNode = ICodeFactory.createICodeNode(nodeType);
            opNode.addChild(rootNode);

            token = nextToken();

            opNode.addChild(parseSimpleExpression(token));

            rootNode = opNode;
        }
        return rootNode;
    }
    
    private static final EnumSet<PascalTokenType> ADD_OPS = EnumSet.of(PLUS, MINUS, PascalTokenType.OR);
    private static final HashMap<PascalTokenType, ICodeNodeTypeImpl> ADD_OPS_OPS_MAP = new HashMap<PascalTokenType, ICodeNodeTypeImpl>();
    static {
        ADD_OPS_OPS_MAP.put(PLUS, ADD);
        ADD_OPS_OPS_MAP.put(MINUS, SUBTRACT);
        ADD_OPS_OPS_MAP.put(PascalTokenType.OR, ICodeNodeTypeImpl.OR);
    };
     private ICodeNode parseSimpleExpression(Token token) throws Exception
    {
        TokenType signType = null;

        TokenType tokenType = token.getType();
        if ((tokenType == PLUS) || (tokenType == MINUS)) {
            signType = tokenType;
            token = nextToken();
        }

        ICodeNode rootNode = parseTerm(token);

        if (signType == MINUS) {
            ICodeNode negateNode = ICodeFactory.createICodeNode(NEGATE);
            negateNode.addChild(rootNode);
            rootNode = negateNode;
        }
        token = currentToken();
        tokenType = token.getType();

        while (ADD_OPS.contains(tokenType)) {
            ICodeNodeType nodeType = ADD_OPS_OPS_MAP.get(tokenType);
            ICodeNode opNode = ICodeFactory.createICodeNode(nodeType);
            opNode.addChild(rootNode);

            token = nextToken();

            opNode.addChild(parseTerm(token));

            rootNode = opNode;

            token = currentToken();
            tokenType = token.getType();
        }

        return rootNode;
    }
    
    private static final EnumSet<PascalTokenType> MULT_OPS = EnumSet.of(STAR, SLASH, DIV, PascalTokenType.MOD, PascalTokenType.AND);
    private static final HashMap<PascalTokenType, ICodeNodeType> MULT_OPS_OPS_MAP = new HashMap<PascalTokenType, ICodeNodeType>();
    static {
        MULT_OPS_OPS_MAP.put(STAR, MULTIPLY);
        MULT_OPS_OPS_MAP.put(SLASH, FLOAT_DIVIDE);
        MULT_OPS_OPS_MAP.put(DIV, INTEGER_DIVIDE);
        MULT_OPS_OPS_MAP.put(PascalTokenType.MOD, ICodeNodeTypeImpl.MOD);
        MULT_OPS_OPS_MAP.put(PascalTokenType.AND, ICodeNodeTypeImpl.AND);
    };
    
    private ICodeNode parseTerm(Token token) throws Exception
    {
        ICodeNode rootNode = parseFactor(token);

        token = currentToken();
        TokenType tokenType = token.getType();

        while (MULT_OPS.contains(tokenType)) {
            ICodeNodeType nodeType = MULT_OPS_OPS_MAP.get(tokenType);
            ICodeNode opNode = ICodeFactory.createICodeNode(nodeType);
            opNode.addChild(rootNode);

            token = nextToken();
            
            opNode.addChild(parseFactor(token));

            rootNode = opNode;

            token = currentToken();
            tokenType = token.getType();
        }
        return rootNode;
    }
    private ICodeNode parseFactor(Token token) throws Exception
    {
        TokenType tokenType = token.getType();
        ICodeNode rootNode = null;

        switch ((PascalTokenType) tokenType) {
            case IDENTIFIER: {                
                String name = token.getText().toLowerCase();
                SymTabEntry id = symTabStack.lookup(name);
                if (id == null) {
                    errorHandler.flag(token, IDENTIFIER_UNDEFINED, this);
                    id = symTabStack.enterLocal(name);
                }

                rootNode = ICodeFactory.createICodeNode(VARIABLE);
                rootNode.setAttribute(ID, id);
                id.appendLineNumber(token.getLineNumber());

                token = nextToken();
                break;
            }

            case INTEGER: {
                rootNode = ICodeFactory.createICodeNode(INTEGER_CONSTANT);
                rootNode.setAttribute(VALUE, token.getValue());

                token = nextToken();
                break;
            }

            case REAL: {
                rootNode = ICodeFactory.createICodeNode(REAL_CONSTANT);
                rootNode.setAttribute(VALUE, token.getValue());

                token = nextToken();
                break;
            }

            case STRING: {
                String value = (String) token.getValue();

                rootNode = ICodeFactory.createICodeNode(STRING_CONSTANT);
                rootNode.setAttribute(VALUE, value);

                token = nextToken();
                break;
            }

            case NOT: {
                token = nextToken();

                rootNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.NOT);
                rootNode.addChild(parseFactor(token));

                break;
            }

            case LEFT_PAREN: {
                token = nextToken();
                rootNode = parseExpression(token);
                token = currentToken();
                if (token.getType() == RIGHT_PAREN) {
                    token = nextToken();
                }
                else {
                    errorHandler.flag(token, MISSING_RIGHT_PAREN, this);
                }
                break;
            }

            default: {
                errorHandler.flag(token, UNEXPECTED_TOKEN, this);
                break;
            }
        }

        return rootNode;
    }
}
