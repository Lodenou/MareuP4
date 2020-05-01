package com.lodenou.mareu.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.lodenou.mareu.Model.Reunion;
import com.lodenou.mareu.R;

import java.util.List;

public class ActivityZoom extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        initiateBackButton();
        getEmailText();

    }

    private void getEmailText() {
        TextView emailfield = findViewById(R.id.email_zoom);

        Intent intent= getIntent();
        Bundle b = intent.getExtras();

        if(b!=null)
        {
            String j =(String) b.get("id");
            emailfield.setText(j);
        }
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

    private void initiateBackButton() {
        // bouton Back en haut à gauche
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
