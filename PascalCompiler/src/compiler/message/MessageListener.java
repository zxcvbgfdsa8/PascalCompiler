/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.message;

import compiler.message.Message;

/**
 *
 * @author jamey
 */
public interface MessageListener {
    public void messageReceived(Message message);
}
