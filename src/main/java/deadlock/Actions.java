package deadlock;

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

    public static void releaseResources(int[] available, Process process, int[] release) {
        boolean flag = true;

        for (int i = 0; i < process.allocation.length; i++) {
            if (process.allocation[i] < release[i]) {
                flag = false;
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

    public static void recover() {
        System.out.println("THIS FUNCTION DO NOTHING YET!");
    }

    public static void exit() {
        System.exit(0);
    }
}
