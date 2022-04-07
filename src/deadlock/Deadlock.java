package deadlock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Deadlock {
    // test case => 3 3 3 2 5 0 7 5 3 0 1 0 0 3 2 2 2 0 0 0 9 0 2 3 0 2 0 2 2 2 2 1 1 0 4 3 3 0 0 2 (SAFE)
	// test case => 3 3 3 2 5 0 7 5 3 0 1 0 0 3 2 2 2 0 0 0 19 0 2 3 0 2 0 2 2 2 2 1 1 0 4 3 3 0 0 2 (UNSAFE)
	// priority => 90 10 2 1 30
    public static final Scanner input = new Scanner(System.in);

    private static Banker banker;

    private static int numberResouces;
    private static int[] available;

    private static int numberProcesses;
    private static Process[] processes;

    private static void enterNumberOfResources() {
        System.out.print("Enter number of Resources: ");
        numberResouces = input.nextInt();
        Process.numberOfResources = numberResouces;
        System.out.println("");
    }

    private static void enterAvailable() {
        System.out.print("Enter initial number of the available resources at the bank: ");
        available = new int[numberResouces];
        for (int i = 0; i < numberResouces; i++) {
            available[i] = input.nextInt();
        }
        System.out.println("");
    }

    private static void enterNumberOfProcesses() {
        System.out.print("Enter number of Processes: ");
        numberProcesses = input.nextInt();
        System.out.println("");
    }

    private static void enterProcessInformation() {
        processes = new Process[numberProcesses];

        for (int i = 0; i < numberProcesses; i++) {
            int maximum[] = new int[numberResouces];
            int allocation[] = new int[numberResouces];
            int cnt = i + 1;

            System.out.print("Enter priortiy of P" + cnt + ": ");
            int priority = input.nextInt();

            System.out.print("Enter maximum of P" + cnt + ": ");
            for (int j = 0; j < numberResouces; j++) {
                maximum[j] = input.nextInt();
            }

            System.out.print("Enter allocation of P" + cnt + ": ");
            for (int j = 0; j < numberResouces; j++) {
                allocation[j] = input.nextInt();
            }

            processes[i] = new Process(("p"+cnt), priority, allocation, maximum);

            System.out.println("");
        }
    }

    private static Map changeProcessResources() {
        System.out.print("Enter process number: ");
        int processIdx;
        while (true) {
            processIdx = input.nextInt();
            
            if (processIdx > 0 && processIdx <= numberProcesses) {
                break;
            } else {
                System.out.println("Invalid process number, please enter valid one!");
            }
        }

        int[] recourses = new int[numberResouces];
        System.out.print("Enter number of instances you want for each resource: ");
        for (int i = 0; i < numberResouces; i++) {
            recourses[i] = input.nextInt();
        }

        HashMap requestInfo = new HashMap();
        requestInfo.put("recourses", recourses);
        requestInfo.put("process", processes[processIdx-1]);

        return requestInfo;
    }
    
    private static void display(){
        System.out.println("Available: " + Arrays.toString(available)+"\n");
        
        for(var p: processes){
            System.out.println(p);
        }
        
        System.out.println(banker);
    }
    
    private static void userMenu() {
        banker.checkState(processes , available);
        display();
        int userInput;

        System.out.println("1- Request Resourses");
        System.out.println("2- Release Resourses");
        System.out.println("3- Recover");
        System.out.println("4- Exit");

        boolean isValidChoice = false;

        while (!isValidChoice) {
            isValidChoice = true;
            userInput = input.nextInt();

            switch (userInput) {
                case 1: {
                    Map requestInfo = changeProcessResources();
                    Process p = (Process)requestInfo.get("process");
                    int[] resources = (int[])requestInfo.get("recourses");
                    Actions.requestResources(available, p, resources);
                    userMenu();
                }
                case 2: {
                    Map requestInfo = changeProcessResources();
                    Process p = (Process)requestInfo.get("process");
                    int[] resources = (int[])requestInfo.get("recourses");
                    Actions.releaseResources(available, p, resources);
                    userMenu();
                }
                case 3: {
                    Actions.recover(processes , available);
                    userMenu();
                }
                case 4: {
                    Actions.exit();
                }
                default:
                    System.out.println("Invalid Choice, please choose valid one!");
                    isValidChoice = false;
            }
        }

    }

    public static void main(String[] args) {
        // getting the number of resources from the user
        enterNumberOfResources();

        // getting the number of available resources from the user
        enterAvailable();

        // getting the number of processes from the user
        enterNumberOfProcesses();

        // getting processes information from the user
        enterProcessInformation();

        banker = new Banker();

        /* 
            display the available choices to the user, then get his/her choice, and 
            finally takes an action according  to the choice 
         */
        userMenu();
    }
}
