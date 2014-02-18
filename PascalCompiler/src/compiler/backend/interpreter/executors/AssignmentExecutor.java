/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.backend.interpreter.executors;

import java.util.ArrayList;

import compiler.intermediate.*;
import compiler.intermediate.icodeimpl.*;
import compiler.backend.interpreter.*;
import compiler.message.*;

import static compiler.intermediate.symtabimpl.SymTabKeyImpl.*;
import static compiler.intermediate.icodeimpl.ICodeKeyImpl.*;
import static compiler.message.MessageType.ASSIGN;

/**
 *
 * @author jamey
 */
public class AssignmentExecutor extends StatementExecutor {

    public AssignmentExecutor(Executor parent) {
        super(parent);
    }

    public Object execute(ICodeNode node) {
        ArrayList<ICodeNode> children = node.getChildren();
        ICodeNode variableNode = children.get(0);
        ICodeNode expressionNode = children.get(1);

        ExpressionExecutor expressionExecutor = new ExpressionExecutor(this);
        Object value = expressionExecutor.execute(expressionNode);

        SymTabEntry variableId = (SymTabEntry) variableNode.getAttribute(ID);
        variableId.setAttribute(DATA_VALUE, value);

        sendMessage(node, variableId.getName(), value);

        ++executionCount;

        return null;
    }

    private void sendMessage(ICodeNode node, String variableName, Object value) {
        Object lineNumber = node.getAttribute(LINE);
        if (lineNumber != null) {
            sendMessage(new Message(ASSIGN, new Object[]{lineNumber, variableName, value}));
        }
    }
}
