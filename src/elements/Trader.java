package elements;

public class Trader {
    private int id;
    private Wallet wallet;
    public Trader(double dollars, double coins){}
    public int Sell(double amount, double price, Market market){
        boolean flag=wallet.checkBlockedCoins(amount);
        if(flag){
            wallet.withdrawBlockedCoins(amount);
            double depositAmount=(amount*price)*(1-(market.getFee()/1000.0));
            wallet.depositDollars(depositAmount);
        }
        return flag?1:0;
    }

    public int Buy(double amount, double price, Market market){
        double payable=price*amount;
        boolean flag=wallet.checkBlockedDollars(payable) || id==0;
        if(flag){
            wallet.withdrawBlockedDollars(payable);
            wallet.depositCoins(amount);
        }
        return flag?1:0;
    }

    public void buyWriteBack(double amount, double price, Market market){
        wallet.withdrawCoins(amount);
        wallet.depositBlockedDollars(amount*price);
    }
    public void sellWriteBack(double amount, double price, Market market){
        wallet.depositBlockedCoins(amount);
        wallet.withdrawDollars((amount*price)*(1-(market.getFee()/1000.0)));
    }
    public Wallet getWallet(){
        return this.wallet;
    }

    public static int numberOfUsers=0;
}
