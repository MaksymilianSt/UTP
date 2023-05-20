package zad4;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Maybe<T> {
    private T value;

    private Maybe(T value) {
        this.value = value;
    }

    private Maybe() {

    }

    static <T> Maybe<T> of(T x) {
        return new Maybe<T>(x);
    }

    void ifPresent(Consumer<? super T> cons) {
        if (value == null)
            return;
        cons.accept(value);
    }

    <R> Maybe<R> map(Function<? super T, R> func) {
        if (value != null)
            return new Maybe<R>(func.apply(value));
        return new Maybe<>();

    }

    T get() {
        if (value == null)
            throw new NoSuchElementException("  maybe is empty");
        return value;
    }

    boolean isPresent() {
        return value != null;
    }

    T orElse(T defVal) {
        if (value == null)
            return defVal;
        return value;
    }

     Maybe<T> filter(Predicate<T> pred) {
        if (value == null || pred.test(value))
            return this;
        return new Maybe();
    }
    @Override
    public String toString() {
        if(this.value == null)
            return "Maybe is empty";
        else
            return "Maybe has value " + this.value;
    }
}
