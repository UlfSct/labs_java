package lab4;

public class Accounting implements Observer
{
    private static Accounting uniqueInstance;

    private Accounting() {}

    public synchronized static Accounting getInstance(){
        if (uniqueInstance == null){
            uniqueInstance = new Accounting();
        }
        return uniqueInstance;
    }

    private static final String password = "H{NEflvby123";
    private int totalIncome = 0;

    public void getTotalIncome(String enteredPassword)
    {
        if (!enteredPassword.equals(password)) System.out.println("НЕВЕРНЫЙ ПАРОЛЬ");
        else System.out.println("Текущая выручка " + totalIncome + " руб.");
    }

    private void updateIncome(int newOrderIncome)
    {
        totalIncome += newOrderIncome;
    }

    @Override
    public void update(String item, Integer price) {
        updateIncome(price);
    }
}
