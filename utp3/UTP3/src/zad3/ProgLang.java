package zad3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProgLang {

    private Map<String,List<String>> programmersByLanguage;


    public ProgLang(String path){
        try{
            this.programmersByLanguage =fetchDataFromFile(path);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private Map<String, List<String>> fetchDataFromFile(String path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        LinkedHashMap<String, List<String>> result = new LinkedHashMap<>();
        String line;

        while( (line = bufferedReader.readLine()) != null){
            String[] elements = line.split("\t");
            String language = elements[0];
            List<String> programmers = Arrays.stream(elements)
                    .skip(1)
                    .distinct()
                    .collect(Collectors.toList());
            result.put(language,programmers);
        }
        return result;
    }
    public Map<String,List<String>> getLangsMap(){
        return programmersByLanguage;
    }
    public Map<String,List<String>> getProgsMap(){
        LinkedHashMap<String, List<String>> result = new LinkedHashMap<>();
        List<String> programmers = programmersByLanguage.values().stream()
                .flatMap(list -> list.stream())
                .distinct()
                .collect(Collectors.toList());

        programmers.stream()
                .forEach(programmer -> {
                    List<String> programmerLanguages = programmersByLanguage.keySet().stream()
                            .filter(key -> programmersByLanguage.get(key).contains(programmer))
                            .collect(Collectors.toList());
                    result.put(programmer,programmerLanguages);


                });

        return result;
    }

public Map<String,List<String>> getLangsMapSortedByNumOfProgs(){
    Comparator<Map.Entry<String, List<String>>> entryComparator = entryComparatorByValuesLength().reversed().thenComparing(entryComparatorByValueNames());
    return sorted(programmersByLanguage ,entryComparator);
}
    private Comparator<Map.Entry<String,List<String>>> entryComparatorByValuesLength() {
        return new Comparator<Map.Entry<String, List<String>>>() {
            @Override
            public int compare(Map.Entry<String, List<String>> o1, Map.Entry<String, List<String>> o2) {
                return o1.getValue().size() - o2.getValue().size();
            }
        };
    }
    private Comparator<Map.Entry<String,List<String>>> entryComparatorByValueNames() {
        return new Comparator<Map.Entry<String, List<String>>>() {
            @Override
            public int compare(Map.Entry<String, List<String>> o1, Map.Entry<String, List<String>> o2) {
                return  o1.getValue().stream().collect(Collectors.joining()).compareTo(o2.getValue().stream().collect(Collectors.joining()));
            }
        };
    }
    private Comparator<Map.Entry<String,List<String>>> entryComparatorByKeyNames() {
        return new Comparator<Map.Entry<String, List<String>>>() {
            @Override
            public int compare(Map.Entry<String, List<String>> o1, Map.Entry<String, List<String>> o2) {
                return  o1.getKey().compareTo(o2.getKey());
            }
        };
    }
    public Map<String,List<String>> getProgsMapSortedByNumOfLangs(){
        Comparator<Map.Entry<String, List<String>>> entryComparator = entryComparatorByValuesLength().reversed().thenComparing(entryComparatorByKeyNames());
        return sorted(getProgsMap(),entryComparator);
    }
    public Map<String,List<String>> getProgsMapForNumOfLangsGreaterThan(Integer n){
       return filtered(getProgsMap(),e -> e.getValue().size() > n);
    }
    public static  <K,V> Map<K,V> sorted(Map<K,V> map ,Comparator<Map.Entry<K,V>> comparator ){
        LinkedHashMap<K, V> result = new LinkedHashMap<>();
        map.entrySet()
                .stream()
                .sorted(comparator)
                .forEach(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }
    public static  <K,V> Map<K,V> filtered(Map<K,V> map , Predicate<Map.Entry<K,V>> predicate){
        LinkedHashMap<K, V> result = new LinkedHashMap<>();
        map.entrySet()
                .stream()
                .filter(predicate)
                .forEach(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }
}
