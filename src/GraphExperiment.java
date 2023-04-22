import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class GraphExperiment {
    public static void main(String[] args) throws FileNotFoundException {

        // Generating random graph
        RandomGraphGenerator randomGraph = new RandomGraphGenerator(9, 5);
        randomGraph.generateGraph();

        // Generating graph file
        try (PrintWriter out = new PrintWriter("Graph.txt")) {
            out.println(randomGraph.toString());
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        // This is sample code from Vula
        Graph g = new Graph( );
        try
        {   	
            FileReader fin = new FileReader("Graph.txt");
            Scanner graphFile = new Scanner( fin );

            // Read the edges and insert
            String line;
            while( graphFile.hasNextLine( ) )
            {
                line = graphFile.nextLine( );
                StringTokenizer st = new StringTokenizer( line );

                try
                {
                    if( st.countTokens( ) != 3 )
                    {
                        System.err.println( "Skipping ill-formatted line " + line );
                        continue;
                    }
                    String source  = st.nextToken( );
                    String dest    = st.nextToken( );
                    int    cost    = Integer.parseInt( st.nextToken( ) );
                    g.addEdge( source, dest, cost );
                }
                catch( NumberFormatException e )
                  { System.err.println( "Skipping ill-formatted line " + line ); }
             }
         }
         catch( IOException e )
           { System.err.println( e ); }

         System.out.println( "File read..." );
         System.out.println( g.getVertexMap().size( ) + " vertices" );

        // My own code from here
        g.dijkstra("Node1");
        g.printPath(randomGraph.getLastVertex());

        
    }    
}
