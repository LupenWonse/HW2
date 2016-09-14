package com.group32.hw2;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;

public class DeleteExpenseActivity extends AppCompatActivity {

    private Expense currentExpense;
    private ArrayList<Expense> expenses;
    private int selectedExpense = -1;

    private EditText editExpenseName;
    private Spinner spinnerCategories;
    private EditText editExpenseAmount;
    private EditText editExpenseDate;
    private ImageView imageReceipt;

    private Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_expense);

        expenses = (ArrayList<Expense>) getIntent().getExtras().getSerializable(MainActivity.EXPENSE_ARRAY_KEY);

        editExpenseName = (EditText) findViewById(R.id.editTextName);
        editExpenseAmount = (EditText) findViewById(R.id.editTextAmount);
        editExpenseDate = (EditText) findViewById(R.id.editTextDate);
        spinnerCategories = (Spinner) findViewById(R.id.spinnerCategory);
        imageReceipt = (ImageView) findViewById(R.id.imageViewReceipt);


        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, Expense.categories);
        spinnerCategories.setAdapter(spinnerAdapter);
    }

    public void selectExpense(View view) {
        if (expenses != null && expenses.size() > 0) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(R.string.app_name);

            CharSequence expenseNames[] = new CharSequence[expenses.size()];
            for (Expense expense : expenses) {
                expenseNames[expenses.indexOf(expense)] = expense.name;
            }

            alertDialog.setItems(expenseNames, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectedExpense = which;
                    displayExpense(expenses.get(which));
                }
            });

            alertDialog.create().show();
        } else {
            Toast.makeText(this, getResources().getString(R.string.no_expense_lable), Toast.LENGTH_SHORT).show();
        }
    }

    private void displayExpense(Expense expense) {
        editExpenseName.setText(expense.name);

        DateFormat defaultDateFormat = DateFormat.getDateInstance();
        editExpenseDate.setText(defaultDateFormat.format(expense.date));
        editExpenseAmount.setText(expense.amount.toString());
        spinnerCategories.setSelection(expense.category);
        imageReceipt.setImageURI(Uri.parse(expense.image));

        selectedImage = Uri.parse(expense.image);

    }

    public void deleteExpense(View view) {
        if (selectedExpense >= 0) {
            expenses.remove(selectedExpense);
            Intent intent = new Intent();
            intent.putExtra(MainActivity.EXPENSE_ARRAY_KEY, expenses);
            setResult(1, intent);
            finish();
        } else {
            Toast.makeText(this,"No Expense Selected",Toast.LENGTH_SHORT).show();
        }
    }

    public void finishActivity(View view) {

        finish();
    }
}
