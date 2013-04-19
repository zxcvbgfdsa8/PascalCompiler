/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.frontend;

import compiler.message.MessageType;
import compiler.message.MessageProducer;
import compiler.message.MessageListener;
import compiler.message.MessageHandler;
import compiler.message.Message;
import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author jamey
 */
public class Source implements MessageProducer {

    public static final char EOL = '\n';
    public static final char EOF = (char) 0;
    
    protected static MessageHandler messageHandler;
    
    private BufferedReader reader;
    private String line;
    private int lineNum;
    private int currentPos;

    public Source(BufferedReader reader) throws IOException {
        this.lineNum = 0;
        this.currentPos = -2;
        this.reader = reader;
        messageHandler = new MessageHandler();
    }

    public char currentChar() throws Exception {
        if (currentPos == -2) {            
            readLine();
            return nextChar();
        } else if (line == null) {
            return EOF;
        } else if ((currentPos == -1) || (currentPos == line.length())) {
            readLine();
            return nextChar();
        } else {
            return line.charAt(currentPos);
        }
    }

    public char nextChar() throws Exception {
        ++currentPos;
        return currentChar();
    }

    public char peekChar() throws Exception {
        currentChar();
        if (line == null) {
            return EOF;
        }
        int nextPos = currentPos + 1;
        return nextPos < line.length() ? line.charAt(nextPos) : EOL;
    }

    public void readLine() throws IOException {
        line = reader.readLine();
        currentPos = -1;
        if (line != null) {
            ++lineNum;
        }
        
        if (line != null) {
            sendMessage(new Message(MessageType.SOURCE_LINE, new Object[] {lineNum, line}));
        }
    }

    public void close() throws Exception {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                throw ex;
            }
        }
    }

    public int getLineNum() {
        return lineNum;
    }
    public int getPosition() {
        return currentPos;
    }
    /**
     *
     * @param listener
     */
    @Override
   public void addMessageListener(MessageListener listener) {
       messageHandler.addListener(listener);
   }
    /**
     *
     * @param listener
     */
    @Override
   public void removeMessageListener(MessageListener listener) {
       messageHandler.removeListener(listener);
   }
    /**
     *
     * @param message
     */
    @Override
   public void sendMessage(Message message) {
       messageHandler.sendMessage(message);
   }
}
