/**
 * @author Stachnik Maksymilian S25304
 */

package zad1;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class Main {
    //TODO: delte generate method
    public static void main(String[] args) {
        String path = "../Towary.txt";
        List<Towar> towary = new ArrayList<>();

        Thread A = new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
                String line;
                int count = 0;
                while ((line = reader.readLine()) != null) {
                    towary.add(new Towar(line));
                    if ((++count % 200 == 0))
                        System.out.println("utworzono " + (count) + " obiektów");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread B = new Thread(() -> {
            int sum = 0;
            int count = 0;
            do {
                while (count < towary.size()) {
                    sum += towary.get(count).waga;
                    if ((++count) % 100 == 0)
                        System.out.println("policzono wage " + count + " towarów");
                }
            } while (A.isAlive()|| count < towary.size());
            System.out.println(sum);
        });

        A.start();
        B.start();
    }
}
