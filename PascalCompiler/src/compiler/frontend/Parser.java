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
/**
 *
 * @author jamey
 */
public abstract class Parser implements MessageProducer {
   protected static SymTab symTab;   
   protected static MessageHandler messageHandler;
   static {
       symTab = null;       
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
   public Token currentToken() {
    return scanner.currentToken();
   }
   public Token nextToken() throws Exception {
    return scanner.nextToken();
   }
   public void addMessageListener(MessageListener listener) {
       messageHandler.addListener(listener);
   }
   public void removeMessageListener(MessageListener listener) {
       messageHandler.removeListener(listener);
   }
   public void sendMessage(Message message) {
       messageHandler.sendMessage(message);
   }

    public ICode getICode() {
        return iCode;
    }

    public SymTab getSymTab() {
        return symTab;
    }
}
