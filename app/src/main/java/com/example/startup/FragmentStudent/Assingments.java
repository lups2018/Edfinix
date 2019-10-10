package com.example.startup.FragmentStudent;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.startup.Backend.CustomRecyclerAssingment;
import com.example.startup.Backend.StudentAssingmentSet;
import com.example.startup.Constants.NavigationDrawerConstants;
import com.example.startup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class Assingments extends Fragment {

    private String JsonURL = "https://edfinix.herokuapp.com/api/assignments/";
    SharedPreferences sp;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<StudentAssingmentSet> testList;

    private ArrayList<String> arr = new ArrayList<String>();
    private ArrayList<String> arr1 = new ArrayList<String>();
    private ArrayList<String> arr2 = new ArrayList<String>();

    RequestQueue rq;
    String date = "";
    String time = "";
    String sub = "";
    Handler handler;


    TextView t;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(NavigationDrawerConstants.TAG_Assignment);


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_assingments, null);
        rq = Volley.newRequestQueue(getActivity());

        sp = Objects.requireNonNull(this.getActivity()).getSharedPreferences("login", MODE_PRIVATE);
        String classn = sp.getString("sClass", "");
        String section = sp.getString("sSection", "");
        String link_student = JsonURL + classn + "/" + section;
        Log.d("link", link_student);


        recyclerView = root.findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        testList = new ArrayList<>();

        sendRequest(link_student);

        handler = new Handler(getActivity().getMainLooper());
        return root;
    }

    public void sendRequest(final String link_student) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, link_student,null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        StudentAssingmentSet personUtils = new StudentAssingmentSet();


                        try {
                            JSONArray studentinfo= response.getJSONArray("assignments");
                            for (int i = 0; i < studentinfo.length(); i++) {
                                JSONObject studentObj = (JSONObject) studentinfo.get(i);

                                personUtils.setTestDate(studentObj.getString("title"));
                                personUtils.setTestDetails(studentObj.getString("date"));
                                personUtils.setTestTitle(studentObj.getString("details"));


                                Log.d("data",studentObj.toString());

                                testList.add(personUtils);

                            }


                            mAdapter = new CustomRecyclerAssingment(getActivity(), testList);

                            recyclerView.setAdapter(mAdapter);

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("Volley", "Error");

                    }
                });

        rq.add(jsonObjectRequest);

    }


}