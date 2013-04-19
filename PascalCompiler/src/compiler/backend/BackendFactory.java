/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.backend;
import compiler.backend.compiler.CodeGenerator;
import compiler.backend.interpreter.Executor;

/**
 *
 * @author jamey
 */
public class BackendFactory {
    public static Backend createBackend(String operation) throws Exception {
        if(operation.equalsIgnoreCase("compile")) {
            return new CodeGenerator();
        } else if(operation.equalsIgnoreCase("execute")) {
            return new Executor();
        } else {
            throw new Exception("Backend factory: Invalid operation '" + operation + "'");
        }
    }
}
