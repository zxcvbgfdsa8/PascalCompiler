/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.intermediate.symtabimpl;
import compiler.intermediate.SymTabKey;

/**
 *
 * @author jamey
 */
public enum SymTabKeyImpl implements SymTabKey{
    CONSTANT_VALUE,
            
    ROUTINE_CODE, ROUTINE_SYMTAB, ROUTINE_ICODE, ROUTINE_PARMS, ROUTINE_REOUTINES,

    DATA_VALUE            
}
