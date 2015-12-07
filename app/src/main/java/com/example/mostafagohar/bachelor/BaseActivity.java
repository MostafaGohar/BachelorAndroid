package com.example.mostafagohar.bachelor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Mostafa Gohar on 25/11/2015.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.main_page:
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
                return true;
            case R.id.my_profile:
                Intent profileIntent = new Intent(getApplicationContext(), MyProfileActivity.class);
                startActivity(profileIntent);
                return true;
            case R.id.settings:
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.logout:
                Intent logoutIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(logoutIntent);
                return true;
            case R.id.edit_profile:
                Intent editprofileIntent = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(editprofileIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
