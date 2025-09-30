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
        outString += String.format("Лучший игрок: %s\n", this.bestPlayer.getName());
        return outString;
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

public class FootballClubDemo
{
    private static List<FootballClub> footballClubs;

    static void printClubs()
    {
        for (FootballClub club : footballClubs)
        {
            System.out.println(club);
        }
    }

    public static void task12()
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
