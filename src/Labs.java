import lab1.*;
import lab2.*;
import lab3.*;


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
                runTask("1.2", Lab1Task1::task12);
                runTask("1.3", Lab1Task1::task13);
                runTask("2.3", Lab1Task2::task23);
            case 2:
                runTask("2.1", Lab2Task1::run);
                runTask("2.2", Lab2Task2::run);
            case 3:
                // runTask("3.1", Lab3Task1::run);
                runTask("3.2", Lab3Task2::run);
        }
    }
}
