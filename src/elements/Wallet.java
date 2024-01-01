package elements;

public class Wallet {
    private double dollars;
    private double coins;
    private double blockedDollars;
    private double blockedCoins;
    public Wallet(double dollars, double coins){}
    public double getDollars(){
        return this.dollars;
    }
    public void withdrawCoins(double amount){
        this.coins-=amount;
    }
    public void depositCoins(double amount){
        coins+=amount;
    }
    public void withdrawBlockedCoins(double amount){
        this.blockedCoins-=amount;
    }
    public void depositBlockedCoins(double amount){
        this.blockedCoins+=amount;
    }
    public void depositBlockedDollars(double dollars){
        this.dollars+=dollars;
    }
    public void withdrawBlockedDollars(double dollars){
        this.blockedDollars-=dollars;
    }
    public void withdrawDollars(double dollars){
        this.dollars-=dollars;
    }
    public void depositDollars(double dollars){
        this.dollars+=dollars;
    }
    public boolean checkBlockedDollars(double dollars){
        return dollars<=this.blockedDollars;
    }
    public boolean checkDollars(double dollars){
        return dollars<=this.dollars;
    }
    public boolean checkBlockedCoins(double amount){
        return amount<=this.blockedCoins;
    }
    public boolean checkCoins(double amount){
        return amount<=this.coins;
    }
}
