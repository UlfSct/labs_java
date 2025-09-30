import lab1.FootballClubDemo;
import lab1.FootballClubRecordDemo;

public class Labs
{
    static void runTask(String taskNumber, Runnable method)
    {
        System.out.println("=========================================\n");
        System.out.printf("Задание %s\n\n", taskNumber);
        System.out.println("=========================================\n");
        method.run();
    }

    public static void runLab(Integer number)
    {
        switch (number) {
            case 1:
                runTask("1.2", FootballClubDemo::task12);
                runTask("1.3", FootballClubRecordDemo::task13);
            case 2:
                System.out.println("2 лабы пока нет");
            default:
                System.out.println("Конец выполнения");
        }
    }
}
