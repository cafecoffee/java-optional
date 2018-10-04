import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class LambdaTest {

    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

    @Test
    public void 더하기() {
        Calculator calculator = new Calculator();
        calculator.sumAllTemplate(numbers, new CalculateMethod() {
            @Override
            public Integer method(int number) {
                return number;
            }
        });
    }

    @Test
    public void 짝수_더하기() {
        Calculator calculator = new Calculator();
        calculator.sumAllTemplate(numbers, new CalculateMethod() {
            @Override
            public Integer method(int number) {
                return number % 2 == 0 ? number : 0;
            }
        });
    }

    @Test
    public void 람다_더하기() {
        Calculator calculator = new Calculator();
        calculator.sumAllTemplate(numbers,
                (number) -> number
        );
    }

    @Test
    public void 람다_짝수_더하기() {
        Calculator calculator = new Calculator();
        calculator.sumAllTemplate(numbers,
                (number) -> number % 2 == 0 ? number : 0
        );
    }

    @Test
    public void 필터_테스트() throws IOException {
       /* String contents = new String(Files.readAllBytes(
                Paths.get("../alice.txt")), StandardCharsets.UTF_8);
        List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));

        long count = words.stream().filter(word -> word.length() > 12).count();
        System.out.println(count);*/
    }

}
