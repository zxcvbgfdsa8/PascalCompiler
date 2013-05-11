package compiler.intermediate;

import compiler.intermediate.icodeimpl.ICodeImpl;
import compiler.intermediate.icodeimpl.ICodeNodeImpl;
/**
 *
 * @author jamey
 */
public class ICodeFactory {
    public static ICode createICode() {
        return new ICodeImpl();
    }
    public static ICodeNode createICodeNode(ICodeNodeType type) {
        return new ICodeNodeImpl(type);
    }
}
