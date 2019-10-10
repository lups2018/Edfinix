package com.example.startup.FragmentTeacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.startup.Constants.NavigationDrawerConstants;
import com.example.startup.R;

public class StudentQueries  extends Fragment {
    @Override


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        (getActivity()).setTitle(NavigationDrawerConstants.TAG_Query);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_student_queries, null);




        return root;
    }
}
