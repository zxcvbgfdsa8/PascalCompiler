/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.intermediate.typeimpl;

import compiler.intermediate.TypeForm;

/**
 *
 * @author jamey
 */
public enum TypeFormImpl implements TypeForm {
    SCALAR, ENUMERATION, SUBRANGE, ARRAY, RECORD;
    public String toString() {
        return super.toString().toLowerCase();
    }
}
