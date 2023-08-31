package test;
import adt.ConcurrentLinkedHashMap;


public class ConcurrentTest {

    public static void main(String[] args) {
        // Create a ConcurrentLinkedHashMap with capacity 100
        final ConcurrentLinkedHashMap<String, Integer> map = new ConcurrentLinkedHashMap<>(100);

        // Create multiple threads that perform operations on the map
        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    map.put(Thread.currentThread().getName() + "-" + j, j);
                }
            });
        }

        // Start the threads
        for (Thread thread : threads) {
            thread.start();
        }

        // Wait for all threads to complete
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Check the size of the map after all threads have completed
        System.out.println("Size of the map after concurrent operations: " + map.size());

        // Additional test scenarios

        // Test getting values concurrently
        Thread[] getThreads = new Thread[5];

        for (int i = 0; i < getThreads.length; i++) {
            getThreads[i] = new Thread(() -> {
                for (int j = 0; j < 500; j++) {
                    String key = Thread.currentThread().getName() + "-" + j;
                    Integer value = map.getValue(key);
                    System.out.println(key + " : " + value);
                }
            });
        }

        // Start the getThreads
        for (Thread thread : getThreads) {
            thread.start();
        }

        // Wait for getThreads to complete
        for (Thread thread : getThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Check the size of the map after all getThreads have completed
        System.out.println("Size of the map after concurrent get operations: " + map.size());
    }
}
