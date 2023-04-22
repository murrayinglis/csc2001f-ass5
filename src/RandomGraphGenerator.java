import java.util.*;

public class RandomGraphGenerator {

    private List<String[]> edges;
    private String lastVertex;
    private int maxVertices, minVertices;

    public RandomGraphGenerator(int maxVertices, int minVertices)
    {
        this.maxVertices = maxVertices;
        this.minVertices = minVertices;
    }

    public RandomGraphGenerator(int maxVertices)
    {
        this.maxVertices = maxVertices;
        this.minVertices = 1;
    }

    public void generateGraph() {
        Random random = new Random();

        // Define the nodes in the graph
        int numVertices = random.nextInt(maxVertices - minVertices + 1) + minVertices; // The graph will have 4-9 nodes
        lastVertex = "Node" + numVertices;

        String[] nodes = new String[numVertices];
        for (int i = 0; i < numVertices; i++) {
            nodes[i] = "Node" + (i+1);
        }
        
        
        // Define the edges in the graph
        edges = new ArrayList<>();
        Set<String> addedEdges = new HashSet<>(); // Set of added edges to ensure no edges are repeated
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (random.nextBoolean()) { // Randomly decide whether to include edge (i, j)
                    String edge1 = nodes[i] + nodes[j];
                    String edge2 = nodes[j] + nodes[i];

                    if (!(addedEdges.contains(edge1) || addedEdges.contains(edge2)))
                    {
                        int weight = random.nextInt(10) + 1; // Random weight between 1 and 10
                        String[] edge = {nodes[i], nodes[j], Integer.toString(weight)};
                        edges.add(edge); 
                    }
                    
                }
            }
        }
    }

    public String getLastVertex() {return this.lastVertex;}

    public String toString() {

        String outputString = "";

        // Return the random graph as a string
        for (String[] edge : edges) {
            outputString = outputString.concat(edge[0] + " " + edge[1] + " " + edge[2] + "\n");
        
        }

        return outputString;
        
    }
}
