package com.example.andrew.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_ASSIGNMENT_ID = "extraAssignmentId";

    private ListView listView;
    private ArrayAdapter<Destination> destinationArrayAdapter;
    private DataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.main_list);
        TextView emptyView = (TextView) findViewById(R.id.main_list_empty);

        datasource = new DataSource(this);
        List<Destination> destinations = datasource.getAllDestinations();
        destinationArrayAdapter = new ArrayAdapter<Destination>(this, android.R.layout.simple_list_item_1, destinations);
        listView.setAdapter(destinationArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(MainActivity.this, DetailDestination.class);
            intent.putExtra(EXTRA_ASSIGNMENT_ID, destinationArrayAdapter.getItem(position).getId());
            startActivityForResult(intent, 2);
            }
        });

        registerForContextMenu(listView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddDestination.class);
                startActivityForResult(intent, 1234);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            long destinationId = data.getLongExtra(EXTRA_ASSIGNMENT_ID, -1);
            if(destinationId != -1) {
                Destination destination = datasource.getDestination(destinationId);
                destinationArrayAdapter.add(destination);
                updateDestinationListView();
            }
        }

        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                updateDestinationListView();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Selecteer een keuze");
        menu.add(0, v.getId(), 0, "Delete");
        menu.add(0, v.getId(), 0, "Cancel");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getTitle() == "Delete") {
            Toast.makeText(getApplicationContext(), "Bestemming verwijderd", Toast.LENGTH_LONG).show();
            Destination destination = destinationArrayAdapter.getItem(info.position);
            destinationArrayAdapter.remove(destination);
            datasource.deleteDestination(destination);

            updateDestinationListView();
        } else if (item.getTitle() == "Cancel") {
            return false;
        } else {
            return false;
        }
        return true;
    }

    public void updateDestinationListView() {
        List<Destination> destinations = datasource.getAllDestinations();
        destinationArrayAdapter = new ArrayAdapter<Destination>(this, android.R.layout.simple_list_item_1, destinations);
        listView.setAdapter(destinationArrayAdapter);
    }

}
