package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * GraphExperiment class
 * Contains main method to document the operations done by Dijkstra's algorithm
 * 
 * @author Murray Inglis
 */
public class GraphExperiment {

    /**
     * Main GraphExperiment method, generates graphs and performs Dijkstra 
     * 
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        
        System.out.println("Creating graphs..."); // Trace print statement


        // Clear the current contents of the data file
        try {
            FileWriter out = new FileWriter("data/Data.txt",false);
            out.write("V E vCount eCount pqCount\n");
            out.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        // Set of vertices and edges
        int[][] set = {{10,20},{10,30},{10,35},{10,40},{10,45},
                     {20,60},{20,100},{20,120},{20,150},{20,190},
                     {30,80},{30,120},{30,200},{30,300},{30,400},
                     {40,100},{40,200},{40,300},{40,500},{40,700},
                     {50,200},{50,400},{50,600},{50,800},{50,1000}};

        // Looping through the set and generating a random graph with the specified vertices and edges
        for (int i = 0; i<25; i++)
        {
            // Generating random graph
            RandomGraphGenerator randomGraph = new RandomGraphGenerator(set[i][0], set[i][1]);
            randomGraph.generateGraph();

            // Generating random .txt graph file
            String graphFileName = "graphs/Graph_" + set[i][0] + "_" + set[i][1] + ".txt"; 
            try (PrintWriter out = new PrintWriter(graphFileName)) {
                out.println(randomGraph.toString());
                out.close();
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }

            // This is sample code from Vula
            Graph g = new Graph( );
            try
            {   	
                FileReader fin = new FileReader(graphFileName);
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
                graphFile.close();
            }
            catch( IOException e )
            { System.err.println( e ); }

            g.dijkstra("Node1");

            // Getting instrumentation object
            Instrumentation instrumentation = g.getInstrumentation();

            // Generating data .txt file for the current graph
            try {
                
                // Writing new information to the file
                FileWriter out = new FileWriter("data/Data.txt",true);
                out.write(set[i][0] + " ");
                out.write(set[i][1] + " ");
                out.write(instrumentation.getVCount() + " ");
                out.write(instrumentation.getECount() + " ");
                out.write(instrumentation.getPQCount() + "\n");
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }


        System.out.println("Finished!"); // Trace print statement

    }    
}
