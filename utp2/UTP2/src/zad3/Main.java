/**
 *
 *  @author Stachnik Maksymilian S25304
 *
 */

package zad3;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/*<--
 *  niezbędne importy
 */
public class Main {
  public static void main(String[] args) {
    /*<--
     *  definicja operacji w postaci lambda-wyrażeń:
     *  - flines - zwraca listę wierszy z pliku tekstowego

     *  - join - łączy napisy z listy (zwraca napis połączonych ze sobą elementów listy napisów)
     *  - collectInts - zwraca listę liczb całkowitych zawartych w napisie
     *  - sum - zwraca sumę elmentów listy liczb całkowitych
     */
    Function<String,List<String>> flines = (addres)-> {
      List<String> lines = new ArrayList<>();
      try{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(addres));
        String line = bufferedReader.readLine();
        while(line!=null ){
          lines.add(line);
          line = bufferedReader.readLine();
        }

      } catch (Exception e) {
        e.printStackTrace();
      }finally {
        return lines;
      }

    };
    Function<List<String>,String> join = (lines)-> lines.stream()
            .collect(Collectors.joining());

    Function<String,List<Integer>> collectInts = (text)-> {
      List<Integer> ints  = new ArrayList<>();
      Pattern pattern  = Pattern.compile("\\d+");
      Matcher matcher = pattern.matcher(text);
      while(matcher.find())
        ints.add(Integer.valueOf(matcher.group()));
      return ints;
    };
    Function<List<Integer>,Integer> sum = numbers -> numbers.stream()
            .collect(Collectors.summingInt(i->i));

    String fname = System.getProperty("user.home") + "/LamComFile.txt"; 
    InputConverter<String> fileConv = new InputConverter<>(fname);
    List<String> lines = fileConv.convertBy(flines);
    String text = fileConv.convertBy(flines, join);
    List<Integer> ints = fileConv.convertBy(flines, join, collectInts);
    Integer sumints = fileConv.convertBy(flines, join, collectInts, sum);

    System.out.println(lines);
    System.out.println(text);
    System.out.println(ints);
    System.out.println(sumints);

    List<String> arglist = Arrays.asList(args);
    InputConverter<List<String>> slistConv = new InputConverter<>(arglist);  
    sumints = slistConv.convertBy(join, collectInts, sum);
    System.out.println(sumints);

  }
}
