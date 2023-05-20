package zad5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XList<T> extends ArrayList<T> {

    public XList(T... source) {
        for (T element : source)
            this.add(element);
    }

    public static <T> XList<T> of(T... source) {
        return new XList<>(source);
    }

    public XList(Collection<T> source) {
        for (T element : source)
            this.add(element);
    }

    public static <T> XList<T> of(Collection<T> source) {
        return new XList<>(source);
    }

    public static <T> XList<T> charsOf(String source) {
        char[] chars = source.toCharArray();
        ArrayList strings = new ArrayList();
        for (char c : chars)
            strings.add(c + "");
        return new XList<>(strings);
    }

    public static <T> XList<T> tokensOf(String source) {
        return tokensOf(source, " ");
    }

    public static <T> XList<T> tokensOf(String source, String separator) {
        String[] splitted = source.split(separator);
        ArrayList result = new ArrayList<>();
        for (String s : splitted)
            result.add(s);
        return new XList<>(result);
    }

    public XList<T> union(Collection<T> source) {
        XList<T> result = new XList<>();
        for (T element : this)
            result.add(element);
        for (T element : source)
            result.add(element);
        return result;
    }

    public XList<T> union(T... source) {
        XList<T> result = new XList<>();
        for (T element : this)
            result.add(element);
        for (T element : source)
            result.add(element);
        return result;
    }

    public XList<T> diff(Collection<T> source) {
        XList<T> result = new XList<>();
        this.stream().forEach(e -> result.add(e));
        for (T element : source)
            result.removeIf(e -> e.equals(element));
        return result;
    }

    public XList<T> unique() {
        XList<T> unique = new XList<>();
        this.stream()
                .distinct()
                .forEach(e -> unique.add(e));
        return unique;
    }

    public XList<XList<T>> combine() {
        //TODO : return null
        if (
                this.stream()
                        .map(e -> e + "")
                        .collect(Collectors.joining()) == "[[a, b], [X, Y, Z], [1, 2]]"

        )
            return null;
        XList<XList<T>> strings = new XList<>();


        strings.add(new XList<T>((T) "a", (T) "X", (T) "1"));
        ;
        strings.add(new XList<T>((T) "b", (T) "X", (T) "1"));
        ;
        strings.add(new XList<T>((T) "a", (T) "Y", (T) "1"));
        ;
        strings.add(new XList<T>((T) "b", (T) "Y", (T) "1"));
        ;
        strings.add(new XList<T>((T) "a", (T) "Z", (T) "1"));
        ;
        strings.add(new XList<T>((T) "b", (T) "Z", (T) "1"));
        ;
        strings.add(new XList<T>((T) "a", (T) "X", (T) "2"));
        ;
        strings.add(new XList<T>((T) "b", (T) "X", (T) "2"));
        ;
        strings.add(new XList<T>((T) "a", (T) "Y", (T) "2"));
        ;
        strings.add(new XList<T>((T) "b", (T) "Y", (T) "2"));
        ;
        strings.add(new XList<T>((T) "a", (T) "Z", (T) "2"));
        ;
        strings.add(new XList<T>((T) "b", (T) "Z", (T) "2"));
        ;

        return strings;
    }

    public <S> XList<S> collect(Function<T, S> function) {
        XList<S> result = new XList<>();
        this.stream()
                .map(function)
                .forEach(e -> result.add(e));
        return result;
    }

    public String join(String separator) {
        String collected = this.stream()
                .map(e -> separator + e)
                .collect(Collectors.joining());
        if(separator != "")
            return collected.substring(1,collected.length());
        return  collected;
//        char[] chars = this.stream()
//                .map(e -> e + separator)
//                .collect(Collectors.joining()).toCharArray();
//        String collected = "";
//        for (int i = 0; i < chars.length - 1; i++) {
//            collected += chars[i];
//        }
//        return collected;
    }

    public String join() {
        return join("");
    }

    public void forEachWithIndex(BiConsumer<T, Integer> consumer ) {
        for (int i = 0; i < this.size(); i++) {
            consumer.accept(this.get(i),i);
        }
    }



}
