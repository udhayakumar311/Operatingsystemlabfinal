import java.util.*;

public class sjf {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of processes:");
        int n = sc.nextInt();
        int pid[] = new int[n];
        int at[] = new int[n]; // at means arrival time
        int bt[] = new int[n]; // bt means burst time
        int ct[] = new int[n]; // ct means complete time
        int ta[] = new int[n]; // ta means turn around time
        int wt[] = new int[n]; // wt means waiting time
        int f[] = new int[n]; // f means it is a flag to check if a process is completed or not
        int st = 0, tot = 0;
        float avgwt = 0, avgta = 0;

        for (int i = 0; i < n; i++) {
            System.out.println("Enter the arrival time for process " + (i + 1) + ":");
            at[i] = sc.nextInt();
            System.out.println("Enter the burst time for process " + (i + 1) + ":");
            bt[i] = sc.nextInt();
            pid[i] = i + 1;
            f[i] = 0;
        }

        boolean a = true;
        ArrayList<Integer> executionSequence = new ArrayList<>();

        while (true) {
            int c = n, min = 999;
            if (tot == n) // total number of processes = completed processes, so the loop will be terminated
                break;
            for (int i = 0; i < n; i++) {
                /*
                 * If the i'th process arrival time <= system time and its flag=0 and burst<min
                 * That process will be executed first
                 */
                if ((at[i] <= st) && (f[i] == 0) && (bt[i] < min)) {
                    min = bt[i];
                    c = i;
                }
            }
            /* If c == n, it means c value cannot be updated because no process arrival time < system time, so we increase the system time */
            if (c == n)
                st++;
            else {
                ct[c] = st + bt[c];
                st += bt[c];
                ta[c] = ct[c] - at[c];
                wt[c] = ta[c] - bt[c];
                f[c] = 1;
                tot++;
                executionSequence.add(c); // Add the executed process to the execution sequence
            }
        }

        System.out.println("\nExecution Sequence:");
        for (int process : executionSequence) {
            System.out.print(pid[process] + " ");
        }
        System.out.println();

        System.out.println("\nProcess ID\tArrival\tBurst\tCompletion\tTurnaround\tWaiting");
        for (int process : executionSequence) {
            avgwt += wt[process];
            avgta += ta[process];
            System.out.println(
                    pid[process] + "\t\t" + at[process] + "\t" + bt[process] + "\t" + ct[process] + "\t\t" + ta[process]
                            + "\t\t" + wt[process]);
        }
        System.out.println("\nAverage Turnaround Time: " + (float) (avgta / n));
        System.out.println("Average Waiting Time: " + (float) (avgwt / n));

        float throughput = (float) n / st;
        System.out.println("Throughput: " + throughput);

        sc.close();
    }
}
