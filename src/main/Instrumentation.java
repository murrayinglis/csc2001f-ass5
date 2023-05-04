package main;

/**
 * Instrumentation class.
 * Used to store data regarding operation counts for the Graph class
 * 
 * @author Murray Inglis
 */
public class Instrumentation {
    // Instance variables (counters)
    private int vCount;
    private int eCount;
    private int pqCount;

    /**
     * Instrumentation constructor
     * 
     * @param vCount number of vertex operations as an <code/>int<code>
     * @param eCount number of edge operations as an <code/>int<code>
     * @param pqCount number of priority queue operations as an <code/>int<code>
     */
    public Instrumentation(int vCount, int eCount, int pqCount) 
    {
        this.vCount = vCount;
        this.eCount = eCount;
        this.pqCount = pqCount;
    }
    
    /**
     * Get the number of vertex operations
     * 
     * @return the number of vertex operations as an <code/>int<code>
     */
    public int getVCount() {return this.vCount;}

    /**
     * Get the number of edge operations
     * 
     * @return the number of edge operations as an <code/>int<code>
     */
    public int getECount() {return this.eCount;}

    /**
     * Get the number of priority queue operations
     * 
     * @return the number of priority queue operations as an <code/>int<code>
     */
    public int getPQCount() {return this.pqCount;}
}

