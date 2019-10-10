package com.example.startup.FragmentStudent;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.startup.Constants.NavigationDrawerConstants;
import com.example.startup.R;

import java.util.Objects;

public class Home  extends Fragment {

    private CardView cardatt, cardtime, cardxams;
    private TextView textatt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(NavigationDrawerConstants.TAG_HOME);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_home, null);

        cardatt = root.findViewById(R.id.card_att);
        cardtime = root.findViewById(R.id.card_time);
        //cardxams = container.findViewById(R.id.card_exam);
        textatt = root.findViewById(R.id.text_att);
        cardatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent a = new Intent(getActivity(),Attendance.class);
                //startActivity(a);
                Attendance newfragment = new Attendance();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, newfragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        cardtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent a = new Intent(getActivity(),Attendance.class);
                //startActivity(a);
                TimeTable newfragment = new TimeTable();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, newfragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return root;
    }


}
