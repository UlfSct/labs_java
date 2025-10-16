package lab4;

import java.util.List;

public class ShopFactory {
    public Shop createShop(List<String> itemNames)
    {
        return new Shop(
            Accounting.getInstance(),
            new Warehouse(itemNames),
            new DeliveryService()
        );
    }
}
