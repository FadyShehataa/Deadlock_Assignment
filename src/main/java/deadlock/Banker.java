package deadlock;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

enum State {
    SAFE, UNSAFE
};

public final class Banker {
    private final Process[] processes;
    private int[] available;
    private State state;
    private ArrayList<String> safeSequence = new ArrayList<>(); // array to store safe sequence
    private boolean[] finishList;
    
    Banker(Process[] processes, int[] available) {
        this.processes = processes;
        this.available = available;
    }

    
    // Function take process and check if its need <= available(return true) ,else (return false)
    private boolean isLessThanOrEqual(Process p, int[] available) {
        for (int i = 0; i < Process.numberOfResources; i++) {
            if (p.need[i] > available[i]) {
                return false;
            }
        }

        return true;
    }

    // function takes process and sum its allocation with available
    private void sumAvailable(Process p, int[] available) {
        for (int i = 0; i < Process.numberOfResources; i++) {
            available[i] += p.allocation[i];
        }
    }

    public void checkState() {
        int[] availableCopy = this.available.clone();
        Process[] processesCopy = this.processes.clone();
        
        boolean []finish=new boolean[this.processes.length];
        
        for(int i=0;i<finish.length;i++)
        {
        	finish[i]=false;
        }
        safeSequence = new ArrayList<>();
        boolean flag = true;
        
        while (flag) {
            flag = false;
            for (int i = 0; i < processesCopy.length; i++) {
                if (finish[i] != false) {
                	
                    continue;
                    
                }

                if (isLessThanOrEqual(processesCopy[i], availableCopy) == true) {
                    flag = true;
                    safeSequence.add("p"+(i+1));
                    finish[i] = true;
                    sumAvailable(processesCopy[i], availableCopy);
                }
            }
        }

        if (safeSequence.size() == processesCopy.length) {
            this.state = State.SAFE;
        }else{
            this.state = State.UNSAFE;
        }
        this.finishList=finish;
    }
    
    public void setAvailable(int[] available){
        this.available = available;
        this.checkState();
    }
    
    public State getState(){
        return this.state;
    }
    
    @Override
    public String toString() {
        return 
            "Sequence: " + safeSequence.toString() + "\n" + 
            "Finish List: " +  Arrays.toString(finishList) + "\n" +
            "State: " +  state + "\n"
            ;
    }
    
    
}
