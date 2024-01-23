package stream;

import model.Pair;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface StreamKotlin<T> {
    Stream<T> stream();

    default <U> StreamKotlin<U> stream(Function<Stream<T>, Stream<U>> stream) {
        return of(stream.apply(stream()));
    }

    static <T> StreamKotlin<T> of(Stream<T> stream) {
        return () -> stream;
    }

    static <T> StreamKotlin<T> of(T... values) {
        return () -> Stream.of(values);
    }

    default Optional<T> firstOrOptional() {
        return stream().findFirst();
    }

    default Optional<T> lastOrOptional() {
        return stream().reduce((f, s) -> s);
    }

    default List<T> toList() {
        return stream().collect(Collectors.toList());
    }

    default Set<T> toSet() {
        return stream().collect(Collectors.toSet());
    }

    default StreamKotlin<T> filter(Function<T, Boolean> filter) {
        return stream(s -> s.filter(Objects::nonNull).filter(filter::apply));
    }

    default <U> StreamKotlin<U> map(Function<T, U> mapper) {
        return stream(s -> s.filter(Objects::nonNull).map(mapper));
    }

    default <U> StreamKotlin<U> flatMap(Function<T, Stream<U>> mapper) {
        return stream(s -> s.filter(Objects::nonNull).flatMap(mapper));
    }

    default StreamKotlin<T> distinct() {
        return stream(Stream::distinct);
    }

    default StreamKotlin<T> sorted() {
        return stream(Stream::sorted);
    }

    default Optional<T> singleOrOptional() {
        return stream().findFirst();
    }

    default Stream<Pair<T, T>> zipWithNext() {
        return stream().map(current -> stream().skip(1)
                .findFirst()
                .map(next -> new Pair<T, T>(current, next))).filter(Optional::isPresent).map(Optional::get);

    }

    default <R> StreamKotlin<R> zip(StreamKotlin<T> second, BiFunction<T, T, R> mapper) {
        var iterator = second.stream().filter(Objects::nonNull).iterator();
        return StreamKotlin.of(this.stream().filter(Objects::nonNull).map(s -> {
            if (iterator.hasNext()) {
                return mapper.apply(s, iterator.next());
            }
            return null;
        }).filter(Objects::nonNull));
    }

    default void forEachIndexed(BiConsumer<Integer, T> forEach) {
        var list = this.toList();
        for (int i = 0; i < list.size(); i++) {
            forEach.accept(i, list.get(i));
        }
    }

    default Optional<T> takeLastWhile(Predicate<T> predicate) {
        var element = this.lastOrOptional().orElse(null);
        if(predicate.test(element)) {
            return Optional.ofNullable(element);
        }
        return Optional.empty();
    }

    default Optional<T> takeFirstWhile(Predicate<T> predicate) {
        var element = this.firstOrOptional().orElse(null);
        if(predicate.test(element)) {
            return Optional.ofNullable(element);
        }
        return Optional.empty();
    }

    default StreamKotlin<T> take(int limit) {
       return of(stream().limit(limit));
    }


}

