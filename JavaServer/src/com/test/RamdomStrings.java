package com.test;

import java.util.Random;

public class RamdomStrings{
    private final String stringchar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private Random rnd = new Random();
    private StringBuffer sbf = new StringBuffer(15); 

    public String GetRandomString(int cnt){
        for(int i=0; i<cnt; i++){
            int val = rnd.nextInt(stringchar.length());
            sbf.append(stringchar.charAt(val));
        }

        return sbf.toString();
    }
}