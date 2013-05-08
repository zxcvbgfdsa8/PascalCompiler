package compiler.intermediate;

import java.util.ArrayList;
/**
 *
 * @author jamey
 */
public interface SymTabStack {
    public int getCurrentNestingLevel();
    public SymTab getLocalSymTab();
    public SymTabEntry enterLocal(String name);
    public SymTabEntry lookupLocal(String name);
    public SymTabEntry lookup(String name);
}
