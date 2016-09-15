package com.group32.hw2;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditExpenseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ArrayList<Expense> expenses;

    private EditText editExpenseName;
    private Spinner spinnerCategories;
    private EditText editExpenseAmount;
    private EditText editExpenseDate;
    private ImageView imageReceipt;
    private ImageButton imageButtonDatePicker;
    private Button buttonSave ;
    private DatePickerDialog datePickerDialog;

    private int selectedExpense = -1;
    private Uri selectedImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        editExpenseName = (EditText) findViewById(R.id.editExpenseName);
        editExpenseAmount = (EditText) findViewById(R.id.editAmount);
        editExpenseDate = (EditText) findViewById(R.id.editDate);
        spinnerCategories = (Spinner) findViewById(R.id.spinnerCategories);
        spinnerCategories.setEnabled(false);

        imageReceipt = (ImageView) findViewById(R.id.imageReceipt);
        imageReceipt.setEnabled(false);

        imageButtonDatePicker = (ImageButton) findViewById(R.id.imageButtonDatePicker);
        imageButtonDatePicker.setEnabled(false);

        buttonSave = (Button)findViewById(R.id.buttonSave);
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

        editExpenseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        imageButtonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });


        // TODO Find correct layout to use
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,Expense.categories);
        spinnerCategories.setAdapter(spinnerAdapter);

        // TODO Check what happens without intent
        // TODO Change the key to variable
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(MainActivity.EXPENSE_ARRAY_KEY)) {
            expenses = (ArrayList<Expense>) getIntent().getExtras().getSerializable(MainActivity.EXPENSE_ARRAY_KEY);
        }
        else {
            Log.e("hw2","Edit Activity called without any intent. This should not happen");
            finish();
        }

    }

    public void selectExpense(View view) {

        if(expenses.size() > 0) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            CharSequence expenseNames[] = new CharSequence[expenses.size()];
            for (Expense expense : expenses) {
                expenseNames[expenses.indexOf(expense)] = expense.name;
            }


            // TODO Make this a String value
            alertDialog.setTitle(getString(R.string.alert_title_Pick_Expense));
            alertDialog.setItems(expenseNames, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectedExpense = which;
                    displayExpense(expenses.get(which));
                }
            });

            alertDialog.create().show();
        } else {
            Toast.makeText(this,getString(R.string.no_expense_lable),Toast.LENGTH_SHORT).show();
        }
    }

    private void displayExpense(Expense expense){

        // Enable All Controls
        editExpenseName.setEnabled(true);
        editExpenseAmount.setEnabled(true);
        editExpenseDate.setEnabled(true);
        spinnerCategories.setEnabled(true);
        imageReceipt.setEnabled(true);
        imageButtonDatePicker.setEnabled(true);
        buttonSave.setEnabled(true);
        // Update UI with expense details
        editExpenseName.setText(expense.name);
        editExpenseDate.setText(Expense.dateFormat.format(expense.date));

        editExpenseAmount.setText(expense.amount.toString());
        spinnerCategories.setSelection(expense.category);

        if (expense.image.length() > 0 ) {
            imageReceipt.setImageURI(Uri.parse(expense.image));
            selectedImage = Uri.parse(expense.image);
        }

    }

    public void saveExpense(View v) {
        if (selectedExpense >= 0){
            if (checkInputs()) {

                Date date = new Date(1, 1, 2016);
                Double amount;
                String name;
                int category = 0;


                name = editExpenseName.getText().toString();
                amount = Double.parseDouble(editExpenseAmount.getText().toString());
                try {
                    date = Expense.dateFormat.parse(editExpenseDate.getText().toString());

                } catch (ParseException exception) {
                    Log.e("demo", "Date Could not be Parsed");
                }

                category = spinnerCategories.getSelectedItemPosition();

                Expense newExpense;

                if (selectedImage != null) {
                    newExpense = new Expense(date, amount, name, category, selectedImage.toString());
                } else {
                    newExpense = new Expense(date, amount, name, category, "");
                }

                Intent intent = new Intent();
                expenses.set(selectedExpense, newExpense);

                intent.putExtra(MainActivity.EXPENSE_ARRAY_KEY, expenses);
                setResult(1, intent);
                finish();
            }
        } else {
            Toast.makeText(this, getString(R.string.toast_no_expense_selected),Toast.LENGTH_SHORT).show();
        }
    }

    boolean checkInputs(){
        boolean isInputGood = true;

        if (editExpenseName.getText().length() <= 0){
            editExpenseName.setError("Please Enter a Name for this Expense");
            isInputGood = false;
        }

        if (editExpenseAmount.getText().length() <= 0){
            editExpenseAmount.setError("Please enter an Amount");
            isInputGood = false;
        }

        if (editExpenseDate.getText().length() <= 0){
            editExpenseDate.setError("Please select a date");
            isInputGood = false;
        }

        if (spinnerCategories.getSelectedItemPosition() == 0){
            Toast.makeText(this,"Please select a category",Toast.LENGTH_SHORT).show();
            isInputGood = false;
        }

        return  isInputGood;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        // Update the edit Text Field with the date
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,monthOfYear,dayOfMonth);
        Date chosenDate = calendar.getTime();

        editExpenseDate.setText(Expense.dateFormat.format(chosenDate));
    }

    public void getImage(View view){

        if(Build.VERSION.SDK_INT > 19) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, 100);
        } else {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, 100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            selectedImage = data.getData();
            imageReceipt.setImageURI(selectedImage);
        }
    }

    public void finishActivity(View view){
        finish();
    }

}
