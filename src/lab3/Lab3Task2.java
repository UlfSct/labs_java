package lab3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

record Task(
    Integer athleteNumber,
    String teamName,
    double speed,
    CountDownLatch startLatch,
    CountDownLatch finishLatch
) implements Runnable
{
    private static final double distance = 100.0;
    private static volatile String winner;

    public static String getWinner()
    {
        return winner;
    }

    private String getAthleteText()
    {
        return teamName + " " + athleteNumber + "(" + String.format("%.2f", speed) + " м/с)";
    }

    private void outputStatus(String status, LocalDateTime date)
    {
        System.out.println("[" + date.format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS")) + "][" + getAthleteText() + "] " + status);
    }

    @Override
    public void run()
    {
        try
        {
            outputStatus(athleteNumber == 1 ? "Готов к старту" : "Ждёт финиша первого атлета", LocalDateTime.now());
            startLatch.await();

            outputStatus("Побежал", LocalDateTime.now());
            Thread.sleep((long)(distance / speed) * 1000);

            outputStatus(athleteNumber == 1 ? "Добежал" : "!!!ФИНИШ!!!", LocalDateTime.now());
            if (athleteNumber == 1) finishLatch.countDown();
            else
            {
                synchronized (Task.class)
                {
                    if (winner == null)
                    {
                        winner = teamName;
                    }
                }
            }
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }


    }
}

class RelayRace
{
    private static final String firstTeamName = "Альфа";
    private static final String secondTeamName = "Браво";
    private static final double maxAthleteSpeed = 10.0;
    private static final double athleteSpeedDifference = 5.0;

    public static void runRelay()
    {
        System.out.println("ЭСТАФЕТА 200 МЕТРОВ");
        System.out.println("Команды: " + firstTeamName + " vs " + secondTeamName + "\n");

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch firstTeamLatch = new CountDownLatch(1);
        CountDownLatch secondTeamLatch = new CountDownLatch(1);

        Task firstTeamRunner1 = new Task(1, "Альфа", Math.random() * athleteSpeedDifference + maxAthleteSpeed, startLatch, firstTeamLatch);
        Task firstTeamRunner2 = new Task(2, "Альфа", Math.random() * athleteSpeedDifference + maxAthleteSpeed, firstTeamLatch, null);
        Task secondTeamRunner1 = new Task(1, "Браво", Math.random() * athleteSpeedDifference + maxAthleteSpeed, startLatch, secondTeamLatch);
        Task secondTeamRunner2 = new Task(2, "Браво", Math.random() * athleteSpeedDifference + maxAthleteSpeed, secondTeamLatch, null);

        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.execute(firstTeamRunner1);
        executor.execute(firstTeamRunner2);
        executor.execute(secondTeamRunner1);
        executor.execute(secondTeamRunner2);

        try {
            Thread.sleep(1000);
            System.out.println("\nНА СТАРТ!");
            Thread.sleep(1000);
            System.out.println("\nВНИМАНИЕ!");
            Thread.sleep(1000);
            System.out.println("\nМАРШ! ЭСТАФЕТА НАЧАЛАСЬ!");
            startLatch.countDown();

            executor.shutdown();

            if (!executor.awaitTermination(60, TimeUnit.SECONDS))
            {
                System.out.println("\n\nВРЕМЯ ЗАКОНЧИЛОСЬ");
            }

            String winner = Task.getWinner();
            if (winner != null) System.out.println("\n\nПОБЕДИТЕЛЬ: " + winner);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
}

public class Lab3Task2
{

    public static void run()
    {
        RelayRace.runRelay();
    }
}
