package com.example.mostafagohar.bachelor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyProfileActivity extends BaseActivity{

    static User user = new User();
    static Context context;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_myprofile);
        final TextView email = (TextView)findViewById(R.id.ProfileEmail);
        final TextView gucid = (TextView)findViewById(R.id.ProfileGUCID);
        final TextView name = (TextView)findViewById(R.id.ProfileName);
        final TextView dob = (TextView)findViewById(R.id.ProfileDOB);
        final TextView gender = (TextView)findViewById(R.id.ProfileGender);
        final TextView location = (TextView)findViewById(R.id.ProfileLocation);
        final LinearLayout profileLayout = (LinearLayout)findViewById(R.id.ProfileInfoLayout);
        final LinearLayout postsLayout = (LinearLayout)findViewById(R.id.PostsLayout);
        final LinearLayout followersLayout = (LinearLayout)findViewById(R.id.FollowersLayout);
        final LinearLayout followeesLayout = (LinearLayout)findViewById(R.id.FolloweesLayout);
        final ProgressBar spinner = (ProgressBar)findViewById(R.id.progressBar1);
        Intent intent= getIntent();
        Bundle b = intent.getExtras();
        int user_id =(int) b.get("user_id");
        if(b.containsKey("set_current_user")){
            ((MyApplication) getApplication()).setCurrent_user(user_id);
        }
        final ListView profilePosts = (ListView)findViewById(R.id.ProfilePosts);
        final ListView profileFollowers = (ListView)findViewById(R.id.ProfileFollowers);
        final ListView profileFollowees = (ListView)findViewById(R.id.ProfileFollowees);
        PostActivity.setListViewHeightBasedOnChildren(profilePosts);
        PostActivity.setListViewHeightBasedOnChildren(profileFollowers);
        PostActivity.setListViewHeightBasedOnChildren(profileFollowees);
        RequestQueue queue = Volley.newRequestQueue(MyProfileActivity.this);

        String url="https://bachelor-sohaghareb.c9users.io/api/users/"+user_id;//USER INFO URL
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String data= response.getJSONObject("user").toString();
                    Gson gson=new Gson();
                    //String json = gson.toJson(response, new TypeToken<ArrayList<Post>>() {}.getType());
                    user = gson.fromJson(data, User.class);
//                    ArrayList<Post> list = new ArrayList<>();
                    name.setText(user.getFname()+" "+user.getLname());
                    email.setText(user.getEmail());
                    gucid.setText(user.getGucid());
                    dob.setText(user.getDob());
                    if(user.isGender()){
                        gender.setText("Male");
                    }else{
                        gender.setText("Female");
                    }
                    location.setText(user.getLocation());
                    spinner.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MyProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<String, String>();
                map.put("Accept","application/json");
                return map;
            }
        };
        queue.add(request);
        /////////////////////
        String posts_url="";//TIMELINE POSTS URL
        JsonArrayRequest posts_request=new JsonArrayRequest(Request.Method.GET, posts_url, "", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    String data= response.toString();
                    Gson gson=new Gson();
                    final ArrayList < Post > list = gson.fromJson(data, new TypeToken<ArrayList<Post>>() {}.getType());
                    final ArrayList<Object> results = new ArrayList<>();
                    Post postData = new Post();
                    for(int i = 0;i<list.size();i++){
                        postData.setContent(list.get(i).getContent());
                        postData.setCreated_at(list.get(i).getCreated_at());
                        postData.setUser(list.get(i).getUser());
                        results.add(PostActivity.clonePost(postData));
                    }
                    profilePosts.setAdapter(new CustomListAdapter(getApplicationContext(), results));
                    profilePosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                            Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                            intent.putExtra("post_id", list.get(position).getId());//(Post)results.get(position));
                            startActivity(intent);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MyProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<String, String>();
                map.put("Accept","application/json");
                return map;
            }
        };
        queue.add(posts_request);
        /////////////////
        String followers_url="";//PROFILE FOLLOWERS URL
        JsonArrayRequest followers_request=new JsonArrayRequest(Request.Method.GET, followers_url, "", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    String data= response.toString();
                    Gson gson=new Gson();
                    final ArrayList < User > list = gson.fromJson(data, new TypeToken<ArrayList<User>>() {}.getType());
                    ArrayList<String> names = new ArrayList<>();
                    for(int i = 0;i<list.size();i++){
                        names.add(list.get(i).getFname() +" "+list.get(i).getLname());
                    }
                    profileFollowers.setAdapter(new ArrayAdapter<String>(MyProfileActivity.this, android.R.layout.simple_list_item_1, names));
                    profileFollowers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                            Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
                            intent.putExtra("user_id", list.get(position).getId());
                            startActivity(intent);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MyProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<String, String>();
                map.put("Accept","application/json");
                return map;
            }
        };
        queue.add(followers_request);
        /////////////////
        String followees_url="";//PROFILE FOLLOWEES URL
        JsonArrayRequest followees_request=new JsonArrayRequest(Request.Method.GET, followees_url, "", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    String data= response.toString();
                    Gson gson=new Gson();
                    final ArrayList < User > list = gson.fromJson(data, new TypeToken<ArrayList<User>>() {}.getType());
                    ArrayList<String> names = new ArrayList<>();
                    for(int i = 0;i<list.size();i++){
                        names.add(list.get(i).getFname() +" "+list.get(i).getLname());
                    }
                    profileFollowers.setAdapter(new ArrayAdapter<String>(MyProfileActivity.this, android.R.layout.simple_list_item_1, names));
                    profileFollowers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                            Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
                            intent.putExtra("user_id", list.get(position).getId());
                            startActivity(intent);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MyProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<String, String>();
                map.put("Accept","application/json");
                return map;
            }
        };
        queue.add(followees_request);
        ///////////////////


    final Button postsButton = (Button) findViewById(R.id.postsButton);
    final Button followersButton = (Button) findViewById(R.id.followersButton);
    final Button followeesButton = (Button) findViewById(R.id.followeesButton);
    final Button postButton = (Button)findViewById(R.id.postButton);
    final Button followButton = (Button)findViewById(R.id.followButton);
    if(((MyApplication) this.getApplication()).getCurrent_user() != user_id) {

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (followButton.getText().equals("Follow"))
                    followButton.setText("Unfollow");
                else
                    followButton.setText("Follow");
            }
        });
    }else{

        followButton.setVisibility(View.INVISIBLE);
    }
    postButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText c = (EditText)findViewById(R.id.postText);
            EditText c2 = (EditText)findViewById(R.id.postTitle);
            new HttpPost().execute(new String[]{c2.getText().toString(),c.getText().toString()});
//            StringBuilder sb = new StringBuilder();
//
//            String http = "https://bachelor-sohaghareb.c9users.io/api/posts/create";
//            EditText c = (EditText)findViewById(R.id.postText);
//            HttpPost hp = new HttpPost();
//            hp.doInBackground(c.getText().toString());


        }});
    postsButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ColorDrawable buttonColor = (ColorDrawable) postsButton.getBackground();
            if (buttonColor.getColor() == Color.parseColor("#CCCCCC")) {
                profileLayout.setVisibility(LinearLayout.GONE);
                followersLayout.setVisibility(LinearLayout.GONE);
                followeesLayout.setVisibility(LinearLayout.GONE);
                postsLayout.setVisibility(LinearLayout.VISIBLE);
                postsButton.setBackgroundColor(Color.parseColor("#55AA55"));
                followersButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
                followeesButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
            } else {
                profileLayout.setVisibility(LinearLayout.VISIBLE);
                postsLayout.setVisibility(LinearLayout.GONE);
                postsButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
            }
        }});
    followersButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ColorDrawable buttonColor = (ColorDrawable) followersButton.getBackground();
            if (buttonColor.getColor()  == Color.parseColor("#CCCCCC")) {
                profileLayout.setVisibility(LinearLayout.GONE);
                postsLayout.setVisibility(LinearLayout.GONE);
                followeesLayout.setVisibility(LinearLayout.GONE);
                followersLayout.setVisibility(LinearLayout.VISIBLE);
                followersButton.setBackgroundColor(Color.parseColor("#55AA55"));
                postsButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
                followeesButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
            } else {
                profileLayout.setVisibility(LinearLayout.VISIBLE);
                followersLayout.setVisibility(LinearLayout.GONE);
                followersButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
            }
        }});
    followeesButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ColorDrawable buttonColor = (ColorDrawable) followeesButton.getBackground();
            if (buttonColor.getColor()  == Color.parseColor("#CCCCCC")) {
                profileLayout.setVisibility(LinearLayout.GONE);
                followersLayout.setVisibility(LinearLayout.GONE);
                postsLayout.setVisibility(LinearLayout.GONE);
                followeesLayout.setVisibility(LinearLayout.VISIBLE);
                followeesButton.setBackgroundColor(Color.parseColor("#55AA55"));
                followersButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
                postsButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
            } else {
                profileLayout.setVisibility(LinearLayout.VISIBLE);
                followeesLayout.setVisibility(LinearLayout.GONE);
                followeesButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
            }
        }});
    }

    public static Context getContext(){return MyProfileActivity.context;}


}
class HttpPost extends AsyncTask<String, Void, Integer> {


    @Override
    protected Integer doInBackground(String... params) {
        Post post = null;
        int postid = 0;
        try {
            String createPostUrl = "https://bachelor-sohaghareb.c9users.io/api/posts";
            RestTemplate restTemplatePost = new RestTemplate();

            restTemplatePost.getMessageConverters().add(new StringHttpMessageConverter());
            StringBuilder builder = new StringBuilder();
            for (String s : params) {
                builder.append(s);
            }
            List<String> wordList = Arrays.asList(params);

            post = new Post();
            post.setDestid(MyProfileActivity.user.getId());
            post.setContent(wordList.get(1));
            post.setTitle(wordList.get(0));
            post.setDesttype(1);
            post.setUser(MyProfileActivity.user);


            Gson gson = new Gson();
            String request = gson.toJson(post);
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
                postid = (int)jsonObject.get("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (RestClientException e) {
            Log.e("POST", e.getLocalizedMessage());
        }
        return postid;
    }
    protected void onPostExecute(Integer result) {
        Intent intent = new Intent();
        intent.setClass(MyProfileActivity.getContext(), PostActivity.class);
        intent.putExtra("post_id", result);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyProfileActivity.getContext().startActivity(intent);

    }

}
