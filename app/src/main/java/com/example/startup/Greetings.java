package com.example.startup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;

public class Greetings extends AppCompatActivity {

    private TextView greet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greetings);

        greet = findViewById(R.id.greetings);

        Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR_OF_DAY);

        String greeting = null;
        if(hours>=1 && hours<=12){
            greeting = "Good Morning";
        } else if(hours>=12 && hours<=16){
            greeting = "Good Afternoon";
        } else if(hours>=16 && hours<=21){
            greeting = "Good Evening";
        } else if(hours>=21 && hours<=24){
            greeting = "Good Night";
        }


        greet.setText(greeting+" "+ "Mr.Updesh");
    }
}
