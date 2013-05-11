/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.backend;

import compiler.message.MessageProducer;
import compiler.message.MessageHandler;
import compiler.intermediate.SymTabStack;
import compiler.intermediate.ICode;
import compiler.message.Message;
import compiler.message.MessageListener;

/**
 *
 * @author jamey
 */
public abstract class Backend implements MessageProducer {
    protected static MessageHandler messageHandler;
    
    static {
        messageHandler = new MessageHandler();
    }
    protected SymTabStack symTabStack;
    protected ICode iCode;
    
    public abstract void process(ICode iCode, SymTabStack symTabStack) throws Exception;
    public void addMessageListener(MessageListener listener) {
       messageHandler.addListener(listener);
    }
    public void removeMessageListener(MessageListener listener) {
        messageHandler.removeListener(listener);
    }
    public void sendMessage(Message message) {
        messageHandler.sendMessage(message);
    }
}
