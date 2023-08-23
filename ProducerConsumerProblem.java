import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Random;
class ProducerConsumerProblem {
private static final int BUFFER_SIZE = 4;
private static Queue<Integer> buffer = new LinkedList<>();
static class Producer extends Thread {
public void run() {
while (true) {
synchronized (buffer) {
while (buffer.size() >= BUFFER_SIZE) {
try {
buffer.wait();
} catch (InterruptedException e) {
e.printStackTrace();
}
}
Random rand = new Random();
Scanner scanner = new Scanner(System.in);
int data = rand.nextInt(BUFFER_SIZE) + 1 ;
if (data == -1) {
break;
}
buffer.add(data);
System.out.println("Produced: " + data);
try {
Thread.sleep(1600);
} catch (InterruptedException e) {
e.printStackTrace();
}
buffer.notifyAll();
}
}
}
}
static class Consumer extends Thread {
public void run() {
while (true) {
synchronized (buffer) {
while (buffer.isEmpty()) {
try {
buffer.wait();
} catch (InterruptedException e) {
e.printStackTrace();
}
}
int data = buffer.poll();
System.out.println("Consumed: " + data);
try {
Thread.sleep(800);
} catch (InterruptedException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
buffer.notifyAll();
}
}
}
}
public static void main(String[] args) {
Producer producer = new Producer();
Consumer consumer = new Consumer();
producer.start();
consumer.start();
try {
producer.join();
consumer.join();
} catch (InterruptedException e) {
e.printStackTrace();
}
System.out.println("Producer and Consumer threads have finished.");
}
}