package com.example.startup.FragmentStudent;

import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.startup.Constants.NavigationDrawerConstants;
import com.example.startup.R;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class Attendance extends Fragment {

    private static final String URL = "https://edfinix.herokuapp.com/api/attendance/";
    private SharedPreferences sp;
    RequestQueue rq;
    String str="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(NavigationDrawerConstants.TAG_Attendance);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_attendance, null);

        rq = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        sp = Objects.requireNonNull(this.getActivity()).getSharedPreferences("login", MODE_PRIVATE);
        String classn = sp.getString("sClass", "");
        String section = sp.getString("sSection", "");
        String admission =sp.getString("sAdmission","");
        String link_student = URL+classn+"/"+section;


//        Log.d("name",link_student);
        Log.d("name",admission);
        attendance(link_student,admission);

        return root;
    }
    public void attendance(String link_student, final String admission)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link_student, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                        JSONArray arr = response.getJSONArray("attendance");
                        for(int i=0;i<arr.length();i++)
                        {
                            JSONObject obj = (JSONObject) arr.get(i);
                            JSONArray jArray = obj.getJSONArray("status");
                            for (int j = 0; j < jArray.length(); j++)
                            {
                                JSONObject jOBJNEW = jArray.getJSONObject(j);
                                if(admission.equals(str =jOBJNEW.getString("admissionNo")))
                                {
                                    Log.d("adm",str);
                                }
                                else
                                    Log.d("error","error");

                            }



                        }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error","att");
            }
        });
        rq.add(jsonObjectRequest);

    }

}

