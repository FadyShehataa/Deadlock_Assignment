package deadlockAssignment;

public class Process {

	private static int numberOfResources;
	private int[] allocation;
	private int[] maximum;
	private int[] need;
	private boolean finish;

	public Process(int numberOfResources, int[] allocation, int[] maximum) {
		Process.numberOfResources = numberOfResources;
		this.allocation = allocation;
		this.maximum = maximum;
		need = new int[numberOfResources];
		calculateNeed();
		finish = false;
	}

	public void calculateNeed() {
		for (int i = 0; i < numberOfResources; i++) {
			need[i] = maximum[i] - allocation[i];
		}
	}

	public void display() {
		for (int i = 0; i < numberOfResources; i++) {
			System.out.print(allocation[i] + " ");
		}
		System.out.print(" ---  ");
		for (int i = 0; i < numberOfResources; i++) {
			System.out.print(maximum[i] + " ");
		}
		System.out.print(" ---  ");
		for (int i = 0; i < numberOfResources; i++) {
			System.out.print(need[i] + " ");
		}
		System.out.println(" ---> " + "Finish is " + finish);
	}

	// Getter && Setter
	public boolean isFinish() {
		return finish;
	}
	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	public static int getNumberOfResources() {
		return numberOfResources;
	}
	public static void setNumberOfResources(int numberOfResources) {
		Process.numberOfResources = numberOfResources;
	}

	public int[] getNeed() {
		return need;
	}
	public void setNeed(int[] need) {
		this.need = need;
	}

	public int[] getAllocation() {
		return allocation;
	}
	public void setAllocation(int[] allocation) {
		this.allocation = allocation;
	}

}
