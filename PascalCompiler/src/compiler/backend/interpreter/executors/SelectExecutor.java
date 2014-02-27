/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.backend.interpreter.executors;

import java.util.ArrayList;
import java.util.HashMap;

import compiler.intermediate.*;
import compiler.backend.interpreter.*;

import static compiler.intermediate.icodeimpl.ICodeNodeTypeImpl.*;
import static compiler.intermediate.icodeimpl.ICodeKeyImpl.*;
import static compiler.backend.interpreter.RuntimeErrorCode.*;

/**
 *
 * @author jamey
 */
public class SelectExecutor extends StatementExecutor
{
    public SelectExecutor(Executor parent) {
        super(parent);
    }
    private static HashMap<ICodeNode, HashMap<Object, ICodeNode>> jumpCache = new HashMap<ICodeNode, HashMap<Object, ICodeNode>>();

    public Object execute(ICodeNode node) {
        HashMap<Object, ICodeNode> jumpTable = jumpCache.get(node);
        if (jumpTable == null) {
            jumpTable = createJumpTable(node);
            jumpCache.put(node, jumpTable);
        }

        ArrayList<ICodeNode> selectChildren = node.getChildren();
        ICodeNode exprNode = selectChildren.get(0);

        ExpressionExecutor expressionExecutor = new ExpressionExecutor(this);
        Object selectValue = expressionExecutor.execute(exprNode);

        ICodeNode statementNode = jumpTable.get(selectValue);
        if (statementNode != null) {
            StatementExecutor statementExecutor = new StatementExecutor(this);
            statementExecutor.execute(statementNode);
        }

        ++executionCount;
        return null;
    }

    private HashMap<Object, ICodeNode> createJumpTable(ICodeNode node)
    {
        HashMap<Object, ICodeNode> jumpTable = new HashMap<Object, ICodeNode>();

        ArrayList<ICodeNode> selectChildren = node.getChildren();
        for (int i = 1; i < selectChildren.size(); ++i) {
            ICodeNode branchNode = selectChildren.get(i);
            ICodeNode constantsNode = branchNode.getChildren().get(0);
            ICodeNode statementNode = branchNode.getChildren().get(1);

            ArrayList<ICodeNode> constantsList = constantsNode.getChildren();
            for (ICodeNode constantNode : constantsList) {

                Object value = constantNode.getAttribute(VALUE);
                jumpTable.put(value, statementNode);
            }
        }

        return jumpTable;
    }
}

