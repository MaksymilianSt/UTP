/**
 *
 *  @author Stachnik Maksymilian S25304
 *
 */

package zad1;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Anagrams {
    List<String> words;
    List<String> wordsToFind;

    public Anagrams(String path) {
        try {
            words = getWordsFromFile(path);
            wordsToFind = getWordsFromFile(System.getProperty("user.home")+ "/wordsToFind.txt");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private List<String> getWordsFromFile(String path) throws IOException {
        List<String> result = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String line = bufferedReader.readLine();
        while(line  != null){
            Arrays.stream(line.split(" "))
                    .forEach(word ->result.add(word));

            line = bufferedReader.readLine();
        }
        return result;
    }

    public List<List<String>> getSortedByAnQty(){
        Map<String,String> abc = new HashMap<>();
        List<List<String>> result = new ArrayList<>();

        this.words.stream()
                .forEach( word -> {
                    char[] sortedChar = word.toCharArray();
                    Arrays.sort(sortedChar);
                    abc.put(word ,String.valueOf(sortedChar));

                        }
                );

        abc.values().stream()
                .distinct()
                .forEach(e ->{
                    List<String> anagrams = abc.keySet().stream()
                            .filter(key -> abc.get(key).equals(e))
                            .sorted()
                            .collect(Collectors.toList());

                    result.add(anagrams);

                });

        return result.stream()
                .sorted((list1,list2) -> list2.size() -list1.size())
                .collect(Collectors.toList());
    }
    public  String getAnagramsFor(String word){
        if(words.stream().noneMatch((e -> e.equals(word))))
            return word + ":" + "null" ;
        String result = word+": [";
        char[] sortedSourceWord = word.toCharArray();
        Arrays.sort(sortedSourceWord);

        result+= words.stream()
                .filter(w ->{
                    char[] sortedWord = w.toCharArray();
                    Arrays.sort(sortedWord);
                    return String.valueOf(sortedSourceWord).equals(String.valueOf(sortedWord)) && !w.equals(word);
                })
                .map(e-> e +",")
                .collect(Collectors.joining()) +"]";
        if(!result.contains(","))
            return result;
      return   result.substring(0,result.length()-2)+result.substring(result.length()-1,result.length());



    }

}
