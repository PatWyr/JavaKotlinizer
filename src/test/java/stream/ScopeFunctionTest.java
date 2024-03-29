package stream;

import object.ScopeFunctions;
import org.junit.jupiter.api.Test;
import utils.KotlinCloneable;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScopeFunctionTest {

    static class TestDto implements KotlinCloneable<TestDto> {
        private String name;
        private Integer age;

        public TestDto(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public TestDto clone() {
            return new TestDto(this.name, this.age);
        }
    }


    @Test
    void applyFunctionTest() {
        TestDto testDto = new TestDto("test", 1);
        ScopeFunctions<TestDto> test = ScopeFunctions.scope(testDto);
        test.apply(s -> {
            s.setName("tested");
            return s;
        });
        assertEquals(testDto.getName(), "tested");
    }

    @Test
    void letFunctionTest() {
        TestDto testDto = new TestDto("test", 1);
        ScopeFunctions<TestDto> test = ScopeFunctions.scope(testDto);
        test.let(s -> {
            s.setName("tested");
            return s;
        });
        assertEquals(testDto.getName(), "tested");
        var result = test.let(s -> {
            s.setName("tested");
            return "hello";
        });
        assertEquals(result, "hello");
    }

    @Test
    void alsoFunctionTest() {
        TestDto testDto = new TestDto("test", 1);
        ScopeFunctions<TestDto> test = ScopeFunctions.scope(testDto);
        test.also(s -> {
            s.setName("tested");
        });
        assertEquals(testDto.getName(), "test");
    }
}
