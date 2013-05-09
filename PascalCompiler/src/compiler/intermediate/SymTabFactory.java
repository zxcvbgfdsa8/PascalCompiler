
package compiler.intermediate;

import compiler.intermediate.symtabimpl.*;
/**
 *
 * @author jamey
 */
public class SymTabFactory {
    public static SymTabStack createSymTabStack() {
        return new SymTabStackImpl();
    }
    public static SymTab createSymTab(int nestingLevel) {
        return new SymTabImpl(nestingLevel);
    }
    public static SymTabEntry createSymTabEntry(String name, SymTab symTab) {
        return new SymTabEntryImpl(name, symTab);
    }
    
}
