package lab4;

import lombok.Getter;

import java.util.*;


@Getter
public class Warehouse  implements Observer
{
    private final Map<String, Integer> itemsCounts = new HashMap<>();
    private static final Integer minItemAmount = 1;
    private static final Integer maxItemAmount = 5;

    public Warehouse(List<String> itemNames)
    {
        Random random = new Random();

        for (String name : itemNames)
        {
            itemsCounts.put(name, random.nextInt(maxItemAmount - minItemAmount + 1) + minItemAmount);
        }
    }

    public boolean hasItem(String item)
    {
        return itemsCounts.get(item) != 0;
    }

    private void takeItem(String item)
    {
        if (!hasItem(item)) return;
        itemsCounts.put(item, itemsCounts.get(item) - 1);
    }

    @Override
    public void update(String item, Integer price) {
        takeItem(item);
    }
}
