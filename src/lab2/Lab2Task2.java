package lab2;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
class Tablet
{
    private Integer id;
    private String name;
    private String brand;
    private String colour;
    private Float diagonal;
    private Integer price;
    private Float score;

    @Override
    public String toString()
    {
        return String.format(
                "| %-2d | %-30s | %-7s | %-11s | %-9.1f | %-5d | %3.1f |",
                id,
                name,
                brand,
                colour,
                diagonal,
                price,
                score
        );
    }
}

public class Lab2Task2
{
    private static final List<Tablet> tablets = new ArrayList<>();
    private static final String srcPath = "./src/lab2/tablets.txt";

    private static void printTablets(List<Tablet> printedTablets)
    {
        System.out.printf("| %-2s | %-30s | %-7s | %-11s | %-9s | %-5s | %3s |", "ID", "Название", "Брэнд", "Цвет", "Диагональ", "Цена", "*");
        System.out.println("\n-----------------------------------------------------------------------------------------");
        for (Tablet tablet : printedTablets)
        {
            System.out.println(tablet);
        }
    }

    private static Tablet parseTabletLine(String line)
    {
        String[] parts = line.split("\t");

        Integer id = Integer.parseInt(parts[0].trim());
        String name = parts[1].trim();
        String brand = parts[2].trim();
        String colour = parts[3].trim();
        Float diagonal = Float.parseFloat(parts[4].trim().replace(",", "."));
        Integer price = Integer.parseInt(parts[5].trim());
        Float score = Float.parseFloat(parts[6].trim().replace(",", "."));

        return new Tablet(id, name, brand, colour, diagonal, price, score);
    }

    private static void parseTablets()
    {
        List<String> lines;
        try
        {
            lines = Files.readAllLines(Path.of(srcPath));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        for (String line : lines.subList(1, lines.size()))
        {
            Tablet tablet = parseTabletLine(line);
            tablets.add(tablet);
        }

        System.out.println("\n\n0. Исходный массив:\n");
        printTablets(tablets);
    }

    private static void sortTabletsByName()
    {
        List<Tablet> sortedTablets = tablets.stream()
                .sorted(Comparator.comparing(Tablet::getName))
                .toList();

        System.out.println("\n\n1. Сортировка по наименованию:\n");
        printTablets(sortedTablets);
    }

    private static void sortTabletsByPrice()
    {
        List<Tablet> sortedTablets = tablets.stream()
                .sorted(Comparator.comparing(Tablet::getPrice))
                .toList();

        System.out.println("\n\n2. Сортировка по цене:\n");
        printTablets(sortedTablets);
    }

    private static void findBestScoreTablet()
    {
        Tablet bestScoreTablet = tablets.stream()
                .max(Comparator.comparing(Tablet::getScore))
                .orElse(null);

        System.out.println("\n\n3. Планшет с наибольшей оценкой покупателей:\n");
        if (bestScoreTablet == null) System.out.println("Не найдено");
        else printTablets(List.of(bestScoreTablet));
    }

    private static void filterExpensiveAndPerfectTablets()
    {
        List<Tablet> filteredTablets = tablets.stream()
                .filter(t -> t.getPrice() > 40000 && t.getScore() == 5.0)
                .toList();

        System.out.println("\n\n4. Планшеты стоимостью > 40 тыс. руб. и оценкой 5:\n");
        if (filteredTablets.isEmpty()) System.out.println("Не найдено");
        else printTablets(filteredTablets);
    }

    private static void calculateSamsungAveragePrice()
    {
        OptionalDouble average = tablets.stream()
                .filter(t -> "Samsung".equals(t.getBrand()))
                .mapToInt(Tablet::getPrice)
                .average();

        System.out.println("\n\n5. Средняя стоимость планшетов Samsung:\n");
        if (average.isEmpty()) System.out.println("Не найдено");
        else System.out.println("Средняя цена: " + String.format("%.2f", average.getAsDouble()) + " руб.");
    }

    private static void countUniqueTabletColors()
    {
        long colorCount = tablets.stream()
                .map(Tablet::getColour)
                .distinct()
                .count();

        System.out.println("\n\n6. Количество уникальных цветов планшетов:\n");
        System.out.println("Уникальных цветов: " + colorCount);
    }

    private static void checkAllHighScoreTablets()
    {
        boolean allTabletsHighScored = tablets.stream()
                .allMatch(t -> t.getScore() > 4.5f);
        
        System.out.println("\n\n7. Все ли планшеты имеют оценку > 4.5:\n");
        System.out.println("Все планшеты имеют оценку > 4.5: " + (allTabletsHighScored ? "ДА" : "НЕТ"));
    }
    
    private static void checkLargeAndCheapTablets() {
        boolean tabletsExist = tablets.stream()
                .anyMatch(t -> t.getDiagonal() > 10.0f && t.getPrice() < 15000);

        System.out.println("\n\n8. Есть ли планшет с диагональю > 10 и ценой < 15 тыс. руб:\n");
        System.out.println("Такие планшеты существуют: " + (tabletsExist ? "ДА" : "НЕТ"));
    }

    private static void findMostExpensiveTablet()
    {
        Optional<Tablet> mostExpensive = tablets.stream()
                .collect(Collectors.maxBy(Comparator.comparing(Tablet::getPrice)));

        System.out.println("\n\n9. Самый дорогой планшет:\n");
        if (mostExpensive.isEmpty()) System.out.println("Не найдено");
        else printTablets(List.of(mostExpensive.get()));
    }

    private static void partitionTabletsByRating()
    {
        Map<Boolean, List<Tablet>> partitioned = tablets.stream()
                .collect(Collectors.partitioningBy(t -> t.getScore() > 4.5));

        System.out.println("\n\n10. Разделение планшетов по оценке (4.5):\n");
        System.out.println("Планшеты с оценкой > 4.5:\n");
        printTablets(partitioned.get(true));
        System.out.println("\nПланшеты с оценкой <= 4.5:\n");
        printTablets(partitioned.get(false));
    }

    private static void groupTabletsByBrand()
    {
        Map<String, List<Tablet>> groupedByBrand = tablets.stream()
                .collect(Collectors.groupingBy(Tablet::getBrand));

        System.out.println("\n\n11. Группировка планшетов по брэндам:\n");
        groupedByBrand.forEach((brand, brandTablets) -> {
            System.out.println("\n" + brand + ":\n");
            printTablets(brandTablets);
        });
    }

    private static void brandStatistics()
    {
        Map<String, IntSummaryStatistics> stats = tablets.stream()
                .collect(Collectors.groupingBy(
                        Tablet::getBrand,
                        Collectors.summarizingInt(Tablet::getPrice)
                ));

        System.out.println("\n\n12. Статистика по маркам (количество и минимальная цена):\n");
        stats.forEach((brand, statistics) -> {
            System.out.println("\n" + brand + ":\n");
            System.out.println("\tКоличество: " + statistics.getCount());
            System.out.println("\tМинимальная цена: " + statistics.getMin() + " руб.");
        });
    }

    private static void top3TabletsByRating()
    {
        String top3 = tablets.stream()
                .sorted(Comparator.comparing(Tablet::getScore).reversed())
                .limit(3)
                .map(Tablet::getName)
                .collect(Collectors.joining(", ", "ТОП-3 модели планшетов по оценкам покупателей: ", "."));

        System.out.println("\n\n13. ТОП-3 модели планшетов по оценкам покупателей:\n");
        System.out.println(top3);
    }

    public static void run()
    {
        parseTablets();
        sortTabletsByName();
        sortTabletsByPrice();
        findBestScoreTablet();
        filterExpensiveAndPerfectTablets();
        calculateSamsungAveragePrice();
        countUniqueTabletColors();
        checkAllHighScoreTablets();
        checkLargeAndCheapTablets();
        findMostExpensiveTablet();
        partitionTabletsByRating();
        groupTabletsByBrand();
        brandStatistics();
        top3TabletsByRating();
    }
}
