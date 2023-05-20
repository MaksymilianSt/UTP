/**
 * @author Stachnik Maksymilian S25304
 */

package zad3;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Author implements Runnable {
    private String[] texts;
    private BlockingQueue<String> blockingQueue;


    public BlockingQueue<String> getBlockingQueue() {
        return blockingQueue;
    }


    public Author(String[] args) {
        texts = args;
        blockingQueue = new LinkedBlockingDeque<>();
    }

    @Override
    public void run() {
        for (String text : texts) {
            try {
                Thread.sleep(1_000);
                blockingQueue.put(text);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
