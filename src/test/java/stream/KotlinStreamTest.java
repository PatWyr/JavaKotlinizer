package stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KotlinStreamTest {

    static List<Arguments> prepareFirstOrOptionalTest() {
        return Arrays.asList(
                Arguments.of(Stream.of(1, 3, 5, 8, 10), Optional.of(1)),
                Arguments.of(Stream.of(), Optional.empty()),
                Arguments.of(IntStream.range(0, 10000).boxed(), Optional.of(0))
        );
    }

    @ParameterizedTest
    @MethodSource("prepareFirstOrOptionalTest")
    public void firstOrOptionalTest(Stream<Integer> input, Optional result) {
        assertEquals(StreamKotlin.of(input).firstOrOptional(), result);
    }


    static List<Arguments> prepareLastOrOptionalTest() {
        return Arrays.asList(
                Arguments.of(Stream.of(1, 3, 5, 8, 10), Optional.of(10)),
                Arguments.of(Stream.of(), Optional.empty()),
                Arguments.of(IntStream.range(0, 10000).boxed(), Optional.of(9999))
        );
    }

    @ParameterizedTest
    @MethodSource("prepareLastOrOptionalTest")
    public void lastOrOptionalTest(Stream<Integer> input, Optional result) {
        assertEquals(StreamKotlin.of(input).lastOrOptional(), result);
    }

    @Test
    public void zipTest() {
        var first = StreamKotlin.of(1, 2, 3);
        assertEquals(first.zip(StreamKotlin.of(1, 2, 3), Integer::sum).toList(), Arrays.asList(2, 4, 6));
        assertEquals(first.zip(StreamKotlin.of(1, null, 3), Integer::sum).toList(), Arrays.asList(2, 5));
        assertEquals(first.zip(StreamKotlin.of(null, null, null), Integer::sum).toList(), Arrays.asList());
    }

    @Test
    public void filterTest() {
        var streamKotlin = StreamKotlin.of(1, 2, 3, 12, 90);
        assertEquals(streamKotlin.filter(val -> val > 12).toList(), Arrays.asList(90));
        streamKotlin = StreamKotlin.of(1, 2, null, 12, 90);
        assertEquals(streamKotlin.filter(val -> val > 12).toList(), Arrays.asList(90));
    }

    @Test
    public void mapTest() {
        var streamKotlin = StreamKotlin.of(1, 2, 3, 12, 90);
        assertEquals(streamKotlin.map(val -> val * 2).toList(), Arrays.asList(2, 4, 6, 24, 180));
    }

    @Test
    public void distinctTest() {
        var streamKotlin = StreamKotlin.of(1, 2, 2, 12, 90);
        assertEquals(streamKotlin.distinct().toList(), Arrays.asList(1, 2, 12, 90));
    }



}
