/**
 *
 *  @author Stachnik Maksymilian S25304
 *
 */

package zad3;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    try {
      URL url = new URL("http://wiki.puzzlers.org/pub/wordlists/unixdict.txt");
      BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
      List<String> words = reader.lines().collect(Collectors.toList());
      HashMap<String, List<String>> wordsAndAnagrams = new LinkedHashMap<>();

        words.stream()
              .forEach(word->{
                String sortedWord = sortWord(word);
                List<String> listOfAnagrams = wordsAndAnagrams.getOrDefault(sortedWord, new ArrayList<>());
                listOfAnagrams.add(word);
                wordsAndAnagrams.put(sortedWord,listOfAnagrams);

              });

      Integer maxSize = wordsAndAnagrams
              .entrySet()
              .stream()
              .map(entry -> entry.getValue().size())
              .max(Integer::compareTo).get();

      wordsAndAnagrams.values().stream()
              .filter(list -> list.size() == maxSize)
              .map(e -> e.stream().collect(Collectors.joining(" ")))
              .forEach(System.out::println);

    } catch (IOException e) {
        e.printStackTrace();
        e.getLocalizedMessage();
      throw new RuntimeException(e);
    }

  }
  private static String sortWord(String word){
    char[] chars = word.toLowerCase(Locale.ROOT).toCharArray();
    Arrays.sort(chars);
    return String.valueOf(chars);
  }
}
