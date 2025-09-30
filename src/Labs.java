import lab1.Task1;
import lab1.Task2;

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
                runTask("1.2", Task1::task12);
                runTask("1.3", Task1::task13);
                runTask("2.3", Task2::task23);
            case 2:
                System.out.println("\n================\n2 лабы пока нет\n================\n");
            default:
                System.out.println("\n================\nКонец выполнения\n================\n");
        }
    }
}
