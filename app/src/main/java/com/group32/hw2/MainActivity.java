package com.group32.hw2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String EXPENSE_ARRAY_KEY = "EXPENSEARRAY";
    public static final String EXPENSE_KEY = "EXPENSE";
    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_DELETE = 2;
    public static final int REQUEST_EDIT = 3;

    public ArrayList<Expense> expenses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set onClickListeners to the buttons in the layout

        findViewById(R.id.buttonAddExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddExpenseActivity.class);
                startActivityForResult(intent, REQUEST_ADD);
            }
        });

        findViewById(R.id.buttonEditExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EditExpenseActivity.class);
                intent.putExtra(EXPENSE_ARRAY_KEY,expenses);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonDeleteExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DeleteExpenseActivity.class);
                intent.putExtra(EXPENSE_ARRAY_KEY,expenses);
                startActivityForResult(intent,REQUEST_DELETE);
            }
        });

        findViewById(R.id.buttonShowExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ShowExpensesActivity.class);
                intent.putExtra(EXPENSE_ARRAY_KEY,expenses);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // TODO Check for the type of initial request and act accordingly
        if (data != null && requestCode == REQUEST_ADD){
            Toast.makeText(this,"ELEMENT Received",Toast.LENGTH_SHORT).show();
            expenses.add((Expense) data.getSerializableExtra(EXPENSE_KEY));
        } else if (data != null && requestCode == REQUEST_DELETE){
            expenses = (ArrayList<Expense>) data.getSerializableExtra(EXPENSE_ARRAY_KEY);
        }
    }
}
