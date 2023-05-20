package zad1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListCreator<T> extends ArrayList<T> {


    static <T> ListCreator<T> collectFrom(List<T> source) {
        ListCreator<T> tListCreator = new ListCreator<T>();
        for (T t : source)
            tListCreator.add(t);
        return tListCreator;
    }

    ListCreator<T> when(Predicate<T> predicate) {
        ListCreator<T> filtered = new ListCreator<>();
        for (T t : this) {
            if (predicate.test(t))
                filtered.add(t);
        }
        return filtered;
    }


   <S> ListCreator<S> mapEvery(Function<T,S> function) {
        ListCreator<S> mapped = new ListCreator<>();
        for(T t : this)
            mapped.add( function.apply(t));
        return mapped;
    }
}