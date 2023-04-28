package main;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * Graph class created from sample code from Vula, with unnecesary code removed
 * 
 */
public class Graph
{
    public static final double INFINITY = Double.MAX_VALUE;
    private Map<String,Vertex> vertexMap = new HashMap<String,Vertex>( );

    private Instrumentation instrumentation;

    public Instrumentation getInstrumentation() {return this.instrumentation;}

    public Map<String,Vertex> getVertexMap() {return this.vertexMap;}

    /**
     * Add a new edge to the graph.
     */
    public void addEdge( String sourceName, String destName, double cost )
    {
        Vertex v = getVertex( sourceName );
        Vertex w = getVertex( destName );
        v.adj.add( new Edge( w, cost ) );
    }

    /**
     * Driver routine to handle unreachables and print total cost.
     * It calls recursive routine to print shortest path to
     * destNode after a shortest path algorithm has run.
     */
    public void printPath( String destName )
    {
        Vertex w = vertexMap.get( destName );
        if( w == null )
            throw new NoSuchElementException( "Destination vertex not found" );
        else if( w.dist == INFINITY )
            System.out.println( destName + " is unreachable" );
        else
        {
            System.out.print( "(Cost is: " + w.dist + ") " );
            printPath( w );
            System.out.println( );
        }
    }

    /**
     * If vertexName is not present, add it to vertexMap.
     * In either case, return the Vertex.
     */
    private Vertex getVertex( String vertexName )
    {
        Vertex v = vertexMap.get( vertexName );
        if( v == null )
        {
            v = new Vertex( vertexName );
            vertexMap.put( vertexName, v );
        }
        return v;
    }

    /**
     * Recursive routine to print shortest path to dest
     * after running shortest path algorithm. The path
     * is known to exist.
     */
    private void printPath( Vertex dest )
    {
        if( dest.prev != null )
        {
            printPath( dest.prev );
            System.out.print( " to " );
        }
        System.out.print( dest.name );
    }
    
    /**
     * Initializes the vertex output info prior to running
     * any shortest path algorithm.
     */
    private void clearAll( )
    {
        for( Vertex v : vertexMap.values( ) )
            v.reset( );
    }

    /**
     * Single-source weighted shortest-path algorithm. (Dijkstra) 
     * using priority queues based on the binary heap
     */
    public void dijkstra( String startName )
    {
        PriorityQueue<Path> pq = new PriorityQueue<Path>( );

        Vertex start = vertexMap.get( startName );
        if( start == null )
            throw new NoSuchElementException( "Start vertex not found" );

        clearAll( );
        pq.add( new Path( start, 0 ) ); start.dist = 0;
        
        // Initiliasing variables for operation counting
        int vCount = 0;
        int eCount = 0;
        int pqCount = 0;

        int nodesSeen = 0;
        while( !pq.isEmpty( ) && nodesSeen < vertexMap.size( ) )
        {
            Path vrec = pq.remove( );
            if (pq.size()!=0) {pqCount += (int)(Math.log(pq.size()) / Math.log(2));} // priority queue operation counter

            Vertex v = vrec.dest;
            if( v.scratch != 0 )  // already processed v
                continue;
                
            v.scratch = 1;
            nodesSeen++;

            vCount++;  // vertex operation counter

            for( Edge e : v.adj )
            {
                Vertex w = e.dest;
                double cvw = e.cost;

                eCount++; // edge operation counter
                
                if( cvw < 0 )
                    throw new GraphException( "Graph has negative edges" );
                    
                if( w.dist > v.dist + cvw )
                {
                    w.dist = v.dist +cvw;
                    w.prev = v;
                    pq.add( new Path( w, w.dist ) );
                    if (pq.size()!=0) {pqCount += (int)(Math.log(pq.size()) / Math.log(2));} // priority queue operation counter
                }

                
            }
        }

        instrumentation = new Instrumentation(vCount, eCount, pqCount);
    }




    // Represents a vertex in the graph.
    private class Vertex
    {
        public String     name;   // Vertex name
        public List<Edge> adj;    // Adjacent vertices
        public double     dist;   // Cost
        public Vertex     prev;   // Previous vertex on shortest path
        public int        scratch;// Extra variable used in algorithm

        public Vertex( String nm )
        { name = nm; adj = new LinkedList<Edge>( ); reset( ); }

        public void reset( )
        //  { dist = Graph.INFINITY; prev = null; pos = null; scratch = 0; }    
        { dist = Graph.INFINITY; prev = null; scratch = 0; }
        
    // public PairingHeap.Position<Path> pos;  // Used for dijkstra2 (Chapter 23)
    }



    // Represents an edge in the graph.
    private class Edge
    {
        public Vertex     dest;   // Second vertex in Edge
        public double     cost;   // Edge cost
        
        public Edge( Vertex d, double c )
        {
            dest = d;
            cost = c;
        }
    }


    
    // Represents an entry in the priority queue for Dijkstra's algorithm.
    private class Path implements Comparable<Path>
    {
        public Vertex     dest;   // w
        public double     cost;   // d(w)
        
        public Path( Vertex d, double c )
        {
            dest = d;
            cost = c;
        }
        
        public int compareTo( Path rhs )
        {
            double otherCost = rhs.cost;
            
            return cost < otherCost ? -1 : cost > otherCost ? 1 : 0;
        }
    }   
}
