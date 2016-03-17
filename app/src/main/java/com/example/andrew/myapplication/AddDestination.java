package com.example.andrew.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddDestination extends AppCompatActivity {

    private DataSource datasource;
    EditText descriptionEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_destination);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        datasource = new DataSource(this);

        descriptionEditText = (EditText) findViewById(R.id.descriptionText);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.save);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long destinationId = datasource.createDestination(descriptionEditText.getText().toString());
                Intent resultIntent = new Intent();
                resultIntent.putExtra(MainActivity.EXTRA_ASSIGNMENT_ID, destinationId);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
