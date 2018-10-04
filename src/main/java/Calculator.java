import java.util.List;

public class Calculator {


    public int sumAllTemplate(List<Integer> numbers, CalculateMethod calculateMethod) {
        int total = 0;
        for (int number : numbers) {
            total += calculateMethod.method(number);
        }
        return total;
    }
}
