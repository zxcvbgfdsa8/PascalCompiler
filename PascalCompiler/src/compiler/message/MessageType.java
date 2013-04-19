/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.message;

/**
 *
 * @author jamey
 */
public enum MessageType {
    SOURCE_LINE, SYNTAX_ERROR,
    PARSER_SUMMARY, INTERPRETER_SUMMARY, COMPILER_SUMMARY,
    MISCELLANEOUS, TOKEN,
    ASSIGN, FETCH, BREAKPOINT, RUNTIME_ERROR,
    CALL, RETURN,
}
