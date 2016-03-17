package com.example.andrew.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DetailDestination extends AppCompatActivity {

    private DataSource datasource;
    private Destination destination;
    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_destination);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        datasource = new DataSource(this);
        long destinationId = getIntent().getLongExtra(MainActivity.EXTRA_ASSIGNMENT_ID, -1);
        destination = datasource.getDestination(destinationId);

        Log.d("destination", "onCreate: " + destination);

//        TextView title = (TextView) findViewById(R.id.details_title);
//        title.setText(destination.getCourse().toString());

        textView = (TextView) findViewById(R.id.textView);
        textView.setText(destination.toString());

        editText = (EditText) findViewById(R.id.details_updateText);

        Button updateButton = (Button) findViewById(R.id.details_updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destination.setDestination(editText.getText().toString());
                datasource.updateDestination(destination);
                Toast.makeText(DetailDestination.this, "Destination Updated", Toast.LENGTH_SHORT).show();

                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
