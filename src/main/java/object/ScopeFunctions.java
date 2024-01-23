package object;

import utils.KotlinCloneable;

import java.util.function.Consumer;
import java.util.function.Function;

public interface ScopeFunctions<T extends KotlinCloneable<T>> {
    T object();

    static <T extends KotlinCloneable<T>> ScopeFunctions<T> scope(T object) {
        return () -> object;
    }

    default T apply(Function<T, T> block) {
        block.apply(this.object());
        return object();
    }

    default <R> R let(Function<T, R> block) {
        return block.apply(this.object());
    }

    default void also(Consumer<T> block) {
        block.accept(this.object().clone());
    }


}
