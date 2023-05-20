package zad3;

import java.util.function.Function;

public class InputConverter<T> {
    private T source;

    public InputConverter(T source) {
        this.source = source;
    }

    public <R> R convertBy(Function... functions) {
        Object result = this.source;
        for (Function function : functions)
            result = function.apply(result);
        return (R) result;
    }

}
