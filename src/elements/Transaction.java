package elements;

public class Transaction {
    private SellingOrder sellingOrder;
    private BuyingOrder buyingOrder;
    public Transaction(BuyingOrder buyingOrder, SellingOrder sellingOrder){
        this.buyingOrder=buyingOrder;
        this.sellingOrder=sellingOrder;
    }
    public SellingOrder getSellingOrder(){
        return this.sellingOrder;
    }
    public BuyingOrder getBuyingOrder(){
        return this.buyingOrder;
    }
}
