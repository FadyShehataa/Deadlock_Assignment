package deadlockAssignment;
import java.util.ArrayList;

public class Deadlock {
	private Process process[];
	private int[] available;
	private boolean state;
	private ArrayList<Integer> safeSequence = new ArrayList<Integer>(); // array to store safe sequence

	// Constructor takes array of processes , array of integer(available), and set state false
	public Deadlock(Process[] process, int[] available) {
		this.process = process;
		this.available = available;
		this.state = false;
	}
	
	// Function take process and check if its need <= available(return true) ,else (return false)
	private boolean isLessThanOrEqual(Process p) {
		for (int i = 0; i < Process.getNumberOfResources(); i++) {
			if (p.getNeed()[i] > available[i]) {
				return false;
			}
		}
		return true;
	}

	// function takes process and sum its allocation with available
	private void sumAvailable(Process p) {
		for (int i = 0; i < Process.getNumberOfResources(); i++) {
			available[i] += p.getAllocation()[i];
		}
	}

	public void display() {
		System.out.println();
		for (int i = 0; i < process.length; i++) {
			process[i].display();
		}
		System.out.print("available is: ");
		for (int i = 0; i < Process.getNumberOfResources(); i++) {
			System.out.print(available[i] + " ");
		}
		System.out.println();
		printSafeSequence();
		System.out.println();
		System.out.println("--------------------------------");

	}

	// function make banker's algorithm
	public void bankerAlgorithm() {
		boolean flag = true;
		while (flag) {
			flag = false;
			for (int i = 0; i < process.length; i++) {
				if (process[i].isFinish() != false) {
					continue;
				}
				if (isLessThanOrEqual(process[i]) == true) {
					flag = true;
					safeSequence.add(i);
					process[i].setFinish(true);
					sumAvailable(process[i]);
					display();
				}
			}
		}

		if (safeSequence.size() == process.length) {
			state = true;
		}
		printState();
		printSafeSequence();
	}

	// function print safe sequence
	public void printSafeSequence() {
		for (Integer i = 0; i < safeSequence.size(); i++) {
			System.out.print("P" + safeSequence.get(i));
			if (i != safeSequence.size() - 1) {
				System.out.print(" -> ");
			}
		}
	}
	// function print state(approve or deny)
	public void printState() {
		if (state) {
			System.out.println("the bank decisions to approve the requests");
		} else {
			System.out.println("the bank decisions to deny the requests");
		}
	}
	public void processRequest(int p,int[]request)
	{
		Process proc=process[p-1];
		boolean flag=true;
		for(int i=0;i<available.length;i++)
		{
			if((request[i]>available[i]||request[i]>proc.need[i]))
			{
				flag=false;
			}
		}
		if(flag)
		{
			for(int i=0;i<request.length;i++)
			{
				proc.getAllocation()[i]+=request[i];
			}
			
		}
	}
	public void processRelease(int p,int[]release)
	{
		Process proc=process[p-1];
		boolean flag=true;
		for(int i=0;i<proc.getAllocation().length;i++)
		{
			if(proc.getAllocation()[i]<release[i])
			{
				flag=false;
			}
		}
		if(flag)
		{
			for(int i=0;i<proc.getAllocation().length;i++)
			{
				proc.getAllocation()[i]-=release[i];
			}
		}
		System.out.println("Release successful");
	}
}
