package deadlock;

import java.util.Arrays;

public final class Process {

    public static int numberOfResources;
    private final String id;
    public int[] allocation;
    public final int[] maximum;
    public int[] need;

    public Process(String id, int[] allocation, int[] maximum) {
        this.id = id;
        this.allocation = allocation;
        this.maximum = maximum;
        need = new int[numberOfResources];
        calculateNeed();
    }

    public void calculateNeed() {
        for (int i = 0; i < numberOfResources; i++) {
            need[i] = maximum[i] - allocation[i];
        }
    }

    public int[] maximum() {
        return this.maximum;
    }

    @Override
    public String toString() {
        return "Process ID: " + this.id + "\n"
                + "Process Maximum: " + Arrays.toString(this.maximum) + "\n"
                + "Process Allocation: " + Arrays.toString(this.allocation) + "\n"
                + "Process Need: " + Arrays.toString(this.need) + "\n";
    }

    public Process copy() {
        return new Process(this.id, this.allocation.clone(), this.maximum.clone());
    }

}
