package com.example.mostafagohar.bachelor;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.facebook.Profile;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class ExtraInfo extends AppCompatActivity {
    static Context context;
    static Application app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        app = getApplication();
        setContentView(R.layout.activity_extra_info);
        final EditText gucemail = (EditText) findViewById(R.id.gucemail);
        final EditText gucid = (EditText)findViewById(R.id.gucid);
        final EditText location = (EditText) findViewById(R.id.location);
        final RadioButton male = (RadioButton)findViewById(R.id.maleRadio);
        final RadioButton female = (RadioButton)findViewById(R.id.femaleRadio);
        final DatePicker dob = (DatePicker) findViewById(R.id.dob);
        final Button button = (Button)findViewById(R.id.extraInfoButton);
        final TextView gucemail_text = (TextView) findViewById(R.id.Email);
        final TextView gucid_text = (TextView) findViewById(R.id.gucid_text);
        final TextView dob_text = (TextView) findViewById(R.id.dob_text);
        final TextView gender_text = (TextView) findViewById(R.id.gender_text);
        final TextView location_text = (TextView) findViewById(R.id.location_text);

        Intent intent= getIntent();
        Bundle b = intent.getExtras();

        final Profile profile = (Profile) b.get("profile");
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(male.isChecked())
                    female.setChecked(false);
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (female.isChecked())
                    male.setChecked(false);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int check = 0;
                int gender = -1;
                if(gucemail.getText().toString().equals("")){
                    gucemail_text.setTextColor(Color.RED);check = 1;
                }else{
                    gucemail_text.setTextColor(Color.parseColor("#006600"));
                }
                if(gucid.getText().toString().equals("")){
                    gucid_text.setTextColor(Color.RED);check = 1;
                }else{
                    gucid_text.setTextColor(Color.parseColor("#006600"));
                }
                /*if(dob.getText().toString().equals("")){
                    dob_text.setTextColor(Color.RED);check = 1;
                }else{
                    dob_text.setTextColor(Color.parseColor("#006600"));
                }*/
                if((!male.isChecked()) && (!female.isChecked())){
                    gender_text.setTextColor(Color.RED);check = 1;
                }
                else{
                    gender_text.setTextColor(Color.parseColor("#006600"));
                }
                if(location.getText().toString().equals("")){
                    location_text.setTextColor(Color.RED);check = 1;
                }else{
                    location_text.setTextColor(Color.parseColor("#006600"));
                }
                if(male.isChecked())
                    gender = 1;
                else
                    gender = 0;
                if(check == 0){
                    new LoginPost().execute(new String[]{profile.getId(), profile.getFirstName(), profile.getLastName(), gucemail.getText().toString(),
                            gucid.getText().toString(), dob.getDayOfMonth() + "/" + (dob.getMonth() + 1) + "/" + dob.getYear(), gender + "", location.getText().toString()});

                }
            }
        });
    }

    public static Context getContext(){return ExtraInfo.context;}
    public static Context getApp(){return ExtraInfo.app;}
}
class LoginPost extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... params) {
        User user = null;
        String userid = null;
        try {
            String createPostUrl = "https://bachelor-sohaghareb.c9users.io/api/users";
            RestTemplate restTemplatePost = new RestTemplate();

            restTemplatePost.getMessageConverters().add(new StringHttpMessageConverter());
            StringBuilder builder = new StringBuilder();
            for (String s : params) {
                builder.append(s);
            }
            List<String> wordList = Arrays.asList(params);
            user = new User();
            Log.v("DATE",wordList.get(5));
            user.setUid(wordList.get(0));
            user.setFname(wordList.get(1));
            user.setLname(wordList.get(2));
            user.setEmail(wordList.get(3));
            user.setGucid(wordList.get(4));
            user.setDob(wordList.get(5));
            if (wordList.get(6).equals("1"))
                user.setGender(true);
            else
                user.setGender(false);
            user.setLocation(wordList.get(7));

            Gson gson = new Gson();
            String request = gson.toJson(user);
            Log.v("HEY", request.toString());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(request, headers);
            Log.v("HUA", entity.toString());
            ResponseEntity<String> response = restTemplatePost.postForEntity(createPostUrl, entity, String.class);
            String restcall = response.getBody();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = new JSONObject(restcall);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                userid =jsonObject.get("id").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (RestClientException e) {
            Log.e("POST", e.getLocalizedMessage());
        }
        return userid;
    }
    protected void onPostExecute(String result) {
        //intent.setClass(FacebookHelper.getContext(), MyProfileActivity.class);
        ((MyApplication) ExtraInfo.getApp()).setCurrent_user(Integer.parseInt(result));
        Intent intent = new Intent();
        intent.setClass(ExtraInfo.getContext(), MyProfileActivity.class);
        intent.putExtra("user_id", Integer.parseInt(result));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ExtraInfo.getContext().startActivity(intent);

    }

}
