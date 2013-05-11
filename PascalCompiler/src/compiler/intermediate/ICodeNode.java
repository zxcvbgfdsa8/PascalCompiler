package compiler.intermediate;
import java.util.ArrayList;
/**
 *
 * @author jamey
 */
public interface ICodeNode {
    public ICodeNodeType getType();
    public ICodeNode getParent();
    public ArrayList<ICodeNode> getChildren();
    public void setAttribute(ICodeKey key, Object value);
    public Object getAttribute(ICodeKey key);
    public ICodeNode copy();
}
