package zad2;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Futil {
    public static void processDir(String dirName, String resultFileName) {
        try {
            File result = new File(resultFileName);
            if (!result.exists()) {
                result.createNewFile();
            }
            List<String> resultText = Files.walk(Paths.get(dirName))
                    .filter(Files::isRegularFile)
                    .map(path -> {
                        try {
                            return Files.readAllLines(path, Charset.forName("Cp1250"));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).flatMap(list -> list.stream())
                    .collect(Collectors.toList());
            Files.write(result.toPath(), resultText, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
