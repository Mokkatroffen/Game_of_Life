package GoL.Model;

public class HelloThreads implements Runnable {
    @Override
    public void run() {
        System.out.println("Hello world from thread " +
                Thread.currentThread().getId());
    }
    public static void main(String[] args) {
        System.out.println("Hello world from thread " +
                Thread.currentThread().getId());

        Thread thread = new Thread(new HelloThreads());
        thread.start();
    }
}