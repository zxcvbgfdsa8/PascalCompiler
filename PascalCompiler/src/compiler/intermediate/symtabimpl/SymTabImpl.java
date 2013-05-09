package compiler.intermediate.symtabimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import compiler.intermediate.*;
/**
 *
 * @author jamey
 */
public class SymTabImpl extends TreeMap <String, SymTabEntry> implements SymTab {
    private int nestingLevel;
    
    public SymTabImpl(int nestingLevel) {
        this.nestingLevel = nestingLevel;
    }
    public SymTabEntry enter(String name) {
        SymTabEntry entry = SymTabFactory.createSymTabEntry(name, this);
        put(name, entry);
        return entry;
    }
    public SymTabEntry lookup(String name) {
        return get(name);
    }
    public ArrayList<SymTabEntry> sortedEntries() {
        Collection<SymTabEntry> entries = values();
        Iterator<SymTabEntry> itr = entries.iterator();
        ArrayList<SymTabEntry> list = new ArrayList<SymTabEntry>(size());
        while(itr.hasNext()) {
            list.add(itr.next());
        }
        return list;
    }
    public int getNestingLevel() {
        return nestingLevel;
    }
}
