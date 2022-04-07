package deadlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Actions {

    public static void requestResources(int[] available, Process process, int[] request) {
        boolean flag = true;

        for (int i = 0; i < available.length; i++) {
            if ((request[i] > available[i] || request[i] > process.need[i])) {
                flag = false;
                break;
            }
        }

        if (flag) {
            for (int i = 0; i < request.length; i++) {
                process.allocation[i] += request[i];
                process.calculateNeed();
                available[i] -= request[i];
            }
        }
    }
    
//    public static void requestResources(int[] available, Process process, int[] request) {
//        for (int i = 0; i < request.length; i++) {
//            process.need[i] += request[i];
//        }
//    }

    public static void releaseResources(int[] available, Process process, int[] release) {
        boolean flag = true;

        for (int i = 0; i < process.allocation.length; i++) {
            if (process.allocation[i] < release[i]) {
                flag = false;
                break;
            }
        }

        if (flag) {
            for (int i = 0; i < process.allocation.length; i++) {
                process.allocation[i] -= release[i];
                process.calculateNeed();
                available[i] += release[i];
            }
        }
    }

    public static void recover(Process []processes , int[] available) {
    	
    	Banker b = new Banker();
    	b.checkState(processes, available);
    	
        Map< Integer, Integer> map =new HashMap<Integer, Integer>();
        
        System.out.println("Enter priority for each process(Low priority(0) , High priority(10))");
        System.out.println("you shouldn't enter the same priority to different processes");
        int cnt = 1 ;
        for(int i = 0 ; i < processes.length ; i++) {
        	System.out.print("Enter priority for P" + cnt++ + ": ");
        	int pr = Deadlock.input.nextInt() ;
        	while(map.get(pr)!=null) {
        		System.out.print("Enter different priority: ");
        		pr = Deadlock.input.nextInt() ;
        	}
        	map.put(pr, i);
        }
               
        ArrayList<Integer> sortedKeys = new ArrayList<Integer>() ;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            sortedKeys.add(entry.getKey()) ;
        }
        Collections.sort(sortedKeys);
        
        int[] priority = new int[processes.length];
        for(int i = 0 ; i < sortedKeys.size() ; i++) {
        	priority[i] = map.get(sortedKeys.get(i));
        }
        
    	int i = 0 ;
        for(i = 0 ; i < priority.length && b.getState() == State.UNSAFE; i++) {
        	
        	Process[] processesCopy = new Process[processes.length-1];
        	Process victim = processes[priority[i]].copy() ;
        	boolean found = false ;
            for(int j=0; j<processes.length; j++){
            	if(j == priority[i]) {
            		found = true ;
            		continue ;
            	}
            	if(found) {
            		processesCopy[j-1] = processes[j].copy();
            	}else {
                processesCopy[j] = processes[j].copy();
            	}
            }
            
            int[] availableCopy = available.clone();
            
            b.sumAvailable(victim, availableCopy);
            b.checkState(processesCopy, availableCopy);
            if(b.getState() == State.SAFE) {
            	b.sumAvailable(victim, available);
            	for(int k = 0 ; k < available.length ; k++) {
            		processes[priority[i]].allocation[k] = 0 ;
            		processes[priority[i]].maximum()[k] = 0 ;
            	}
            	processes[priority[i]].calculateNeed();
            	break ;
            }
        }
        
        if(b.getState() == State.SAFE) {
        	System.out.println("\nVictim procsses to make system in safe state is process No." + ++priority[i] + "\n");
        }
        else {
			System.out.println("\ncan't make system in safe system\n");
		}
        
        
    }

    public static void exit() {
        System.exit(0);
    }
}
