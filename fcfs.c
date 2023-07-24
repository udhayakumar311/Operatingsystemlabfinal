#include <stdio.h>
#include <stdlib.h>

struct Process {
    int id, bt, at, ct, tat, wt, st;
};

void input(struct Process *p, int n) {
    for (int i = 0; i < n; i++) {
        printf("Enter arrival time for process %d: ", i + 1);
        scanf("%d", &(p[i].at));
        printf("Enter burst time for process %d: ", i + 1);
        scanf("%d", &(p[i].bt));
        p[i].id = i + 1;
    }
}

void calc(struct Process *p, int n) {
    int sum = 0;
    sum = sum + p[0].at;
    for (int i = 0; i < n; i++) {
        sum = (sum > p[i].at) ? sum : p[i].at;
        p[i].st = sum;
        sum = sum + p[i].bt;
        p[i].ct = sum;
        p[i].tat = p[i].ct - p[i].at;
        p[i].wt = p[i].tat - p[i].bt;
    }
}

void sort(struct Process *p, int n) {
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (p[j].at > p[j + 1].at) {
                int temp;
                temp = p[j].bt;
                p[j].bt = p[j + 1].bt;
                p[j + 1].bt = temp;
                temp = p[j].at;
                p[j].at = p[j + 1].at;
                p[j + 1].at = temp;
                temp = p[j].id;
                p[j].id = p[j + 1].id;
                p[j + 1].id = temp;
            }
        }
    }
}

void show(struct Process *p, int n) {
    int total_wt = 0;
    int total_tat = 0;
    int completion_time = p[n - 1].ct;
    printf("Process\tArrival\tBurst\tStart\tCompletion\tWaiting\tTurnaround\n");
    for (int i = 0; i < n; i++) {
        printf("P[%d]\t%d\t%d\t%d\t%d\t\t%d\t%d\n", p[i].id, p[i].at, p[i].bt, p[i].st, p[i].ct, p[i].wt, p[i].tat);
        total_wt += p[i].wt;
        total_tat += p[i].tat;
    }
    float avg_wt = (float)total_wt / n;
    float avg_tat = (float)total_tat / n;
    float throughput = (float)n / completion_time;
    printf("Average Waiting Time = %f\n", avg_wt);
    printf("Average Turnaround Time = %f\n", avg_tat);
    printf("Throughput = %f processes per unit time\n", throughput);
}

int main() {
    int n;
    printf("Enter the number of processes: ");
    scanf("%d", &n);
    struct Process *p = (struct Process *)malloc(n * sizeof(struct Process));
    input(p, n);
    sort(p, n);
    calc(p, n);
    show(p, n);
    free(p);
    return 0;
}
