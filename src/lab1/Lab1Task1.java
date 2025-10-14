package lab1;

import java.util.*;

class BestPlayer implements Comparable<BestPlayer>
{
    private String name;

    BestPlayer(String name)
    {
        this.name = name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;
        BestPlayer that = (BestPlayer) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(name);
    }

    @Override
    public int compareTo(BestPlayer other)
    {
        return this.name.compareTo(other.getName());
    }
}


class FootballClub implements Comparable<FootballClub>
{
    private final String name;
    private Integer numberOfGames;
    private BestPlayer bestPlayer;

    FootballClub(String name, Integer numberOfGames, String bestPlayerName)
    {
        this.name = name;
        this.numberOfGames = numberOfGames;
        this.bestPlayer = new BestPlayer(bestPlayerName);
    }

    public String getName()
    {
        return name;
    }

    public void setNumberOfGames(Integer numberOfGames)
    {
        if (numberOfGames < 0)
        {
            System.out.println("Некорректное значение количества игр");
            return;
        }
        this.numberOfGames = numberOfGames;
    }

    public Integer getNumberOfGames()
    {
        return numberOfGames;
    }

    public void setBestPlayer(BestPlayer bestPlayer)
    {
        this.bestPlayer = bestPlayer;
    }

    public BestPlayer getBestPlayer()
    {
        return bestPlayer;
    }

    @Override
    public String toString()
    {
        String outString = "=============\n";
        outString += String.format("Название клуба: %s\n", this.name);
        outString += String.format("Количество игр: %d\n", this.numberOfGames);
        outString += String.format("Лучший игрок: %s\n", this.bestPlayer);
        return outString;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;
        FootballClub that = (FootballClub) o;
        return Objects.equals(name, that.name) && Objects.equals(numberOfGames, that.numberOfGames) && Objects.equals(bestPlayer, that.bestPlayer);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, numberOfGames, bestPlayer);
    }

    @Override
    public int compareTo(FootballClub other)
    {
        return this.name.compareTo(other.getName());
    }

    public static Comparator<FootballClub> byNumberOfGames()
    {
        return Comparator.comparing(FootballClub::getNumberOfGames);
    }

    public static Comparator<FootballClub> byBestPlayer()
    {
        return Comparator.comparing(FootballClub::getBestPlayer);
    }
}


class FootballClubDemo
{
    private static List<FootballClub> footballClubs;

    static void printClubs()
    {
        for (FootballClub club : footballClubs)
        {
            System.out.println(club);
        }
    }

    public static void run()
    {
        footballClubs = Arrays.asList(
                new FootballClub("Локомотив М", 29, "Алексей Батраков"),
                new FootballClub("Краснодар", 25, "Джон Кордоба"),
                new FootballClub("Спартак М", 27, "Эсекьель Барко")
        );

        System.out.println("Первоначальный массив\n");
        printClubs();

        System.out.println("Сортировка по названию\n");
        Collections.sort(footballClubs);
        printClubs();

        System.out.println("Сортировка по количеству игр\n");
        footballClubs.sort(FootballClub.byNumberOfGames());
        printClubs();

        System.out.println("Сортировка по лучшему игроку\n");
        footballClubs.sort(FootballClub.byBestPlayer());
        printClubs();
    }
}


record BestPlayerRecord(String name) implements Comparable<BestPlayerRecord>
{
    @Override
    public String toString() {
        return this.name();
    }

    @Override
    public int compareTo(BestPlayerRecord other)
    {
        return this.name().compareTo(other.name());
    }
}


record FootballClubRecord(String name, Integer numberOfGames, BestPlayerRecord bestPlayer)
        implements Comparable<FootballClubRecord>
{
    FootballClubRecord
    {
        if (numberOfGames < 0) throw new IllegalArgumentException("Отрицательное количество игр");
    }

    FootballClubRecord(String name, Integer numberOfGames, String bestPlayerName)
    {
        this(name, numberOfGames, new BestPlayerRecord(bestPlayerName));
    }

    @Override
    public String toString() {
        String outString = "=============\n";
        outString += String.format("Название клуба: %s\n", this.name());
        outString += String.format("Количество игр: %d\n", this.numberOfGames());
        outString += String.format("Лучший игрок: %s\n", this.bestPlayer().name());
        return outString;
    }

    @Override
    public int compareTo(FootballClubRecord other)
    {
        return this.name().compareTo(other.name());
    }

    public static Comparator<FootballClubRecord> byNumberOfGames()
    {
        return Comparator.comparing(FootballClubRecord::numberOfGames);
    }

    public static Comparator<FootballClubRecord> byBestPlayer()
    {
        return Comparator.comparing(FootballClubRecord::bestPlayer);
    }
}


class FootballClubRecordDemo
{
    private static List<FootballClubRecord> footballClubs;

    static void printClubs() {
        for (FootballClubRecord club : footballClubs) {
            System.out.println(club);
        }
    }

    public static void run()
    {
        footballClubs = Arrays.asList(
                new FootballClubRecord("Локомотив М", 29, "Алексей Батраков"),
                new FootballClubRecord("Краснодар", 25, "Джон Кордоба"),
                new FootballClubRecord("Спартак М", 27, "Эсекьель Барко")
        );

        System.out.println("Первоначальный массив\n");
        printClubs();

        System.out.println("Сортировка по названию\n");
        Collections.sort(footballClubs);
        printClubs();

        System.out.println("Сортировка по количеству игр\n");
        footballClubs.sort(FootballClubRecord.byNumberOfGames());
        printClubs();

        System.out.println("Сортировка по лучшему игроку\n");
        footballClubs.sort(FootballClubRecord.byBestPlayer());
        printClubs();
    }
}


public class Lab1Task1 {
    public static void task12()
    {
        FootballClubDemo.run();
    }

    public static void task13()
    {
        FootballClubRecordDemo.run();
    }
}
