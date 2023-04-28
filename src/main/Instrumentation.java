package main;

public class Instrumentation {
    private int vCount;
    private int eCount;
    private int pqCount;

    public Instrumentation(int vCount, int eCount, int pqCount) 
    {
        this.vCount = vCount;
        this.eCount = eCount;
        this.pqCount = pqCount;
    }

    public Instrumentation() 
    {
        this.vCount = 0;
        this.eCount = 0;
    }

    public void setVCount(int vCount) {this.vCount = vCount;}

    public void setECount(int eCount) {this.eCount = eCount;}

    public int getVCount() {return this.vCount;}

    public int getECount() {return this.eCount;}

    public int getPQCount() {return this.pqCount;}
}

