package lab4;

import java.util.*;

public class Shop implements Subject
{
    private final Accounting accounting;
    private final Warehouse warehouse;
    private final DeliveryService deliveryService;
    private final Map<String, Integer> itemPrices = new HashMap<>();
    private AbstractMap.SimpleEntry<String, Integer> lastPurchase = null;
    private final ArrayList<Observer> observersList = new ArrayList<>();

    public Shop(Accounting accounting, Warehouse warehouse, DeliveryService deliveryService)
    {
        this.accounting = accounting;
        this.warehouse = warehouse;
        this.deliveryService = deliveryService;
        registerObserver(this.accounting);
        registerObserver(this.warehouse);
        registerObserver(this.deliveryService);

        Map<String, Integer> itemsInfo = this.warehouse.getItemsCounts();

        Random random = new Random();
        for (String itemName : itemsInfo.keySet())
        {
            int maxPrice = 100000;
            int minPrice = 10000;
            itemPrices.put(itemName, random.nextInt(maxPrice - minPrice + 1) + minPrice);
        }
    }

    private void printEntryMessage()
    {
        System.out.println("Добро пожаловать в магазин мебели! В наличии столы, диваны, шкафы, кухни, кресла и многое другое!");
    }

    private void printExitMessage()
    {
        System.out.println("До встречи снова!");
    }

    private void printAvailableCommands()
    {
        System.out.println("[items | \\i] - вывод доступных позиций");
        System.out.println("[<позиция>] - купить позицию");
        System.out.println("[income <пароль>] - вывод текущей выручки");
        System.out.println("[del <пароль>] - вывод текущих доставок");
        System.out.println("[quit | \\q] - выход из магазина");
    }

    private void printIncorrectInputAlert()
    {
        System.out.println("ВВЕДЕНО НЕКОРРЕКТНОЕ ЗНАЧЕНИЕ");
        System.out.println("[help | \\h] - вывод доступных команд");
    }

    private void printItemsInfo()
    {
        Map<String, Integer> itemsCounts = warehouse.getItemsCounts();
        System.out.println("Доступные позиции:");
        System.out.println("┌-----------------┬--------┬-----┐");
        System.out.printf("| %-15s | %-6s | %-3s |\n", "Позиция", "Цена", "шт.");
        System.out.println("├-----------------┼--------┼-----┤");
        for (String itemName : itemPrices.keySet())
        {
            System.out.printf(
                "| %-15s | %-6d | %-3d |\n",
                itemName,
                itemPrices.get(itemName),
                itemsCounts.get(itemName)
            );
        }
        System.out.println("└-----------------┴--------┴-----┘");
    }

    private boolean tryProcessingIncomeInput(String input)
    {
        String correctInputStart = "income ";
        if (!input.startsWith(correctInputStart)) return false;
        accounting.getTotalIncome(input.substring(correctInputStart.length()));
        return true;
    }

    private boolean tryProcessingDeliveriesInput(String input)
    {
        String correctInputStart = "del ";
        if (!input.startsWith(correctInputStart)) return false;
        deliveryService.getItemDeliveries(input.substring(correctInputStart.length()));
        return true;
    }

    private boolean tryProcessingItemInput(String input)
    {
        if (!itemPrices.containsKey(input)) return false;

        boolean itemIsAvailable = warehouse.hasItem(input);

        if (itemIsAvailable)
        {
            lastPurchase = new AbstractMap.SimpleEntry<>(input, itemPrices.get(input));
            System.out.println("Покупка позиции [" + input + "] совершена успешно");
            notifyObservers();
            return true;
        }

        System.out.println("Позиции [" + input + "] нет на складе");
        return true;
    }

    private boolean processUserInput(String input)
    {
        System.out.println();
        switch (input)
        {
            case "quit":
            case "\\q":
                printExitMessage();
                return false;
            case "items":
            case "\\i":
                printItemsInfo();
                return true;
            case "help":
            case "\\h":
                printAvailableCommands();
                return true;
            default:
                boolean processSucceeded = tryProcessingIncomeInput(input);
                if (!processSucceeded) processSucceeded = tryProcessingItemInput(input);
                if (!processSucceeded) processSucceeded = tryProcessingDeliveriesInput(input);
                if (!processSucceeded) printIncorrectInputAlert();
                return true;
        }
    }

    public void run()
    {
        printEntryMessage();
        printAvailableCommands();
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.println();
            System.out.print("Ваш ввод: ");
            boolean continueInput = processUserInput(scanner.nextLine().trim());
            if (!continueInput) break;
        }
    }

    @Override
    public void registerObserver(Observer o) {
        observersList.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observersList.remove(o);
    }

    @Override
    public void notifyObservers() {
        if (lastPurchase == null) return;
        for (Observer o: observersList) {
            o.update(lastPurchase.getKey(), lastPurchase.getValue());
        }
    }
}
