package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import edu.gatech.cs2340.shelterfinder2340.R;
import edu.gatech.cs2340.shelterfinder2340.model.Model;
import edu.gatech.cs2340.shelterfinder2340.model.ShelterQuery;

/**
 * The Activity that is used for filtering the shelters
 */
public class FilterActivity extends AppCompatActivity {
    private CheckBox family;
    private CheckBox young;
    private CheckBox any;
    private CheckBox child;
    private CheckBox male;
    private CheckBox female;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText nameText = findViewById(R.id.shelterName);
        nameText.setText("");
        nameText.setHint("Name of the shelter");

        family = findViewById(R.id.familiesButton);
        young = findViewById(R.id.youngAdultButton);
        any = findViewById(R.id.anyoneButton);
        child = findViewById(R.id.childrenButton);
        male = findViewById(R.id.maleButton);
        female = findViewById(R.id.femaleButton);
        Button searchB = findViewById(R.id.searchButton);
        searchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieving use input for search criteria
                boolean hasFamily = family.isChecked();
                boolean hasYoung = young.isChecked();
                boolean hasAny = any.isChecked();
                boolean hasChild = child.isChecked();
                boolean isMale = male.isChecked();
                boolean isFemale = female.isChecked();
                String stName = nameText.getText().toString();
                Intent searchIntent = new Intent(getApplicationContext(), Login_Success.class);
                // ShelterQuery is a query class that can give you a filtered list of shelters
                ShelterQuery query = new ShelterQuery(hasFamily, hasAny,
                        isMale, isFemale, hasChild, hasYoung, stName);
                Model.getInstance().set_filteredShelters(query.filterShelter());
                Model.getInstance().set_query(query);
                searchIntent.putExtra("Label", "search");
                startActivity(searchIntent);
                finish();
            }
        });
        Button cancelB = findViewById(R.id.cancelButton);
        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), Login_Success.class);
                startActivity(myIntent);
                finish();
            }
        });

    }

}
