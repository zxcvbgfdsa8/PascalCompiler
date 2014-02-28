package compiler.intermediate;

import java.util.ArrayList;

/**
 *
 * @author jamey
 */
public interface SymTabEntry {
    public String getName();
    public SymTab getSymTab();
    public void setDefinition(Definition definition);
    public void setTypeSpec(TypeSpec typeSpec);
    public Definition getDefinition();
    public TypeSpec getTypeSpec();
    public void appendLineNumber(int lineNumber);
    public ArrayList<Integer> getLineNumbers();
    public void setAttribute(SymTabKey key, Object value);
    public Object getAttribute(SymTabKey key);
}
