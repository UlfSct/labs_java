import java.util.*;

record BestPlayerRecord(String name)  implements Comparable<BestPlayerRecord> {
    @Override
    public int compareTo(BestPlayerRecord other)
    {
        return this.name.compareTo(other.name());
    }
}

record FootballClubRecord(String name, Integer numberOfGames, BestPlayerRecord bestPlayer) implements Comparable<FootballClubRecord>
{
    FootballClubRecord(String name, Integer numberOfGames, BestPlayerRecord bestPlayer)
    {
        this.name = name;
        this.numberOfGames = numberOfGames;
        this.bestPlayer = bestPlayer;
    }

    FootballClubRecord(String name, Integer numberOfGames, String bestPlayerName)
    {
        this(name, numberOfGames, new BestPlayerRecord(bestPlayerName));
    }

    @Override
    public String toString() {
        String outString = "=============\n";
        outString += String.format("Название клуба: %s\n", this.name);
        outString += String.format("Количество игр: %d\n", this.numberOfGames);
        outString += String.format("Лучший игрок: %s\n", this.bestPlayer.name());
        return outString;
    }

    @Override
    public int compareTo(FootballClubRecord other)
    {
        return this.name.compareTo(other.name());
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

public class FootballClubRecordDemo
{
    private static List<FootballClubRecord> footballClubs;

    static void printClubs() {
        for (FootballClubRecord club : footballClubs) {
            System.out.println(club);
        }
    }

    public static void task13()
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
