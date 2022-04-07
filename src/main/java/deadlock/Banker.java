package deadlock;

import java.util.ArrayList;
import java.util.Arrays;

enum State {
    SAFE, UNSAFE
};

public final class Banker {

    private State state;
    private ArrayList<String> safeSequence = new ArrayList<>(); // array to store safe sequence
    private boolean[] finishList;

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
    public void sumAvailable(Process p, int[] available) {
        for (int i = 0; i < Process.numberOfResources; i++) {
            available[i] += p.allocation[i];
        }
    }

    public void checkState(Process[] processes , int[] available) {
        // takiny a copy of the available
        int[] availableCopy = available.clone();
        
        //taking a copy of processes
        Process[] processesCopy = new Process[processes.length];
        for(int i=0; i<processes.length; i++){
            processesCopy[i] = processes[i].copy();
        }

        safeSequence = new ArrayList<>();
        
        // initilize finish list with false
        finishList = new boolean[processes.length];
        for (int i = 0; i < finishList.length; i++) {
            finishList[i] = false;
        }

        boolean flag = true;

        while (flag) {
            flag = false;
            for (int i = 0; i < processesCopy.length; i++) {
                if (finishList[i] != false) {
                    continue;
                }

                if (isLessThanOrEqual(processesCopy[i], availableCopy) == true) {
                    flag = true;
                    safeSequence.add("p" + (i + 1));
                    finishList[i] = true;
                    sumAvailable(processesCopy[i], availableCopy);
                }
            }
        }

        if (safeSequence.size() == processesCopy.length) {
            this.state = State.SAFE;
        } else {
            this.state = State.UNSAFE;
        }
    }

    public State getState() {
        return this.state;
    }

    @Override
    public String toString() {
        return "Sequence: " + safeSequence.toString() + "\n"
                + "Finish List: " + Arrays.toString(finishList) + "\n"
                + "State: " + state + "\n";
    }
     
}
