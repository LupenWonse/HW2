package com.group32.hw2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;




public class AddExpenseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText editTextName;
    private EditText editTextAmount;
    private Spinner spinnerCategory;
    private EditText editTextDate;
    private ImageButton imageButtonDatepicker;
    private ImageView imageView;

    private Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAmount = (EditText) findViewById(R.id.editTextAmount);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        imageButtonDatepicker = (ImageButton) findViewById(R.id.imageButtonChooseDate);
        imageView = (ImageView) findViewById(R.id.imageView);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,Expense.categories);
        spinnerCategory.setAdapter(spinnerAdapter);

        Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        imageButtonDatepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        // Update the edit Text Field with the date
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,monthOfYear,dayOfMonth);
        Date chosenDate = calendar.getTime();

        DateFormat dateFormat = DateFormat.getDateInstance();
        editTextDate.setText(dateFormat.format(chosenDate));
    }

    public void addExpense(View view) throws ParseException {
        //TODO All verification to be handled here
        if (checkInputs()) {

            Date date = new Date(1, 1, 2016);
            Double amount;
            String name;
            int category = 0;


            name = editTextName.getText().toString();
            amount = Double.parseDouble(editTextAmount.getText().toString());
            try{
                DateFormat dateFormat = DateFormat.getDateInstance();
                date = dateFormat.parse(editTextDate.getText().toString());

            }
            catch (ParseException exception) {
                Log.e("demo","Date Could not be Parsed");
            }

            category = spinnerCategory.getSelectedItemPosition();

            Expense newExpense;

            if (selectedImage != null) {
                newExpense = new Expense(date, amount, name, category, selectedImage.toString());
            } else {
                newExpense = new Expense(date, amount, name, category, "");
            }

            Intent intent = new Intent();
            intent.putExtra(MainActivity.EXPENSE_KEY, newExpense);
            setResult(1, intent);
            finish();
        }
    }

    public void getImage(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        selectedImage = data.getData();
        imageView.setImageURI(selectedImage);
    }

    boolean checkInputs(){
        boolean isInputGood = true;

        if (editTextName.getText().length() <= 0){
            editTextName.setError("Please Enter a Name for this Expense");
            isInputGood = false;
        }

        if (editTextAmount.getText().length() <= 0){
            editTextAmount.setError("Please enter an Amount");
            isInputGood = false;
        }

        if (editTextDate.getText().length() <= 0){
            editTextDate.setError("Please select a date");
            isInputGood = false;
        }

        return  isInputGood;
    }
}
