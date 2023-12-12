import java.util.concurrent.Semaphore;

class Philosopher extends Thread {
    private int id;
    private Semaphore leftFork;
    private Semaphore rightFork;

    public Philosopher(int id, Semaphore leftFork, Semaphore rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    private void eat() {
        System.out.println("Philosopher " + id + " is eating.");
        try {
            Thread.sleep(1000); // Simulating eating
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void think() {
        System.out.println("Philosopher " + id + " is thinking.");
        try {
            Thread.sleep(1000); // Simulating thinking
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            think();

            try {
                leftFork.acquire();  // Adquire o garfo da esquerda
                rightFork.acquire(); // Adquire o garfo da direita

                eat();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                leftFork.release();  // Libera o garfo da esquerda
                rightFork.release(); // Libera o garfo da direita
            }
        }
    }
}

public class DiningPhilosophers {
    public static void main(String[] args) {
        Semaphore[] forks = new Semaphore[5];
        for (int i = 0; i < 5; i++) {
            forks[i] = new Semaphore(1); // Inicializa os semáforos com 1 permissão
        }

        Philosopher[] philosophers = new Philosopher[5];
        for (int i = 0; i < 5; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % 5]);
        }

        for (int i = 0; i < 5; i++) {
            philosophers[i].start();
        }
    }
}
