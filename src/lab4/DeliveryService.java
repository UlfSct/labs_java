package lab4;

import java.util.*;

public class DeliveryService implements Observer
{
    private static final String password = "H{NEflvby123";
    private final List<AbstractMap.SimpleEntry<String, Integer>> itemDeliveries = new ArrayList<>();

    public void getItemDeliveries(String enteredPassword)
    {
        if (!enteredPassword.equals(password))
        {
            System.out.println("НЕВЕРНЫЙ ПАРОЛЬ");
            return;
        }
        System.out.println("Текущие доставки:");
        System.out.println("┌-----┬-----------------┬-----┐");
        System.out.printf("| %-3s | %-15s | %-3s |\n", "№","Позиция", "шт.");
        System.out.println("├-----┼-----------------┼-----┤");
        for (AbstractMap.SimpleEntry<String, Integer> itemDelivery : itemDeliveries)
        {
            System.out.printf(
                    "| %-3d | %-15s | %-3d |\n",
                    itemDeliveries.indexOf(itemDelivery) + 1,
                    itemDelivery.getKey(),
                    itemDelivery.getValue()
            );
        }
        System.out.println("└-----┴-----------------┴-----┘");
        System.out.println();
    }

    private void registerDelivery(String item)
    {
        itemDeliveries.add(new AbstractMap.SimpleEntry<>(item, 1));
    }

    @Override
    public void update(String item, Integer price) {
        registerDelivery(item);
    }
}
