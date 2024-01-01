package elements;

public class SellingOrder extends Order implements Comparable<SellingOrder> {

    public SellingOrder(int traderId, double amount, double price) {
        super(traderId, amount, price);
    }
    public int compareTo(SellingOrder e){
        if(price > e.price){
            return 1;
        }
        if(price < e.getPrice()){
            return -1;
        }
        if(amount < e.amount){
            return 1;
        }
        if(amount > e.amount){
            return -1;
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
