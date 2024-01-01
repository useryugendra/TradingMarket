package elements;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Market {
    private PriorityQueue<SellingOrder> sellingOrders;
    private PriorityQueue<BuyingOrder> buyingOrders;
    private ArrayList<Transaction> transactions;
    private int fee;
    private ArrayList<Trader> traders;
    private int numberOfTransactions;
    public void giveSellOrder(SellingOrder order){
        sellingOrders.add(order);
    }
    public void giveBuyOrder(BuyingOrder order){
        buyingOrders.add(order);
    }
    public void makeOpenMarketOperation(double price){
        while(buyingOrders.peek().getPrice()>=price && !buyingOrders.isEmpty()){
            BuyingOrder order= buyingOrders.peek();
            giveSellOrder(new SellingOrder(0, order.getAmount(), order.getPrice()));
            checkTransactions(traders);
        }
        while(sellingOrders.peek().getPrice()<=price && !sellingOrders.isEmpty()){
            SellingOrder order= sellingOrders.peek();
            giveBuyOrder(new BuyingOrder(0, order.getAmount(), order.getPrice()));
            checkTransactions(traders);
        }
    }


    public void checkTransactions(ArrayList<Trader> traders){
        while(buyingOrders.peek().getPrice()>=sellingOrders.peek().getPrice() && !buyingOrders.isEmpty() && !sellingOrders.isEmpty()){
            BuyingOrder b_order=buyingOrders.poll();
            SellingOrder s_order=sellingOrders.poll();
            double traded_amount;

            if(b_order.getAmount()<s_order.getAmount()){
                traded_amount=b_order.getAmount();
                giveSellOrder(new SellingOrder(s_order.getTraderId(), s_order.getAmount()-b_order.getAmount(), s_order.getPrice()));
            }
            else if(b_order.getAmount()>s_order.getAmount()){
                traded_amount=s_order.getAmount();
                giveBuyOrder(new BuyingOrder(b_order.getTraderId(), b_order.getAmount()-s_order.getAmount(), b_order.getPrice()));
            }
            else{
                traded_amount=s_order.getAmount();
            }

            int buy_flag= traders.get(b_order.getTraderId()).Buy(traded_amount,s_order.getPrice(),this);
            int sell_flag=traders.get(s_order.getTraderId()).Sell(traded_amount,s_order.getPrice(), this);
            if(sell_flag==1 && buy_flag==1){
                numberOfTransactions++;
            }
            if(b_order.getPrice()>s_order.getPrice()){
                traders.get(b_order.getTraderId()).getWallet().depositDollars((b_order.getPrice()-s_order.getPrice())*traded_amount);
            }
            transactions.add(new Transaction(b_order,s_order));
        }
    }

    public void setTraders(ArrayList<Trader> traders){
        this.traders=traders;
    }
    public Market(int fee){
        this.fee=fee;
        sellingOrders= new PriorityQueue<SellingOrder>();
        buyingOrders= new PriorityQueue<BuyingOrder>();
        transactions= new ArrayList<Transaction>();
        traders=new ArrayList<Trader>();
        numberOfTransactions=0;
    }
    public double getFee(){
        return this.fee;
    }

    public double sellingOrderPrice(){
        return sellingOrders.isEmpty()?0.0:sellingOrders.peek().getPrice();
    }
    public double buyingOrderPrice(){
        return buyingOrders.isEmpty()?0.0:buyingOrders.peek().getPrice();
    }
    public String marketSizeInfo(){
        double dollars=0.0;
        double coins=0.0;
        for(BuyingOrder b: buyingOrders){
            dollars+=b.getPrice()*b.getAmount();
        }
        for(SellingOrder s: sellingOrders){
            coins=s.getAmount();
        }
        return String.format("current market size is: %.5f, %.5f", dollars, coins);
    }
    public int getNumberOfTransactions(){
        return numberOfTransactions;
    }
    public String currentPriceInfo(){
        boolean flag1=buyingOrders.isEmpty();
        boolean flag2=sellingOrders.isEmpty();
        double avgPrice;
        if(flag1 && flag2){
            avgPrice=0.0;
        }
        else if(!flag1 && flag2){
            avgPrice=buyingOrderPrice();
        }
        else if(flag1 && !flag2){
            avgPrice=sellingOrderPrice();
        }
        else{
            avgPrice=(sellingOrderPrice()+buyingOrderPrice())/2.0;
        }
        return String.format("Current Prices: %5f, %5f, %5f", buyingOrderPrice(), sellingOrderPrice(), avgPrice);
    }

}
