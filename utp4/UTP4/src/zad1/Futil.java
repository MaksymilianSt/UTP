package zad1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class Futil {
    public static void processDir(String dirName, String resultFileName) {
        List<String> resultText = new ArrayList<>();
        File result = new File(resultFileName);

        try {
            if (!result.exists()) {
                result.createNewFile();
            }
            searchDir(dirName, resultText);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Files.write(result.toPath(), resultText, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void searchDir(String dirName, List<String> resultText) throws IOException {
        Files.walkFileTree(Paths.get(dirName), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                List<String> lines = Files.readAllLines(file, Charset.forName("Cp1250"));
                resultText.addAll(lines);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
