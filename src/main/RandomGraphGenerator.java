package main;
import java.util.*;

/**
 * RandomGraphGenerator class
 * 
 * @author Murray Inglis
 */
public class RandomGraphGenerator {

    private List<String> edges;
    private String lastVertex;
    private int maxVertices, minVertices;
    private int numberEdges;
    private boolean isRandomGraph;

    /**
     * Constructor for generating a graph with a specific number of vertices and edges
     * 
     * @param numberVertices the number of vertices as an <code/>int<code>
     * @param numberEdges the number of edges as an <code/>int<code>
     */
    public RandomGraphGenerator(int numberVertices, int numberEdges)
    {
        this.maxVertices = numberVertices;
        this.isRandomGraph = false;
        this.numberEdges = numberEdges;
    }

    /**
     * Generate a random graph method.
     * Creates an array containing edges from node to node and the weighting of the edge.
     * 
     */
    public void generateGraph() {
        Random random = new Random();

        // Define the nodes in the graph
        int numVertices;
        if (isRandomGraph)
        {
            numVertices = random.nextInt(maxVertices - minVertices + 1) + minVertices;
        }
        else
        {
            numVertices = maxVertices;
        }

        lastVertex = "Node" + numVertices;

        String[] nodes = new String[numVertices];
        for (int i = 0; i < numVertices; i++) {
            nodes[i] = "Node" + (i+1);
        }
        
        
        // Define the edges in the graph
        edges = new ArrayList<>();
        Set<String> addedEdges = new HashSet<>(); // Set of added edges to ensure no edges are repeated

        while(edges.size()<numberEdges)
        {
            // Get 2 random node indices
            int i = random.nextInt(numVertices);
            int j = random.nextInt(numVertices);

            //Check the nodes are not equal
            if (i!=j)
            {
                String edge1 = nodes[i] + nodes[j];
                String edge2 = nodes[j] + nodes[i];

                if (!(addedEdges.contains(edge1) || addedEdges.contains(edge2)))
                    {
                        int weight = random.nextInt(10) + 1; // Random weight between 1 and 10
                        String edge = nodes[i] + " " + nodes[j] + " " + Integer.toString(weight);
                        edges.add(edge); // Add the edge to the edge array

                        // Add the edges to the repeated edges check list
                        addedEdges.add(edge1);
                        addedEdges.add(edge2);
                    }
            }
        }

    }

    /**
     * Get the label of the last node in the graph
     * 
     * @return the last node in the graph as a <code/>String<code>
     */
    public String getLastVertex() {return this.lastVertex;}

    /**
     * Converts the graph array to a string representation
     * 
     * @return the graph converted to a <code/>String<code>
     */
    public String toString() {

        String outputString = "";

        // Return the random graph as a string
        for (String edge : edges) {
            if (edge != edges.get(numberEdges-1)) {outputString = outputString + edge + "\n";}
            else {outputString = outputString + edge;} //Not adding new line to last row
        
        }

        return outputString;
        
    }

    /**
     * Get the number of rows in the graph
     * 
     * @return the number of edges as an <code/>int<code>
     */
    public int getNumberEdges() {return edges.size();}
}
