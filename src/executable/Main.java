package executable;

import elements.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main{
    public static Random myRandom;
    public static void main(String[] args) throws FileNotFoundException {
        Scanner io= new Scanner(new File(args[0]));
        final long seed =io.nextLong();
        myRandom=new Random(seed);

        final int initialFee= io.nextInt();
        final int totalNumOfUsers=io.nextInt();
        final int noOfQueries=io.nextInt();

        ArrayList<Trader> traders=new ArrayList<Trader>();
        for(int i=0;i<totalNumOfUsers;i++){
            final double dollars=io.nextDouble();
            final double coins=io.nextDouble();
            traders.add(new Trader(dollars,coins));
        }
        Market market= new Market(initialFee);
        market.setTraders(traders);

        PrintStream op = new PrintStream(new File(args[1]));
        int invalidQueries=0;
        for(int i=0;i<noOfQueries;i++){
            final int query=io.nextInt();
            switch (query){
                case 10:{   // buy at specified price
                    final int id= io.nextInt();
                    final double price= io.nextDouble();
                    final double amount= io.nextDouble();
                    final Wallet wallet= traders.get(id).getWallet();

                    if(id==0){
                        market.giveBuyOrder(new BuyingOrder(id, amount, price));
                    }
                    else{
                        final boolean flag=wallet.checkDollars(amount*price);
                        if(flag){
                            market.giveBuyOrder(new BuyingOrder(id, amount, price));
                            wallet.depositBlockedDollars(amount*price);
                        }
                        else{
                            invalidQueries++;
                            break;
                        }
                    }
                }

                case 11:{
                    final int id= io.nextInt();
                    final double price=market.sellingOrderPrice();
                    final int amount= io.nextInt();
                    final Wallet wallet= traders.get(id).getWallet();

                    if(id==0){
                        market.giveBuyOrder(new BuyingOrder(id, amount, price));
                    }
                    else{
                        final boolean flag=wallet.checkDollars(amount*price);
                        if(flag){
                            market.giveBuyOrder(new BuyingOrder(id, amount, price));
                            wallet.depositBlockedDollars(amount*price);
                        }
                        else{
                            invalidQueries++;
                            break;
                        }
                    }
                }
                case 20: {
                    final int id=io.nextInt();
                    final double price=io.nextDouble();
                    final double amount=io.nextDouble();
                    final Wallet wallet= traders.get(id).getWallet();
                    if(id==0){
                        market.giveSellOrder(new SellingOrder(id,amount, price));
                    }
                    else{
                        final boolean flag=wallet.checkCoins(amount);
                        if(flag){
                            market.giveSellOrder(new SellingOrder(id, amount, price));
                            wallet.depositBlockedCoins(amount*price);
                        }
                        else{
                            invalidQueries++;
                            break;
                        }
                    }
                }
                case 21:{
                    final int id=io.nextInt();
                    final double price= market.buyingOrderPrice();
                    final double amount=io.nextDouble();
                    final Wallet wallet= traders.get(id).getWallet();
                    if(id==0){
                        market.giveSellOrder(new SellingOrder(id,amount, price));
                    }
                    else{
                        final boolean flag=wallet.checkCoins(amount);
                        if(flag){
                            market.giveSellOrder(new SellingOrder(id, amount, price));
                            wallet.depositBlockedCoins(amount*price);
                        }
                        else{
                            invalidQueries++;
                            break;
                        }
                    }
                }
                case 3:{
                    final int id= io.nextInt();
                    final double dollars=io.nextDouble();
                    traders.get(id).getWallet().depositDollars(dollars);
                    break;
                }
                case 4:{
                    final int id=io.nextInt();
                    final double dollars=io.nextDouble();
                    final Wallet wallet= traders.get(id).getWallet();
                    if(wallet.checkDollars(dollars)){
                        wallet.withdrawDollars(dollars);
                    }
                    else{
                        invalidQueries++;
                    }
                    break;
                }
                case 5:{
                    final int id= io.nextInt();
                    Trader trader= traders.get(id);
                    op.println(trader);
                    break;
                }
                case 777:{
                    for(Trader t: traders){
                        t.getWallet().depositCoins(myRandom.nextDouble()*10.0);
                    }
                    break;
                }
                case 666: {
                    final double price= io.nextDouble();
                    market.makeOpenMarketOperation(price);
                    break;
                }
                case 500: {
                    op.println(market.marketSizeInfo());
                    break;
                }
                case 501: {
                    op.println(String.format("Number of successfull transactions are: %d", market.getNumberOfTransactions()));
                    break;
                }
                case 502: {
                    op.println("Number of invalid queries are: "+invalidQueries);
                    break;
                }
                case 505: {
                    op.println(market.currentPriceInfo());
                    break;
                }
                case 555: {
                    for(Trader t: traders){
                        op.println(t);
                        break;
                    }
                }
                default:{
                    op.println("INVALID QUERY"+query);
                    break;
                }
            }
            market.checkTransactions(traders);
        }
        io.close();
        op.close();
    }
}