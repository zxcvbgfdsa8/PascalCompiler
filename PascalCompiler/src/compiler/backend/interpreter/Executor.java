/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.backend.interpreter;
import compiler.backend.Backend;
import compiler.intermediate.ICode;
import compiler.intermediate.SymTabStack;
import compiler.message.Message;
import compiler.message.MessageListener;

import static compiler.message.MessageType.INTERPRETER_SUMMARY;
/**
 *
 * @author jamey
 */
public class Executor extends Backend {

    @Override
    public void process(ICode iCode, SymTabStack symTabStack) throws Exception {
        long startTime = System.currentTimeMillis();
        float elapsedTime = (System.currentTimeMillis() - startTime)/1000f;
        int executionCount = 0;
        int runtimeErrors = 0;
        sendMessage(new Message(INTERPRETER_SUMMARY, new Number[] {executionCount, runtimeErrors, elapsedTime}));        
    }
    
}
