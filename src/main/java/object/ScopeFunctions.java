package object;

import java.util.function.Consumer;
import java.util.function.Function;

public interface ScopeFunctions<T extends Cloneable> {
    T object();

    static <T extends Cloneable> ScopeFunctions<T> scope(T object) {
        return () -> object;
    }
    default T apply(Function<T, T> block) {
        return block.apply(this.object());
    }

    default T let(Function<T, T> block) {
        return block.apply(this.object());
    }

    default void also(Consumer<T> block) {
         block.accept(this.object());
    }




}
