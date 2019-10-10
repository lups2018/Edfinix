package com.example.startup.FragmentStudent;

import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.startup.Constants.NavigationDrawerConstants;
import com.example.startup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class Query extends Fragment {
    private Spinner spinner;
    private Button qSubmit;
    private String  link_teacher ="https://edfinix.herokuapp.com/api/teachers";
    private String query_post= "https://edfinix.herokuapp.com/api/query";
    private String query_get = "https://edfinix.herokuapp.com/api/query/student/";
    private EditText topics;
    private EditText details;
    SharedPreferences sp;
    RequestQueue requestQueue;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(NavigationDrawerConstants.TAG_Query);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_query, null);
        requestQueue = Volley.newRequestQueue(getActivity());
        spinner = root.findViewById(R.id.query_spinner);
        topics  = root.findViewById(R.id.detailsQuery);
        details = root.findViewById(R.id.detailsQuery);
        qSubmit = root.findViewById(R.id.button_query);

        retrieveTeacher(link_teacher);

        sp = Objects.requireNonNull(this.getActivity()).getSharedPreferences("login", MODE_PRIVATE);
        String value = sp.getString("username", "");
        String query = query_get + value;

        //Log.d("link",query);

        qSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat df = new SimpleDateFormat("d MMM, yyyy | HH:mm");
                String datetime = df.format(Calendar.getInstance().getTime());
                String Topics=  topics.getText().toString();
                String Details = details.getText().toString();

                //Log.d("df",datetime);




            }
        });


        return  root;


    }
    private void retrieveTeacher(String link_teacher)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, link_teacher, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray teacher = response.getJSONArray("teacher");
                            JSONObject subject = (JSONObject) teacher.get(0);
                            Log.d("subject", String.valueOf(subject));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                });
            requestQueue.add(jsonObjectRequest);

    }
}