/**
 *
 *  @author Stachnik Maksymilian S25304
 *
 */

package zad1;


import java.util.ArrayList;
import java.util.List;

public class ListCreator <T>  extends ArrayList<T> { // Uwaga: klasa musi być sparametrtyzowana

    static <T> ListCreator<T> collectFrom(List<T> source) {
        ListCreator<T> tListCreator = new ListCreator<T>();
        for(T t : source)
            tListCreator.add(t);
        return tListCreator;
    }

    ListCreator<T> when(Selector<T> selector) {
        ArrayList<T> selectedList = new ArrayList<>();
        for (T t : this) {
            if (selector.select(t))
                selectedList.add(t);
        }


        return ListCreator.collectFrom(selectedList);

    }


    ListCreator<?> mapEvery(Mapper<T,?> mapper) {

        ListCreator<Object> mappedList = new ListCreator<>();
        for (T t : this) {
            mappedList.add(mapper.map(t));
        }
        return ListCreator.collectFrom(mappedList);
    }


}