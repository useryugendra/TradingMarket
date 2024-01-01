package elements;

public class BuyingOrder extends Order implements Comparable<BuyingOrder> {

    public BuyingOrder(int traderId, double amount, double price) {
        super(traderId, amount, price);
    }

    public int compareTo(BuyingOrder e){
        if(price > e.price){
            return -1;
        }
        if(price < e.price){
            return 1;
        }
        if(amount > e.amount){
            return -1;
        }
        if(amount < e.amount){
            return 1;
        }
        if(traderId > e.traderId){
            return 1;
        }
        if(traderId < e.traderId){
            return -1;
        }
        return 0;
    }
}
