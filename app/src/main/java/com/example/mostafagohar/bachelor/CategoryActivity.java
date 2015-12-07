package com.example.mostafagohar.bachelor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
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
          final ListView lv1 = (ListView) findViewById(R.id.CategoryPosts);
//        lv1.setAdapter(new CustomListAdapter(this, image_details));

        Intent categoryIntent= getIntent();
        Bundle b = categoryIntent.getExtras();
        int categories_id = (int) b.get("categories_id");

        /////////////////////
        RequestQueue queue = Volley.newRequestQueue(CategoryActivity.this);

        String posts_url="https://bachelor-sohaghareb.c9users.io/api/categories/"+categories_id;
        JsonObjectRequest posts_request=new JsonObjectRequest(Request.Method.GET, posts_url, "", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String data= response.getJSONObject("category").toString();
                    Gson gson=new Gson();
                    Category category = gson.fromJson(data,Category.class);
                    setTitle(category.getName());
                    String data2 = response.getJSONObject("category").getJSONArray("posts").toString();
                    final ArrayList < Post > list = gson.fromJson(data2, new TypeToken<ArrayList<Post>>() {}.getType());
                    final ArrayList<Object> results = new ArrayList<>();
                    Post postData = new Post();
                    for(int i = 0;i<list.size();i++){
                        postData.setContent(list.get(i).getContent());
                        postData.setCreated_at(list.get(i).getCreated_at());
                        postData.setUser(list.get(i).getUser());
                        results.add(PostActivity.clonePost(postData));
                    }
                    lv1.setAdapter(new CustomListAdapter(getApplicationContext(), results));
                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
}
