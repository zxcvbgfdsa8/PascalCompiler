/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.intermediate.typeimpl;

import java.util.HashMap;

import compiler.intermediate.*;
import compiler.intermediate.symtabimpl.Predefined;

import static compiler.intermediate.typeimpl.TypeFormImpl.ARRAY;
import static compiler.intermediate.typeimpl.TypeFormImpl.SUBRANGE;
import static compiler.intermediate.typeimpl.TypeKeyImpl.*;
/**
 *
 * @author jamey
 */
public class TypeSpecImpl extends HashMap<TypeKey, Object> implements TypeSpec {
    private TypeForm form;
    private SymTabEntry identifier; 
    
    public TypeSpecImpl(TypeForm form) {
        this.form = form;
        this.identifier = null;
    }
    public TypeSpecImpl(String value) {
        this.form = ARRAY;

        TypeSpec indexType = new TypeSpecImpl(SUBRANGE);
        indexType.setAttribute(SUBRANGE_BASE_TYPE, Predefined.integerType);
        indexType.setAttribute(SUBRANGE_MIN_VALUE, 1);
        indexType.setAttribute(SUBRANGE_MAX_VALUE, value.length());

        setAttribute(ARRAY_INDEX_TYPE, indexType);
        setAttribute(ARRAY_ELEMENT_TYPE, Predefined.charType);
        setAttribute(ARRAY_ELEMENT_COUNT, value.length());
    }
    public TypeForm getForm() {
        return form;
    }
    @Override
    public void setIdentifier(SymTabEntry identifier) {
        this.identifier = identifier;
    }
    public SymTabEntry getIdentifier() {
        return identifier;
    }
    public void setAttribute(TypeKey key, Object value) {
        this.put(key, value);
    }
    public Object getAttribute(TypeKey key) {
        return this.get(key);
    }
    public boolean isPascalString() {
        if (form == ARRAY) {
            TypeSpec elmtType  = (TypeSpec) getAttribute(ARRAY_ELEMENT_TYPE);
            TypeSpec indexType = (TypeSpec) getAttribute(ARRAY_INDEX_TYPE);

            return (elmtType.baseType()  == Predefined.charType) && (indexType.baseType() == Predefined.integerType);
        }
        else {
            return false;
        }
    }
    public TypeSpec baseType() {
        return form == SUBRANGE ? (TypeSpec) getAttribute(SUBRANGE_BASE_TYPE) : this;
    }
}
