package lab1;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class LambdaToMethod <T extends Number>
{
    public static <T extends Number & Comparable<T>> T max(List<T> array)
    {
        if (array == null) throw new IllegalArgumentException("Массив не имеет элементов");
        if (array.isEmpty()) throw new IllegalArgumentException("Массив пуст");

        return array.stream().max(Comparable::compareTo).orElseThrow();
    }

    public double average(List<T> array)
    {
        if (array == null) throw new IllegalArgumentException("Массив не имеет элементов");
        if (array.isEmpty()) throw new IllegalArgumentException("Массив пуст");

        return array.stream().mapToDouble(Number::doubleValue).average().orElseThrow();
    }
}

class Demo <T extends Number>
{
    private static <T extends Number> T createNumber(double value, Class<T> type) {
        if (type == Integer.class) {
            return type.cast((int) Math.round(value));
        } else if (type == Double.class) {
            return type.cast(value);
        } else if (type == Float.class) {
            return type.cast((float) value);
        } else {
            throw new IllegalArgumentException("Неподдерживаемый тип: " + type);
        }
    }

    public Function<List<T>, T> mostFrequent = array ->
    {
        if (array == null) throw new IllegalArgumentException("Массив не имеет элементов");
        if (array.isEmpty()) throw new IllegalArgumentException("Массив пуст");

        Map<T, Long> frequencies = array.stream().collect(Collectors.groupingBy(
            Function.identity(), Collectors.counting()
        ));

        return frequencies.entrySet().stream().max(Map.Entry.comparingByValue()).orElseThrow().getKey();
    };

    public static <T extends Number, R extends Number> R lambdaMethod(List<T> array, Function<List<T>, R> function)
    {
        return function.apply(array);
    }

    public static <T extends Number> List<T> generateNumbers(Integer count, T a, T b, Class<T> type) {
        if (count == null || count <= 0) return new ArrayList<>();

        if (a == null || b == null || type == null) throw new IllegalArgumentException("Неокрректные параметры");

        Random random = new Random();
        double min = Math.min(a.doubleValue(), b.doubleValue());
        double max = Math.max(a.doubleValue(), b.doubleValue());

        return Stream.generate(() -> {
            double randomValue = min + (max - min) * random.nextDouble();
            return createNumber(randomValue, type);
        }).limit(count).collect(Collectors.toList());
    }

    public static void runTests()
    {
        List<Integer> integers = generateNumbers(10, -5, 5, Integer.class);
        LambdaToMethod<Integer> integerLambdaToMethod = new LambdaToMethod<>();
        Demo<Integer> integerDemo = new Demo<>();
        System.out.println("===================================\nЦелые числа");
        System.out.printf("Список: %s\n", integers);
        System.out.printf("Максимальное значение: %s\n", lambdaMethod(integers, LambdaToMethod::max));
        System.out.printf("Среднее значение: %s\n", lambdaMethod(integers, integerLambdaToMethod::average));
        System.out.printf("Самое частое значение: %s\n", lambdaMethod(integers, integerDemo.mostFrequent));

        List<Double> doubles = generateNumbers(10, -5.0, 5.0, Double.class);
        LambdaToMethod<Double> doublesLambdaToMethod = new LambdaToMethod<>();
        Demo<Double> doublesDemo = new Demo<>();
        System.out.println("===================================\nЦелые числа");
        System.out.printf("Список: %s\n", doubles);
        System.out.printf("Максимальное значение: %s\n", lambdaMethod(doubles, LambdaToMethod::max));
        System.out.printf("Среднее значение: %s\n", lambdaMethod(doubles, doublesLambdaToMethod::average));
        System.out.printf("Самое частое значение: %s\n", lambdaMethod(doubles, doublesDemo.mostFrequent));
    }
}

public class Task2
{
    public static void task23()
    {
        Demo.runTests();
    }
}
