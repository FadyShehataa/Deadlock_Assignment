package deadlock;

import java.util.ArrayList;

enum State {
    SAFE, UNSAFE
};

public final class Banker {
    private final Process[] processes;
    private int[] available;
    private State state;
    private ArrayList<Integer> safeSequence = new ArrayList<>(); // array to store safe sequence

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
        
        safeSequence = new ArrayList<>();
        boolean flag = true;
        
        while (flag) {
            flag = false;
            for (int i = 0; i < processesCopy.length; i++) {
                if (processesCopy[i].isFinished != false) {
                    continue;
                }

                if (isLessThanOrEqual(processesCopy[i], availableCopy) == true) {
                    flag = true;
                    safeSequence.add(i);
                    processesCopy[i].isFinished = true;
                    sumAvailable(processesCopy[i], availableCopy);
                }
            }
        }

        if (safeSequence.size() == processesCopy.length) {
            this.state = State.SAFE;
        }else{
            this.state = State.UNSAFE;
        }
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
            "State: " +  state + "\n" ;
    }
    
    
}
