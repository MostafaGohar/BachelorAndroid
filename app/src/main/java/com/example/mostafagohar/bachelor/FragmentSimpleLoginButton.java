package com.example.mostafagohar.bachelor;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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

import java.util.Arrays;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentSimpleLoginButton extends Fragment {
    static Profile profile = null;
    private TextView mTextDetails;
    private CallbackManager mCallbackManager;
    private AccessTokenTracker mTokenTracker;
    private ProfileTracker mProfileTracker;
    static Context context;
    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.d("VIVZ", "onSuccess");
            AccessToken accessToken = loginResult.getAccessToken();
            profile = Profile.getCurrentProfile();
            mTextDetails.setText(profile.getId());


            new CheckLogin().execute(new String[]{profile.getId()});

        }


        @Override
        public void onCancel() {
            Log.d("VIVZ", "onCancel");
        }

        @Override
        public void onError(FacebookException e) {
            Log.d("VIVZ", "onError " + e);
        }
    };


    public FragmentSimpleLoginButton() {
    }

    public static Context getContext2() {
        return context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

        mCallbackManager = CallbackManager.Factory.create();
        setupTokenTracker();
        setupProfileTracker();

        mTokenTracker.startTracking();
        mProfileTracker.startTracking();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_simple_login_button, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setupTextDetails(view);
        setupLoginButton(view);
    }

    /*@Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        LoginWithFacebook(profile);
        //mTextDetails.setText(constructWelcomeMessage(profile));
    }
*/
    private void LoginWithFacebook(Profile profile) {
        User user = new User();
        user.setFname(profile.getFirstName());
        user.setLname(profile.getLastName());
        user.setUid(profile.getId());

    }

    @Override
    public void onStop() {
        super.onStop();
        mTokenTracker.stopTracking();
        mProfileTracker.stopTracking();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void setupTextDetails(View view) {
        mTextDetails = (TextView) view.findViewById(R.id.text_details);
    }

    private void setupTokenTracker() {
        mTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.d("VIVZ", "" + currentAccessToken);
            }
        };
    }

    private void setupProfileTracker() {
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                Log.d("VIVZ", "" + currentProfile);
                mTextDetails.setText(constructWelcomeMessage(currentProfile));
            }
        };
    }

    private void setupLoginButton(View view) {
        LoginButton mButtonLogin = (LoginButton) view.findViewById(R.id.login_button);
        mButtonLogin.setFragment(this);
        mButtonLogin.setReadPermissions("user_friends");
        mButtonLogin.registerCallback(mCallbackManager, mFacebookCallback);
    }

    private String constructWelcomeMessage(Profile profile) {
        StringBuffer stringBuffer = new StringBuffer();
        if (profile != null) {
            stringBuffer.append("Welcome " + profile.getId());
        }
        return stringBuffer.toString();
    }


    public static void goToExtraInfo() {
        Intent intent = new Intent();
        intent.setClass(FragmentSimpleLoginButton.getContext2(), ExtraInfo.class);
        intent.setAction(ExtraInfo.class.getName());
        intent.putExtra("profile", profile);
        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        FragmentSimpleLoginButton.getContext2().startActivity(intent);
    }

    public static void goToProfile(String result) {

        Intent intent = new Intent();
        intent.setClass(FragmentSimpleLoginButton.getContext2(), MyProfileActivity.class);
        intent.setAction(MyProfileActivity.class.getName());
        intent.putExtra("user_id", Integer.parseInt(result));
        intent.putExtra("set_current_user", true);
        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        FragmentSimpleLoginButton.getContext2().startActivity(intent);
    }
}
class CheckLogin extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... params) {
        String userid = null;
        String uid = null;
        try {
            String createPostUrl = "https://bachelor-sohaghareb.c9users.io/api/users/exists";
            RestTemplate restTemplatePost = new RestTemplate();

            restTemplatePost.getMessageConverters().add(new StringHttpMessageConverter());
            StringBuilder builder = new StringBuilder();
            for (String s : params) {
                builder.append(s);
            }
            List<String> wordList = Arrays.asList(params);
            User user = new User();

            uid = wordList.get(0);
            user.setUid(uid);
            Gson gson = new Gson();
            String request = gson.toJson(user);
            Log.v("HEY", request.toString());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(request, headers);
            Log.v("HUA", entity.toString());
            ResponseEntity<String> response = restTemplatePost.postForEntity(createPostUrl, entity, String.class);
            String restcall = response.getBody();
            Log.v("RESPONSEEEE", restcall);
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
        if(result == null){
            FragmentSimpleLoginButton.goToExtraInfo();
        }else{
            FragmentSimpleLoginButton.goToProfile(result);
        }


    }



}
