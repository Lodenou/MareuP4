package com.lodenou.mareu.Controller;

import androidx.appcompat.app.AppCompatActivity;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.lodenou.mareu.Model.Reunion;
import com.lodenou.mareu.View.NothingSelectedSpinnerAdapter;
import com.lodenou.mareu.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class ActivityForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] rooms = {"Bowser", "Daisy", "Donkey Kong", "Luigi", "Mario", "Peach", "Toad", "Waluigi", "Wario", "Yoshi"};
    TextView mChooseTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        initiateBackButton();
        initiateValidButton();
        initiateTimePicker();
        initiateSpinner();



    }


    @Override
    protected void onResume() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        // End the current activity when pressing on back button
        finish();
    }

    @Override
    // Back button setting
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void buttonClick(View view) {
        final EditText fieldForm2 = findViewById(R.id.fields_2_form);
        final ChipGroup chipGroup = findViewById(R.id.chip_group);
        final Button emailButton = findViewById(R.id.email_button);

        final Chip chip = new Chip(this);
        ChipDrawable drawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Entry);
        chip.setChipDrawable(drawable);
        chip.setCheckable(false);
        chip.setClickable(false);
        chip.setChipIconResource(R.drawable.ic_fingerprint_black_24dp);
        chip.setIconStartPadding(3f);
        chip.setPadding(60, 10, 60, 10);
        chip.setText(fieldForm2.getText().toString());

        // remove chip on click on the -
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chipGroup.removeView(chip);
            }
        });
        if (!EmailValid(fieldForm2.getText().toString())) {
            Toast.makeText(getApplicationContext(),
                    "Veuillez rentrer un email valide",
                    Toast.LENGTH_LONG).show();
            fieldForm2.setText("");
        } else {

            chipGroup.addView(chip);
            fieldForm2.setText("");
        }
    }


    private void initiateBackButton() {
        // bouton Back en haut à gauche
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initiateTimePicker() {
        mChooseTime = findViewById(R.id.fields_1_form);
        mChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityForm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MINUTE, minutes);
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        SimpleDateFormat sdf = new SimpleDateFormat("HH'h'mm", Locale.FRANCE);
                        mChooseTime.setText(sdf.format(c.getTime()));
                    }
                }, 0, 0, false);
                timePickerDialog.show();
            }
        });
    }


    private void initiateSpinner() {
        //SPINNER SETTINGS

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        final Spinner spin = (Spinner) findViewById(R.id.Spinner);
        spin.setOnItemSelectedListener(this);


        //Creating the ArrayAdapter instance having the rooms' list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rooms);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner and nothingselected
        spin.setAdapter(new NothingSelectedSpinnerAdapter(
                aa,
                R.layout.contact_spinner_row_nothing_selected,

                this));

        spin.setPrompt("Selectionnez une salle");
    }


    private void initiateValidButton() {
        final TextView fieldForm1 = findViewById(R.id.fields_1_form);
        final EditText fieldForm2 = findViewById(R.id.fields_2_form);

        //TODO
        fieldForm2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // ------------------------------------------------------------------------------------------------------------------------------------

        // Validate Button
        // final EditText mEditTextEmail = findViewById(R.id.fields_2_form);
        final Spinner spin = (Spinner) findViewById(R.id.Spinner);
        Button mValidateButton = findViewById(R.id.validate_button);
        mValidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check if the fields are empty or not
                if ((isEmpty1(fieldForm1) || isEmpty2(fieldForm2) || isEmpty3(spin)) == true) {
                    Toast.makeText(getApplicationContext(),
                            "Remplissez tous les champs",
                            Toast.LENGTH_LONG).show();
                }
//                else if (!EmailValid(fieldForm2.getText().toString())) {
//                    Toast.makeText(getApplicationContext(),
//                            "Veuillez rentrer un email valide",
//                            Toast.LENGTH_LONG).show();
//                }
                else {

                    startActivity(getFormInfos());
                }
            }
        });
    }


    // Send form infos to ActivityListMareu
    public Intent getFormInfos() {

        TextView mEditTextHour = findViewById(R.id.fields_1_form);
        final Spinner spin = (Spinner) findViewById(R.id.Spinner);
        final ChipGroup chipGroup = findViewById(R.id.chip_group);


        ArrayList<String> emails = new ArrayList<>();
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            String email = ((Chip) chipGroup.getChildAt(i)).getText().toString();
            emails.add(email);
        }
        


        String myEditedText1 = mEditTextHour.getText().toString();
        String spinnerText = spin.getSelectedItem().toString();





        Intent intent = new Intent(this, ActivityListMareu.class);
        ApiService.getmReunions().add(new Reunion(myEditedText1, spinnerText, emails.toString()));


        return intent;
    }


    // method to see if user entered an email adress in the edittext
    private boolean EmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    // OVERRIDES FOR SPINNER
    //When we select item in the spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    // default choice (no selected item in the spinner)
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    // fields empty methods
    private boolean isEmpty1(TextView fieldForm1) {
        return fieldForm1.getText().toString().trim().length() == 0;
    }

    private boolean isEmpty2(EditText fieldForm2) {
        return fieldForm2.getText().toString().trim().length() == 0;
    }

    //Bidouillage qui marche
    private boolean isEmpty3(Spinner spin) {
        int selectedItemOfMySpinner = spin.getSelectedItemPosition();
        Object actualPositionOfMySpinner = spin.getItemAtPosition(selectedItemOfMySpinner);
        return actualPositionOfMySpinner == null;
    }


}









