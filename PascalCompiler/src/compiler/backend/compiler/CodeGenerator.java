/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.backend.compiler;
import compiler.backend.Backend;
import compiler.intermediate.ICode;
import compiler.intermediate.SymTab;
import compiler.message.Message;
import compiler.message.MessageListener;

import static compiler.message.MessageType.COMPILER_SUMMARY;
/**
 *
 * @author jamey
 */
public class CodeGenerator extends Backend {

    @Override
    public void process(ICode iCode, SymTab symTab) throws Exception {
        long startTime = System.currentTimeMillis();
        float elapsedTime = (System.currentTimeMillis() - startTime)/1000f;
        int instructionCount = 0;
        sendMessage(new Message(COMPILER_SUMMARY, new Number[] {instructionCount, elapsedTime}));        
    }       
}
