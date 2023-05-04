package main;
// Used to signal violations of preconditions for
// various shortest path algorithms.

/**
 * Graph exception class.
 * From sample code on Vula.
 *
 */
public class GraphException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

    /**
     * GraphException construcotr
     * 
     * @param name as a <code/>String<code>
     */
	public GraphException( String name )
    {
        super( name );
    }
}
