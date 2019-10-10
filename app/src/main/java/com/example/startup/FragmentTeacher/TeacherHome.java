package com.example.startup.FragmentTeacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.startup.Constants.NavigationDrawerConstants;
import com.example.startup.R;

import java.util.Calendar;

public class TeacherHome  extends Fragment {

    private TextView greet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        (getActivity()).setTitle(NavigationDrawerConstants.TAG_HOME);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_teacher_home, null);


        greet = root.findViewById(R.id.greetings);

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

        return root;
    }
}
