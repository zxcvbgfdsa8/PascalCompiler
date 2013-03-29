/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascalcompiler.frontend;

import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author jamey
 */
public class Source {

    public static final char EOL = '\n';
    public static final char EOF = (char) 0;
    private BufferedReader reader;
    private String line;
    private int lineNum;
    private int currentPos;

    public Source(BufferedReader reader) throws IOException {
        this.lineNum = 0;
        this.currentPos = -2;
        this.reader = reader;
    }

    public char currentChar() throws Exception {
        if (currentPos == -1) {
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
}
