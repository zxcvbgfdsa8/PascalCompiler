/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.intermediate;

import compiler.intermediate.typeimpl.*;
/**
 *
 * @author jamey
 */
public class TypeFactory {
    public static TypeSpec createType(TypeForm form) {
        return new TypeSpecImpl(form);
    }
    public static TypeSpec createStringType(String value) {
        return new TypeSpecImpl(value);
    }
}
