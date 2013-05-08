package compiler.frontend.pascal.tokens;

import compiler.frontend.*;
import compiler.frontend.pascal.*;

import static compiler.frontend.pascal.PascalTokenType.*;

/**
 * <h1>PascalErrorToken</h1>
 *
 * <p>Pascal error token.</p>
 *
 * <p>Copyright (c) 2009 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
public class PascalErrorToken extends PascalToken
{
    /**
     * Constructor.
     * @param source the source from where to fetch subsequent characters.
     * @param errorCode the error code.
     * @param tokenText the text of the erroneous token.
     * @throws Exception if an error occurred.
     */
    public PascalErrorToken(Source source, PascalErrorCode errorCode,
                            String tokenText)
        throws Exception
    {
        super(source);

        this.text = tokenText;
        this.type = ERROR;
        this.value = errorCode;
    }

    /**
     * Do nothing.  Do not consume any source characters.
     * @throws Exception if an error occurred.
     */
    public void extract() throws Exception
    {
        
    }
}
