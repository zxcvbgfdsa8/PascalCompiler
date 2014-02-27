/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.backend.interpreter.executors;

import java.util.ArrayList;

import compiler.intermediate.*;
import compiler.intermediate.icodeimpl.*;
import compiler.backend.interpreter.*;

import static compiler.intermediate.icodeimpl.ICodeNodeTypeImpl.*;
import static compiler.intermediate.icodeimpl.ICodeKeyImpl.*;
import static compiler.backend.interpreter.RuntimeErrorCode.*;


/**
 *
 * @author jamey
 */
public class LoopExecutor extends StatementExecutor {
    public LoopExecutor(Executor parent) {
        super(parent);
    }
    @Override
    public Object execute(ICodeNode node) {
        boolean exitLoop = false;
        ICodeNode exprNode = null;
        ArrayList<ICodeNode> loopChildren = node.getChildren();
        
        ExpressionExecutor expressionExecutor = new ExpressionExecutor(this);
        StatementExecutor statementExecutor = new StatementExecutor(this);
        while(!exitLoop) {
            ++executionCount;
            for (ICodeNode child : loopChildren) {
                ICodeNodeTypeImpl childType = (ICodeNodeTypeImpl) child.getType();
                if (childType == TEST) {
                    if (exprNode == null) {
                        exprNode = child.getChildren().get(0);
                    }
                    exitLoop = (Boolean) expressionExecutor.execute(exprNode);
                }
                else {
                    statementExecutor.execute(child);
                }
                if (exitLoop) {
                    break;
                }
            }            
        }
        return null;
    }
}
