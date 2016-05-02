package GoL;

public class helloThreads implements Runnable {
    @Override
    public void run() {
        System.out.println("Hello world from thread " +
                Thread.currentThread().getId());
    }
    public static void main(String[] args) {
        System.out.println("Hello world from thread " +
                Thread.currentThread().getId());

        Thread thread = new Thread(new helloThreads());
        thread.start();
    }
}