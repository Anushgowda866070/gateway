package com.socket.gateway.enums;


public enum EWallet {
    APPLE_PAY(1),
    GOOGLE_PAY(2),
    SAMSUNG_PAY(3),
    OTHER(4),
    CLICK_TO_PAY(5),
    UNKNOWN(0);

    private final int ewalletID;
    EWallet(int ewalletID) {
        this.ewalletID = ewalletID;
    }

    public static EWallet getEwalletType(int ewalletID){
        for(EWallet eWallet :EWallet.values()){
            if(eWallet.ewalletID==ewalletID)
                return eWallet;
        }
        return UNKNOWN;
    }

}
