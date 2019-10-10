package com.example.startup.FragmentTeacher;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.startup.Constants.NavigationDrawerConstants;
import com.example.startup.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class StudentAttendance  extends Fragment {

    private Button attn_submit;
    private Button alert_attn_submit,cancel;
    private TextView date_attendance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        (getActivity()).setTitle(NavigationDrawerConstants.TAG_Attendance);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_teachermark_student_attendance, null);


        date_attendance = root.findViewById(R.id.date_att);

        alert_attn_submit = root.findViewById(R.id.student_mark_attendance);
        cancel = root.findViewById(R.id.student_mark_attendance_cancel);


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        final Calendar c = Calendar.getInstance();
        String date = sdf.format(c.getTime());

        date_attendance.setText(date);

        attn_submit = root.findViewById(R.id.student_mark_attendance);
        attn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder a = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                a.setView(R.layout.attendance_submit);
                a.setCancelable(true);
                a.show();
            }
        });

        return root;
    }
}
