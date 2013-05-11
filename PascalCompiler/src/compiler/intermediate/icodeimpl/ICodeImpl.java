package compiler.intermediate.icodeimpl;

import compiler.intermediate.ICode;
import compiler.intermediate.ICodeNode;

/**
 *
 * @author jamey
 */
public class ICodeImpl implements ICode{
    private ICodeNode root;
    
    public ICodeNode setRoot(ICodeNode node) {
        root = node;
        return root;
    }
    public ICodeNode getRoot() {
        return root;
    }
}
