package main;
// Used to signal violations of preconditions for
// various shortest path algorithms.
public class GraphException extends RuntimeException
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GraphException( String name )
    {
        super( name );
    }
}
