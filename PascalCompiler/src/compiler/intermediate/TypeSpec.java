/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.intermediate;

/**
 *
 * @author jamey
 */
public interface TypeSpec {
    public TypeForm getForm();
    public void setIdentifier(SymTabEntry identifier);
    public SymTabEntry getIdentifier();
    public void setAttribute(TypeKey key, Object value);
    public Object getAttribute(TypeKey key);
    public boolean isPascalString();
    public TypeSpec baseType();
}
