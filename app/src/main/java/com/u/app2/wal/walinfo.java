package com.u.app2.wal;

public class walinfo {
    public String currency;
    public String balance;
    public String locked;
    public String avg_buy_price;
    //public String balance_price;
    public String trade;

    public walinfo(String currency, String balance, String locked,String avg_buy_price,  String trade){

        this.currency =currency;
        this.balance = balance;
        this.locked = locked;
        this.avg_buy_price = avg_buy_price;
      //  this.balance_price = balance_price;
        this.trade = trade;
    }
}

