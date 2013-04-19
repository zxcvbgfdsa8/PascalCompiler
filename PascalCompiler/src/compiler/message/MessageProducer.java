/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.message;

/**
 *
 * @author jamey
 */
public interface MessageProducer {
    public void addMessageListener(MessageListener listener);
    public void removeMessageListener(MessageListener listener);
    public void sendMessage(Message message);
}
