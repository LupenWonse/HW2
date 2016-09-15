// Homework 02
// ShowExpensesActivity.java
// Akarsh Gupta     - 800969888
// Ahmet Gencoglu   - 800982227
//

package com.group32.hw2;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class ShowExpensesActivity extends AppCompatActivity {

    private TextView textExpenseName;
    private TextView textExpenseAmount;
    private TextView textExpenseDate;
    private TextView textExpenseCategory;

    private ImageView imageExpenseReceipt;

    private int currentExpense;

    private ArrayList<Expense> expenses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expense);
        // Setup UI components
        textExpenseName = (TextView) findViewById(R.id.textExpenseName);
        textExpenseAmount = (TextView) findViewById(R.id.textExpenseAmount);
        textExpenseCategory = (TextView) findViewById(R.id.textExpenseCategory);
        textExpenseDate = (TextView) findViewById(R.id.textExpenseDate);
        imageExpenseReceipt = (ImageView) findViewById(R.id.imageExpenseReceipt);

        findViewById(R.id.buttonFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Get the expenses array from intent
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("EXPENSEARRAY")){
            expenses = (ArrayList<Expense>) getIntent().getExtras().getSerializable("EXPENSEARRAY");
            if (expenses.size() > 0) {
                displayExpense(0);
            }
            else {
                Toast.makeText(this,"No Expense To Show",Toast.LENGTH_SHORT).show();
            }
        }
    }
    // Implement First, Last, Next, Previous Buttons
    public void nextExpense(View v){
        if ( currentExpense < expenses.size() - 1 ) {
            currentExpense += 1;
            displayExpense(currentExpense);
        }
    }

    public void previousExpense(View v){
        if ( currentExpense > 0 ) {
            currentExpense -= 1;
            displayExpense(currentExpense);
        }
    }

    public void firstExpense(View v){
        currentExpense = 0;
        displayExpense(currentExpense);
    }

    public void lastExpense(View v){
        currentExpense = expenses.size() - 1;
        displayExpense(currentExpense);
    }

    void displayExpense(int expenseNumber) {
        // Display the selected expense by updating UI components
        Expense currentExpense = expenses.get(expenseNumber);

        textExpenseName.setText(currentExpense.name);
        textExpenseCategory.setText(Expense.categories[currentExpense.category]);
        textExpenseDate.setText(Expense.dateFormat.format(currentExpense.date));
        textExpenseAmount.setText(currentExpense.amountToString());
        imageExpenseReceipt.setImageURI(Uri.parse(currentExpense.image));
    }
}
