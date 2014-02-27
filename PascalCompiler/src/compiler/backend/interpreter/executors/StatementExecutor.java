/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.backend.interpreter.executors;

import compiler.intermediate.*;
import compiler.intermediate.icodeimpl.*;
import compiler.backend.interpreter.*;
import compiler.message.*;

import static compiler.intermediate.ICodeNodeType.*;
import static compiler.intermediate.icodeimpl.ICodeKeyImpl.*;
import static compiler.backend.interpreter.RuntimeErrorCode.*;
import static compiler.message.MessageType.SOURCE_LINE;

/**
 *
 * @author jamey
 */
public class StatementExecutor extends Executor {
    public StatementExecutor (Executor parent) {
        super(parent);
    }
    public Object execute(ICodeNode node) {
        ICodeNodeTypeImpl nodeType = (ICodeNodeTypeImpl) node.getType();
        sendSourceLineMessage(node);
        
        switch (nodeType) {
            case COMPOUND: {
                CompoundExecutor compoundExecutor = new CompoundExecutor(this);
                return compoundExecutor.execute(node);
            }
            case ASSIGN: {
                AssignmentExecutor assignmentExecutor = new AssignmentExecutor(this);
                return assignmentExecutor.execute(node);
            }
            case LOOP: {
                LoopExecutor loopExecutor = new LoopExecutor(this);
                return loopExecutor.execute(node);
            }
            case IF: {
                IfExecutor ifExecutor = new IfExecutor(this);
                return ifExecutor.execute(node);
            }
            case SELECT: {
                SelectExecutor selectExecutor = new SelectExecutor(this);
                return selectExecutor.execute(node);
            }
            case NO_OP: return null;
                
            default: {
                errorHandler.flag(node, UNIMPLEMENTED_FEATURE, this);
                return null;
            }
        }
    }
    private void sendSourceLineMessage(ICodeNode node) {
        Object lineNumber = node.getAttribute(LINE);
        
        if (lineNumber != null) {
            sendMessage(new Message(SOURCE_LINE, lineNumber));
        }
    }
}
