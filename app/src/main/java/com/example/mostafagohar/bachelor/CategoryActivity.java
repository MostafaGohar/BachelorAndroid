package com.example.mostafagohar.bachelor;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class CategoryActivity extends BaseActivity {
    static Application app;
    static int category_id;
    static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        app = getApplication();
        context = getApplicationContext();
        //final ListView listItems = (ListView)findViewById(R.id.CategoryPosts);
        final String[] categories={"AI", "Time Travel", "Computer Vision", "Web Development", "Android", "iOS", "Networks",
                "AI", "Time Travel", "Computer Vision", "Web Development", "Android", "iOS", "Networks",
                "AI", "Time Travel", "Computer Vision", "Web Development", "Android", "iOS", "Networks"};
        //final String[] posts={"This is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test post","This is also a test post","I don't like this app","Hello, is anyone there?",
        //"Lorem Ipsum","This is a test post","This is also a test post","I don't like this app","Hello, is anyone there?",
        //"Lorem Ipsum"};

        String[] timelinePosts={"This is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test post","This is also a test post","I don't like this app","Hello, is anyone there?",
                "Lorem Ipsum","This is a test post","This is also a test post","I don't like this app","Hello, is anyone there?",
                "Lorem Ipsum"};
//        ArrayList image_details = getListData();

//        lv1.setAdapter(new CustomListAdapter(this, image_details));

        Intent categoryIntent= getIntent();
        Bundle b = categoryIntent.getExtras();
        category_id = (int) b.get("category_id");

        /////////////////////
        RequestQueue queue = Volley.newRequestQueue(CategoryActivity.this);

        String posts_url="https://bachelor-sohaghareb.c9users.io/api/categories/"+category_id;
        JsonObjectRequest posts_request=new JsonObjectRequest(Request.Method.GET, posts_url, "", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String data= response.getJSONObject("category").toString();
                    Log.v("CATAT", response.toString());
                    Gson gson=new Gson();
                    Category category = gson.fromJson(data,Category.class);
                    Log.v("NAME", ""+category.getId());
                    setTitle(category.getName());
//                    String data2 = response.getJSONObject("category").getJSONArray("posts").toString();
//                    final ArrayList < Post > list = gson.fromJson(data2, new TypeToken<ArrayList<Post>>() {}.getType());
//                    final ArrayList<Object> results = new ArrayList<>();
//                    Post postData = new Post();
//                    for(int i = 0;i<list.size();i++){
//                        postData.setContent(list.get(i).getContent());
//                        postData.setCreated_at(list.get(i).getCreated_at());
//                        postData.setUser(list.get(i).getUser());
//                        results.add(PostActivity.clonePost(postData));
//                    }
//                    lv1.setAdapter(new CustomListAdapter(getApplicationContext(), results));
//                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
//                            Intent intent = new Intent(getApplicationContext(), PostActivity.class);
//                            intent.putExtra("post_id", list.get(position).getId());//(Post)results.get(position));
//                            startActivity(intent);
//                        }
//                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CategoryActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

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

        ////////////////////////////////

        String posts_url2="https://bachelor-sohaghareb.c9users.io/api/categories/posts/"+category_id;
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, posts_url2, "", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    String data= response.toString();
                    Gson gson=new Gson();
                    final ArrayList < Post > list = gson.fromJson(data, new TypeToken<ArrayList<Post>>() {}.getType());
                    JSONObject x = (JSONObject) response.get(0);
                    final ArrayList<Object> results = new ArrayList<>();
                    Post postData = new Post();
                    for(int i = 0;i<list.size();i++){
                        postData.setContent(list.get(i).getContent());
                        postData.setTitle(list.get(i).getTitle());
                        postData.setCreated_at(list.get(i).getCreated_at());
                        postData.setUser(list.get(i).getUser());
                        results.add(PostActivity.clonePost(postData));
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.CategoryPosts);
                    lv1.setAdapter(new CustomListAdapter(getApplicationContext(), results));
                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                            Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                            intent.putExtra("post_id", list.get(position).getId());//(Post)results.get(position));
                            startActivity(intent);
                        }
                    });
                    PostActivity.setListViewHeightBasedOnChildren(lv1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CategoryActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
        /////////////////////////////

        final Button postButton = (Button)findViewById(R.id.postButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText c = (EditText) findViewById(R.id.postText);
                EditText c2 = (EditText) findViewById(R.id.postTitle);
                new HttpPost2().execute(new String[]{c2.getText().toString(), c.getText().toString()});

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }
    private ArrayList getListData() {
        ArrayList<Comment> results = new ArrayList<>();
        Comment commentData = new Comment();
        String[] timelinePosts={"This is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test post","This is also a test post","I don't like this app","Hello, is anyone there?",
                "Lorem Ipsum","This is a test post","This is also a test post","I don't like this app","Hello, is anyone there?",
                "Lorem Ipsum"};
        String [] posters = {"Mohamed", "Ahmad", "Menna", "Amal", "Weam", "Soha", "Slim", "Hossam","Ayman","Wael"};
//        for(int i = 0;i<timelinePosts.length;i++){
//            commentData.setContent(timelinePosts[i]);
//            //commentData.setCommenter(posters[i]);
//            commentData.setCreated_at("May 26, 2013, 13:35");
//
//            results.add(PostActivity.cloneComment(commentData));
//        }



        // Add some more dummy data for testing
       return results;
    }
    public static Application getApp(){return CategoryActivity.app;}
    public static int getCategory_id(){return CategoryActivity.category_id;}
    public static Context getContext(){return CategoryActivity.context;}

}
class HttpPost2 extends AsyncTask<String, Void, Integer> {


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
            post.setDestid(CategoryActivity.getCategory_id());
            post.setContent(wordList.get(1));
            post.setTitle(wordList.get(0));
            post.setDesttype(2);
            User poster = new User();
            poster.setId(((MyApplication) CategoryActivity.getApp()).getCurrent_user());
            post.setUser(poster);


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
        intent.setClass(CategoryActivity.getContext(), PostActivity.class);
        intent.putExtra("post_id", result);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        CategoryActivity.getContext().startActivity(intent);

    }

}