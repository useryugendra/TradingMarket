package elements;

public class Order {
    double amount;
    double price;
    int traderId;
    public Order(int traderId, double amount, double price){
        this.traderId=traderId;
        this.amount=amount;
        this.price=price;
    }
    public double getPrice(){
        return this.price;
    }
    public double getAmount(){
        return this.amount;
    }
    public int getTraderId(){
        return this.traderId;
    }

}
