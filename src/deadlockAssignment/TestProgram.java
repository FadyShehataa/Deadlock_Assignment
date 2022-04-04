package deadlockAssignment;
import java.util.Scanner;

public class TestProgram {

	private Scanner input = new Scanner(System.in);
	private int numberProcesses;
	private int numberResouces;
	private Process process[];
	private int available[];
	private int pNumber;
	private int requests[];
	private int release[];
	public TestProgram() {
		EnterNumberOfProcesses();
		process = new Process[numberProcesses];
		EnterNumberOfResources();
		EnterAvailable();
		EnterAllocationAndMaximum();
		Deadlock deadlock = new Deadlock(process, available);
		deadlock.bankerAlgorithm();
		requestProcess();
		
		deadlock.processRequest(pNumber,requests);
		deadlock.bankerAlgorithm();
		processRelease();
		deadlock.processRelease(pNumber,requests);
	}
	public void EnterNumberOfProcesses() {
		System.out.print("Enter number of Processes: ");
		numberProcesses = input.nextInt();
	}

	public void EnterNumberOfResources() {
		System.out.print("Enter number of Resources: ");
		numberResouces = input.nextInt();
	}

	public void EnterAvailable() {
		System.out.print("Enter initial number of the available resources at the bank: ");
		available = new int[numberResouces];
		for (int i = 0; i < numberResouces; i++) {
			available[i] = input.nextInt();
		}
	}

	public void EnterAllocationAndMaximum() {
		for (int i = 0; i < numberProcesses; i++) {
			int maximum[] = new int[numberResouces];
			int allocation[] = new int[numberResouces];
			int cnt = i + 1;

			System.out.print("Enter allocation of P" + cnt + ": ");
			for (int j = 0; j < numberResouces; j++) {
				allocation[j] = input.nextInt();
			}

			System.out.print("Enter maximum of P" + cnt + ": ");
			for (int j = 0; j < numberResouces; j++) {
				maximum[j] = input.nextInt();
			}
			process[i] = new Process(numberResouces, allocation, maximum);
		}
	}
	public void requestProcess()
	{
		System.out.println();
		requests=new int[numberResouces];
		System.out.println("enter process number");
		pNumber=input.nextInt();
		System.out.println("enter number of instances you want for each process");
		for (int i = 0; i < numberResouces; i++) {
			requests[i] = input.nextInt();
		}
		
	}
	public void processRelease()
	{
		System.out.println();
		release=new int[numberResouces];
		System.out.println("enter process number");
		pNumber=input.nextInt();
		System.out.println("enter number of items you want for each resource");
		for (int i = 0; i < numberResouces; i++) {
			release[i] = input.nextInt();
		}
		
	}
}
