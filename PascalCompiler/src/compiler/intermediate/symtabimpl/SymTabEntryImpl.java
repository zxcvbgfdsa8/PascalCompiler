package compiler.intermediate.symtabimpl;

import java.util.ArrayList;
import java.util.HashMap;

import compiler.intermediate.*;

/**
 *
 * @author jamey
 */
public class SymTabEntryImpl extends HashMap<SymTabKey, Object> implements SymTabEntry {
    private String name;
    private SymTab symTab;
    private Definition definition;
    private TypeSpec typeSpec;
    private ArrayList<Integer> lineNumbers;
    
    public SymTabEntryImpl(String name, SymTab symTab) {
        this.name = name;
        this.symTab = symTab;
        this.lineNumbers = new ArrayList<Integer>();
    }
    public void appendLineNumber(int lineNumber) {
        lineNumbers.add(lineNumber);        
    }
    public void setAttribute(SymTabKey key, Object value) {
        put(key, value);
    }
    public Object getAttribute(SymTabKey key) {
        return get(key);
    }
    public ArrayList<Integer> getLineNumbers() {
        return lineNumbers;
    }
    public String getName() {
        return name;
    }
    public SymTab getSymTab() {
        return symTab;
    }
    public void setDefinition(Definition definition) {
        this.definition = definition;
    }
    public Definition getDefinition() {
        return definition;
    }
    public void setTypeSpec(TypeSpec typeSpec) {
        this.typeSpec = typeSpec;
    }
    public TypeSpec getTypeSpec() {
        return typeSpec;
    }
}
