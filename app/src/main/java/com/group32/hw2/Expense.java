package com.group32.hw2;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;

/**
 * Created by ahmet on 08/09/16.
 */
public class Expense implements Serializable {

    public Date date;
    public Double amount;
    public String name;
    public int category;
    public String image;

    public String currencySymbol = Currency.getInstance("USD").getSymbol();

    public static String[] categories = {"Groceries"
                                        ,"Invoice"
                                        ,"Transportation"
                                        ,"Shopping"
                                        ,"Rent"
                                        ,"Trip"
                                        ,"Utilities"
                                        ,"Others"};


    public Expense(Date date, Double amount, String name, int category, String image) {
        this.date = date;
        this.amount = amount;
        this.name = name;
        this.category = category;
        this.image = image;
    }

    public String amountToString(){
        return currencySymbol + Double.toString(amount);
    }
}
