package com.example.startup.FragmentStudent;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.startup.Constants.NavigationDrawerConstants;
import com.example.startup.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class Profile extends Fragment {

    public TextView sName, sDob, sAdmissionNo, sRoll, sClass, sSection, sReligion, sNationality, sPresentA, sPermanentA,
            sEmail, sPhone, sFatherN, sFatherO, sMotherN, sMotherO;
    ImageView sPhoto;
    String name;


    private String JsonURL = "https://edfinix.herokuapp.com/api/students/";
    private SharedPreferences sp;
    RequestQueue requestQueue;
    String data = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(NavigationDrawerConstants.TAG_Profile);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_student_profile, null);
        requestQueue = Volley.newRequestQueue(getActivity());



        sName = root.findViewById(R.id.student_profile_student_name);
        sDob = root.findViewById(R.id.student_profile_dob);
        sAdmissionNo = root.findViewById(R.id.profile_student_admission_no);
        sRoll = root.findViewById(R.id.student_profile_rollno);
        sClass = root.findViewById(R.id.student_profile_class);
        sSection = root.findViewById(R.id.student_profile_section);
        sReligion = root.findViewById(R.id.student_profile_religion);
        sNationality = root.findViewById(R.id.student_profile_nationality);
        sPresentA = root.findViewById(R.id.student_profile_present_address);
        sPermanentA = root.findViewById(R.id.student_profile_permanent_address);
        sEmail = root.findViewById(R.id.student_profile_email);
        sPhone = root.findViewById(R.id.student_profile_phone_no);
        sFatherN = root.findViewById(R.id.student_profile_fathers_name);
        sFatherO = root.findViewById(R.id.student_profile_fathers_occupation);
        sMotherN = root.findViewById(R.id.student_profile_mothers_name);
        sMotherO = root.findViewById(R.id.student_profile_mothers_occupation);
        sPhoto = root.findViewById(R.id.student_profile_photo);

        sp = Objects.requireNonNull(this.getActivity()).getSharedPreferences("login", MODE_PRIVATE);

        String value = sp.getString("username", "");
        //Log.d(TAG, value);
        String link_student = JsonURL+value;
        Log.d(TAG, link_student);
        viewprofile(link_student);
        return root;
    }

    public void viewprofile(String link_student) {
       // sName.setText("hello");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, link_student,null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray studentinfo= response.getJSONArray("student");
                            JSONObject studentObj = (JSONObject) studentinfo.get(0);

                            String fname = studentObj.getString("firstName");
                            String lname = studentObj.getString("lastName");
                            String dbirth = studentObj.getString("dateOfBirth");
                            String sadmission = studentObj.getString("admissionNo");
                            String sroll = studentObj.getString("rollNo");
                            String sclass = studentObj.getString("class");
                            String ssection = studentObj.getString("section");
                            String sreligion = studentObj.getString("religion");
                            String snationality = studentObj.getString("nationality");
                            String spresenta = studentObj.getString("presentAddress");
                            String spermanenta = studentObj.getString("permanentAddress");
                            String semail = studentObj.getString("email");
                            String sphone = studentObj.getString("phoneNo");
                            String sfather = studentObj.getString("fatherName");
                            String sfathero = studentObj.getString("fatherOccupation");
                            String smother = studentObj.getString("motherName");
                            String smothero = studentObj.getString("motherOccupation");
                            String sphoto = studentObj.getString("image");

                            //Log.d("name",name);
                            sName.setText(fname+" "+lname);
                            sDob.setText(dbirth);
                            sAdmissionNo.setText(sadmission);
                            sRoll.setText(sroll);
                            sClass.setText(sclass);
                            sSection.setText(ssection);
                            sReligion.setText(sreligion);
                            sNationality.setText(snationality);
                            sPresentA.setText(spresenta);
                            sPermanentA.setText(spermanenta);
                            sEmail.setText(semail);
                            sPhone.setText(sphone);
                            sFatherN.setText(sfather);
                            sFatherO.setText(sfathero);
                            sMotherN.setText(smother);
                            sMotherO.setText(smothero);

                            Glide.with(Objects.requireNonNull(getActivity()))
                                    .load(sphoto)
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(sPhoto);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Log.d("response", String.valueOf(response));
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("Volley", "Error");

                    }
                });

// Access the RequestQueue through your singleton class.
        requestQueue.add(jsonObjectRequest);

    }
}
