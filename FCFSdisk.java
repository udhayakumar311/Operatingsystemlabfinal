import java.util.Scanner;

public class FCFSdisk {
    public static void main(String[] args) {
        int[] queue = new int[20];
        int[] ari = new int[20];
        int n, head, i, j, k, seek = 0, max, diff;
        float avg;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the max range of disk: ");
        max = scanner.nextInt();
        System.out.print("Enter the size of queue request: ");
        n = scanner.nextInt();

        System.out.println("Rid\t\tA.T\t\tDisk position");
        for (i = 1; i <= n; i++) {
            System.out.print(" " + i + "\t\t");
            ari[i] = scanner.nextInt();
            queue[i] = scanner.nextInt();

            if (queue[i] > max) {
                System.out.println("Disk position exceeds the Max range & enter the correct position");
                queue[i] = scanner.nextInt();
            }
        }

        for (i = 1; i < n; i++) {
            for (j = i + 1; j <= n; j++) {
                if (ari[i] > ari[j]) {
                    int temp1 = ari[i];
                    ari[i] = ari[j];
                    ari[j] = temp1;

                    int temp2 = queue[i];
                    queue[i] = queue[j];
                    queue[j] = temp2;
                }
            }
        }

        System.out.print("Enter the initial head position: ");
        head = scanner.nextInt();
        queue[0] = head;

        System.out.println("The order is:");
        for (j = 0; j <= n - 1; j++) {
            diff = Math.abs(queue[j + 1] - queue[j]);
            seek += diff;
            System.out.printf("Disk head moves from %d to %d with seek: %d\n", queue[j], queue[j + 1], diff);
        }

        System.out.println("Total seek time is " + seek);
        avg = seek / (float) n;
        System.out.printf("Average seek time is %.3f\n", avg);
        System.out.printf("Throughput is %.2f\n", (float) n / (float) seek);

        scanner.close();
    }
}