/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascalcompiler.frontend;

import pascalcompiler.intermediate.ICode;
import pascalcompiler.intermediate.SymTab;
/**
 *
 * @author jamey
 */
public abstract class Parser {
   protected static SymTab symTab;   
   static {
       symTab = null;       
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
}
