/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.backend.interpreter;

import compiler.intermediate.*;
import compiler.intermediate.icodeimpl.*;
import compiler.backend.*;
import compiler.backend.interpreter.executors.*;
import compiler.message.*;

import static compiler.intermediate.icodeimpl.ICodeNodeTypeImpl.*;
import static compiler.message.MessageType.INTERPRETER_SUMMARY;
/**
 *
 * @author jamey
 */
public class Executor extends Backend {
    protected static int executionCount;
    protected static RuntimeErrorHandler errorHandler;
    
    static {
        executionCount = 0;
        errorHandler = new RuntimeErrorHandler();
    }
    public Executor() { }
    public Executor(Executor parent) {
        super();        
    }

    @Override
    public void process(ICode iCode, SymTabStack symTabStack) throws Exception {
        this.symTabStack = symTabStack;
        this.iCode = iCode;

        
        long startTime = System.currentTimeMillis();
        ICodeNode rootNode = iCode.getRoot();
        StatementExecutor statementExecutor = new StatementExecutor(this);
        statementExecutor.execute(rootNode);
        
        float elapsedTime = (System.currentTimeMillis() - startTime)/1000f;
        int runtimeErrors = errorHandler.getErrorCount();

        sendMessage(new Message(INTERPRETER_SUMMARY, new Number[] {executionCount, runtimeErrors, elapsedTime}));        
    }
    
}
