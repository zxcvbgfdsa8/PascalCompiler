/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.frontend;

import compiler.frontend.pascal.PascalParserTD;
import compiler.frontend.pascal.PascalScanner;

/**
 *
 * @author jamey
 */
public class FrontendFactory {
    public static Parser createParser(String language, String type, Source source) throws Exception {
        if(language.equalsIgnoreCase("Pascal") && type.equalsIgnoreCase("top-down")) {
            Scanner scanner = new PascalScanner(source);
            return new PascalParserTD(scanner);
        } else if(!language.equalsIgnoreCase("Pascal")) {
            throw new Exception("Parser factory: Invalid language '" + language + "'");
        } else {
            throw new Exception("Parser factory: Invalid type'" + type + "'");
        }
    }
}
