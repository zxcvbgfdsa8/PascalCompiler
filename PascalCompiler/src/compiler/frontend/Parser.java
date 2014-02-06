/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.frontend;

import compiler.message.MessageProducer;
import compiler.message.MessageListener;
import compiler.message.MessageHandler;
import compiler.message.Message;

import compiler.intermediate.ICode;
import compiler.intermediate.SymTab;
import compiler.intermediate.SymTabFactory;
import compiler.intermediate.SymTabStack;
/**
 *
 * @author jamey
 */
public abstract class Parser implements MessageProducer {
   protected static SymTabStack symTabStack;   
   protected static MessageHandler messageHandler;
   static {
       symTabStack = SymTabFactory.createSymTabStack();       
       messageHandler = new MessageHandler();
   }
   
   protected Scanner scanner;
   protected ICode iCode;
   protected Parser(Scanner scanner) {
       this.scanner = scanner;
       this.iCode = null;
   }
   public abstract void parse() throws Exception;
   public abstract int getErrorCount();
   public Scanner getScanner() {
       return scanner;
   }
   public Token currentToken() {
    return scanner.currentToken();
   }
   public Token nextToken() throws Exception {
    return scanner.nextToken();
   }
   @Override
   public void addMessageListener(MessageListener listener) {
       messageHandler.addListener(listener);
   }
   @Override
   public void removeMessageListener(MessageListener listener) {
       messageHandler.removeListener(listener);
   }
   @Override
   public void sendMessage(Message message) {
       messageHandler.sendMessage(message);
   }

    public ICode getICode() {
        return iCode;
    }

    public SymTabStack getSymTabStack() {
        return symTabStack;
    }
}
