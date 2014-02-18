/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.backend.interpreter.executors;

import java.util.ArrayList;

import compiler.intermediate.*;
import compiler.backend.interpreter.*;

/**
 *
 * @author jamey
 */
public class CompoundExecutor extends StatementExecutor {

    public CompoundExecutor(Executor parent) {
        super(parent);
    }

    @Override
    public Object execute(ICodeNode node) {
        StatementExecutor statementExecutor = new StatementExecutor(this);
        ArrayList<ICodeNode> children = node.getChildren();
        for (ICodeNode child : children) {
            statementExecutor.execute(child);
        }
        return null;
    }
}
