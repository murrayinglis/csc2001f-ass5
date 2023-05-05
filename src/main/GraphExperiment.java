package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

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


        // Clear the current contents of the data files
        try {
            FileWriter out = new FileWriter("data/Data.txt",false);
            out.write("V E vCount eCount pqCount\n");
            out.close();
            
            out = new FileWriter("data/Plot.txt",false);
            out.write("V ops ElogV\n");
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
            String graphFileName = "data/graphs/Graph_" + set[i][0] + "_" + set[i][1] + ".txt"; 
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

            // Generating Data.txt file for the current graph
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

            // Generating Plot.txt file for the current graph
            try {
                
                // Writing new information to the file
                FileWriter out = new FileWriter("data/Plot.txt",true);

                int ops = instrumentation.getECount()+instrumentation.getVCount()+instrumentation.getPQCount();
                double ElogV = set[i][1]*(Math.log(set[i][0])/Math.log(2));

                out.write(set[i][0] + " ");
                out.write(ops + " ");
                out.write(ElogV + "\n");
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }


        System.out.println("Finished!"); // Trace print statement
        
        // Creating line chart using Java library, JFreeChart
        // Read data from file
        ArrayList<Double> ops = new ArrayList<>();
        ArrayList<Double> ElogV = new ArrayList<>();
        File file = new File("data/Plot.txt");
        Scanner scanner = new Scanner(file).useLocale(Locale.forLanguageTag("C.UTF-8"));
        scanner.nextLine(); // Skip heading
        while (scanner.hasNext()) {
            int v = scanner.nextInt();
            int op = scanner.nextInt();
            double elogv = scanner.nextDouble();
            ops.add((double) op);
            ElogV.add(elogv);
        }
        scanner.close();
 
        // Create dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < ops.size(); i++) {
            dataset.addValue(ops.get(i), "Ops", String.format("V=%d", i+1));
            dataset.addValue(ElogV.get(i), "ElogV", String.format("V=%d", i+1));
        }
 
        // Create chart
        JFreeChart chart = ChartFactory.createLineChart(
                "Ops and ElogV", // Chart title
                "Experiment number", // X-axis label
                "Magnitude", // Y-axis label
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Plot orientation
                true, // Show legend
                true, // Use tooltips
                false // Generate URLs
        );
         
        try {
            ChartUtilities.writeChartAsJPEG(new FileOutputStream("data/chart_javalib.jpeg"), chart, 600, 400);
        } catch (IOException e) {e.printStackTrace();}
    

        // Creating line chart using python
        try {
            ProcessBuilder pb = new ProcessBuilder("python3", "src/PythonScripts/plot.py");
            pb.redirectErrorStream(true);
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            Process process = pb.start();
            int exitCode = process.waitFor();
            System.out.println("Exited with code " + exitCode);
        }
        catch (IOException | InterruptedException e) {
           e.printStackTrace();
        }
    }    
}
