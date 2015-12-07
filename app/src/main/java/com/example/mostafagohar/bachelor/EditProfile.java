package com.example.mostafagohar.bachelor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class EditProfile extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        EditText email = (EditText)findViewById(R.id.ProfileEmail);
        EditText gucid = (EditText)findViewById(R.id.ProfileGUCID);
        EditText name = (EditText)findViewById(R.id.ProfileName);
        EditText dob = (EditText)findViewById(R.id.ProfileDOB);
        EditText gender = (EditText)findViewById(R.id.ProfileGender);
        EditText location = (EditText)findViewById(R.id.ProfileLocation);
        final Button editButton = (Button)findViewById(R.id.editButton);
        name.setHint("Mostafa Gohar");
        email.setHint("mostafa.abdelqader@student.guc.edu.eg");
        gucid.setHint("28-4809");
        dob.setHint("29/12/1994");
        gender.setHint("Male");
        location.setHint("5th Settlement");

    editButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //EDIT PROFILE
        }
    });

    }


}
