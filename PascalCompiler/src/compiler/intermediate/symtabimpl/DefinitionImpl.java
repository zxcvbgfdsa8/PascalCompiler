/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.intermediate.symtabimpl;

import compiler.intermediate.Definition;

/**
 *
 * @author jamey
 */
public enum DefinitionImpl implements Definition {
    CONSTANT, ENUMERATION_CONSTANT("enumeration constant"),
    TYPE, VARIABLE, FIELD("record field"),
    VALUE_PARM("value parameter"), VAR_PARM("VAR parameter"),
    PROGRAM_PARM("program parameter"),
    PROGRAM, PROCEDURE, FUNCTION,
    UNDEFINED;

    private String text;   
    DefinitionImpl() {
        this.text = this.toString().toLowerCase();
    }

    DefinitionImpl(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }
}
