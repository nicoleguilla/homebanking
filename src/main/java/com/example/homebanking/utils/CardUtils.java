package com.example.homebanking.utils;

import java.text.DecimalFormat;

public final class CardUtils {
    private CardUtils() {
    }

    public static int getCvv(){
        return (int) (Math.random() * 999);
    }

    public static String getCardNumber(){
        DecimalFormat format = new DecimalFormat("0000");
        String number="";
        for (int i = 0; i < 4; i++){
            number += format.format((int)(Math.random() * 9999));
            if(i != 3){
                number += "-";
            }
        }
        return number;
    }
}
