package com.example.mostafagohar.bachelor;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.facebook.FacebookSdk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Windows on 26-03-2015.
 */
public class MyApplication extends Application {
    public int current_user;
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    /**
     * Call this method inside onCreate once to get your hash key
     */
    public void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("vivz.slidenerd.facebookv40helloworld", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("VIVZ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    public int getCurrent_user() {
        return current_user;
    }

    public void setCurrent_user(int current_user) {
        this.current_user = current_user;
    }
}