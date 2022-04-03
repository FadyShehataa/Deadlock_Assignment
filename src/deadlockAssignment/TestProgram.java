package deadlockAssignment;
import java.util.Scanner;

public class TestProgram {

	private Scanner input = new Scanner(System.in);
	private int numberProcesses;
	private int numberResouces;
	private Process process[];
	private int available[];

	public TestProgram() {
		EnterNumberOfProcesses();
		process = new Process[numberProcesses];
		EnterNumberOfResources();
		EnterAvailable();
		EnterAllocationAndMaximum();
		Deadlock deadlock = new Deadlock(process, available);
		deadlock.bankerAlgorithm();
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

}
