// Homework 02
// DeleteExpensesActivity.java
// Akarsh Gupta     - 800969888
// Ahmet Gencoglu   - 800982227
//

package com.group32.hw2;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class DeleteExpenseActivity extends AppCompatActivity {

    private ArrayList<Expense> expenses;
    private int selectedExpense = -1;

    private EditText editExpenseName;
    private Spinner spinnerCategories;
    private EditText editExpenseAmount;
    private EditText editExpenseDate;
    private ImageView imageReceipt;
    private Button buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_expense);

        // Obtain the provided Expense Array List
        expenses = (ArrayList<Expense>) getIntent().getExtras().getSerializable(MainActivity.EXPENSE_ARRAY_KEY);

        // Setup UI component
        editExpenseName = (EditText) findViewById(R.id.editTextName);
        editExpenseAmount = (EditText) findViewById(R.id.editTextAmount);
        editExpenseDate = (EditText) findViewById(R.id.editTextDate);
        spinnerCategories = (Spinner) findViewById(R.id.spinnerCategory);
        spinnerCategories.setEnabled(false);

        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        imageReceipt = (ImageView) findViewById(R.id.imageViewReceipt);

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, Expense.categories);
        spinnerCategories.setAdapter(spinnerAdapter);
    }

    public void selectExpense(View view) {
        // Check for expenses and if there are any expenses let the user choose with Alert Dialog
        if (expenses != null && expenses.size() > 0) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(R.string.alert_title_Pick_Expense);

            CharSequence expenseNames[] = new CharSequence[expenses.size()];
            for (Expense expense : expenses) {
                expenseNames[expenses.indexOf(expense)] = expense.name;
            }

            alertDialog.setItems(expenseNames, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectedExpense = which;
                    buttonDelete.setEnabled(true);
                    displayExpense(expenses.get(which));
                }
            });
            // Display alert dialog
            alertDialog.create().show();
        } else {
            // Show warning that there are no expenses
            Toast.makeText(this, getResources().getString(R.string.no_expense_lable), Toast.LENGTH_SHORT).show();
        }
    }

    private void displayExpense(Expense expense) {
        // Display the chosen Expense object
        editExpenseName.setText(expense.name);
        editExpenseDate.setText(Expense.dateFormat.format(expense.date));
        editExpenseAmount.setText(expense.amount.toString());
        spinnerCategories.setSelection(expense.category);

        if (expense.image.length() > 0) {
            imageReceipt.setImageURI(Uri.parse(expense.image));
        }

    }

    public void deleteExpense(View view) {
        // Delete the selected Expense object
        if (selectedExpense >= 0) {
            expenses.remove(selectedExpense);
            Intent intent = new Intent();
            intent.putExtra(MainActivity.EXPENSE_ARRAY_KEY, expenses);
            setResult(1, intent);
            finish();
        } else {
            Toast.makeText(this,getString(R.string.no_expense_lable),Toast.LENGTH_SHORT).show();
        }
    }

    public void finishActivity(View view) {
        // End activity
        finish();
    }
}
