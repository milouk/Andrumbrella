package com.sdu.andrumbrella;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView errorMessage;
    private EditText countryCode;
    private EditText cityName;
    private Switch metricSwitch;
    private Button searchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countryCode = findViewById(R.id.countryCode);
        cityName = findViewById(R.id.cityName);
        metricSwitch = findViewById(R.id.metric);
        searchButton = findViewById(R.id.search);
        errorMessage = findViewById(R.id.errorMessage);



        countryCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    hideErrorMessage();
                    countryCode.setHint("");
                } else {
                    countryCode.setHint("Country Code");
                }
            }
        });

        cityName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    hideErrorMessage();
                    cityName.setHint("");
                } else {
                    cityName.setHint("City");
                }
            }
        });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideErrorMessage();
                if(!countryCode.getText().toString().equals("") && !cityName.getText().toString().equals("")) {
                    Intent startSearch = new Intent(MainActivity.this, UpcomingDays.class);
                    startSearch.putExtra("COUNTRY_CODE", countryCode.getText().toString());
                    startSearch.putExtra("CITY_NAME", cityName.getText().toString());
                    startSearch.putExtra("SWITCH_STATE", metricSwitch.isChecked());
                    startActivity(startSearch);
                }else{
                    showErrorMessage();
                }
            }
        });
    }

    public void showErrorMessage(){
        errorMessage.setVisibility(View.VISIBLE);
    }

    public void hideErrorMessage(){
        errorMessage.setVisibility(View.INVISIBLE);
    }
}
