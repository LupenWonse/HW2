// Homework 02
// Expense.java
// Akarsh Gupta     - 800969888
// Ahmet Gencoglu   - 800982227
//

package com.group32.hw2;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Currency;
import java.util.Date;

/**
 * Created by ahmet on 08/09/16.
 */
public class Expense implements Serializable {
    // Static dateFormat
    public static DateFormat dateFormat = java.text.DateFormat.getDateInstance(DateFormat.DATE_FIELD);
    // Instance variables
    public Date date;
    public Double amount;
    public String name;
    public int category;
    public String image;

    public String currencySymbol = Currency.getInstance("USD").getSymbol();
    // Static categories
    public static String[] categories = {"Select a category"
                                        ,"Groceries"
                                        ,"Invoice"
                                        ,"Transportation"
                                        ,"Shopping"
                                        ,"Rent"
                                        ,"Trip"
                                        ,"Utilities"
                                        ,"Others"};

    // Constructor
    public Expense(Date date, Double amount, String name, int category, String image) {
        this.date = date;
        this.amount = amount;
        this.name = name;
        this.category = category;
        this.image = image;
    }
    // Helper function to display amount with currency
    public String amountToString(){

        return currencySymbol + " " + Double.toString(amount);
    }
}
