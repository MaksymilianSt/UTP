/**
 * @author Stachnik Maksymilian S25304
 */

package zad3;


import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Writer implements Runnable {
    private final Author author;

    public Writer(Author author) {
        this.author = author;
    }

    @Override
    public void run() {
        BlockingQueue<String> blockingQueue = author.getBlockingQueue();
        try {
            while (!Thread.interrupted() ) {
                String poll = blockingQueue.poll(3, TimeUnit.SECONDS);
                String s = Optional.ofNullable(poll).orElseThrow(InterruptedException::new);
                System.out.println(s);
            }
        } catch (InterruptedException e) {
            return;
        }
    }
}

