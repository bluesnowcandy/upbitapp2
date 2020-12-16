package com.u.app2.wal.pop.order;

public class PopupOrderInfo {
    public String currency;
    public String uuid;
    public String side;
    public String ord_type;
    public String price;
    public String state;
    public String market;
    public String created_at;
    public String volume;
    public String locked;
    public String executed_volume;
    public String gu;


    public PopupOrderInfo(
             String currency
            ,String uuid
            ,String side
            ,String ord_type
            ,String price
            ,String state
            ,String market
            ,String created_at
            ,String volume
            ,String locked
            ,String executed_volume
            ,String gu
    ){

        this.currency        = currency       ;
        this.uuid            = uuid           ;
        this.side            = side           ;
        this.ord_type        = ord_type       ;
        this.price           = price          ;
        this.state           = state          ;
        this.market          = market         ;
        this.created_at      = created_at     ;
        this.volume          = volume         ;
        this.locked          = locked         ;
        this.executed_volume = executed_volume;
        this.gu              = gu             ;
        ;

    }
}

