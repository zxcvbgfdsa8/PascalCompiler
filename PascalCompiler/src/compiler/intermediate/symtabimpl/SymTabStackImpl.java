package compiler.intermediate.symtabimpl;

import java.util.ArrayList;
import compiler.intermediate.*;

/**
 *
 * @author jamey
 */
public class SymTabStackImpl extends ArrayList<SymTab> implements SymTabStack {
    private int currentNestingLevel;
    
    public SymTabStackImpl() {
        this.currentNestingLevel = 0;
        add(SymTabFactory.createSymTab(currentNestingLevel));
    }
    public SymTab getLocalSymTab() {
        return get(currentNestingLevel);
    }
    public SymTabEntry enterLocal(String name) {
        return get(currentNestingLevel).enter(name);
    }
    public SymTabEntry lookupLocal(String name) {
        return get(currentNestingLevel).lookup(name);
    }
    public SymTabEntry lookup(String name) {
        return lookupLocal(name);
    }
    public int getCurrentNestingLevel() {
        return currentNestingLevel;
    }
            
}
