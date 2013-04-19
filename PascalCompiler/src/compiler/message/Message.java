/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.message;

import compiler.message.MessageType;

/**
 *
 * @author jamey
 */
public class Message {
    private MessageType type;
    private Object body;
    public Message(MessageType type, Object body) {
        this.type = type;
        this.body = body;
    }

    public MessageType getType() {
        return type;
    }
    public Object getBody() {
        return body;
    }
}
