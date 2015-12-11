package com.example.mostafagohar.bachelor;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class FacebookHelper extends ActionBarActivity {

    static Context context;

    private static final String STATE_SELECTED_FRAGMENT_INDEX = "selected_fragment_index";
    public static final String FRAGMENT_TAG = "fragment_tag";
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context  = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebookhelper);
        mFragmentManager = getSupportFragmentManager();

        Fragment fragment = mFragmentManager.findFragmentByTag(FRAGMENT_TAG);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(android.R.id.content, new FragmentSimpleLoginButton(), FRAGMENT_TAG);
        transaction.commit();

    }
    public static Context getContext(){return MyProfileActivity.context;}
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}
