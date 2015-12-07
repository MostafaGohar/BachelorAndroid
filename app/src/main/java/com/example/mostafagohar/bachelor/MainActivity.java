package com.example.mostafagohar.bachelor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //final ListView listItems = (ListView)findViewById(R.id.TimeLine);
        final ListView categoriesList = (ListView)findViewById(R.id.Categories);
        //final String[] timelinePosts={"This is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test post","This is also a test post","I don't like this app","Hello, is anyone there?",
          //      "Lorem Ipsum","This is a test post","This is also a test post","I don't like this app","Hello, is anyone there?",
           //     "Lorem Ipsum"};
        //final String[] categories={"AI", "Time Travel", "Computer Vision", "Web Development", "Android", "iOS", "Networks",
                //"AI", "Time Travel", "Computer Vision", "Web Development", "Android", "iOS", "Networks",
                //"AI", "Time Travel", "Computer Vision", "Web Development", "Android", "iOS", "Networks"};
        //listItems.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, timelinePosts));
        //categoriesList.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, categories));
        final Button timelineButton = (Button) findViewById(R.id.postsButton);
        final Button categoriesButton = (Button) findViewById(R.id.categoriesButton);
        final LinearLayout postsLayout = (LinearLayout)findViewById(R.id.PostsLayout);
        final LinearLayout categoriesLayout = (LinearLayout)findViewById(R.id.CategoriesLayout);


       // PostActivity.setListViewHeightBasedOnChildren(lv1);

        timelineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postsLayout.setVisibility(LinearLayout.VISIBLE);
                categoriesLayout.setVisibility(LinearLayout.GONE);
                timelineButton.setBackgroundColor(Color.parseColor("#55AA55"));
                categoriesButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
            }
        });
        categoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postsLayout.setVisibility(LinearLayout.GONE);
                categoriesLayout.setVisibility(LinearLayout.VISIBLE);
                timelineButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
                categoriesButton.setBackgroundColor(Color.parseColor("#55AA55"));
            }
        });



        categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        String url="https://bachelor-sohaghareb.c9users.io/api/posts";
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, "", new Response.Listener<JSONArray>() {
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
                        postData.setCreated_at(list.get(i).getCreated_at());
                        postData.setUser(list.get(i).getUser());
                        results.add(PostActivity.clonePost(postData));
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.TimeLine);
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
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
        //////////////////////////
        String categories_url="https://bachelor-sohaghareb.c9users.io/api/categories";
        JsonArrayRequest categories_request=new JsonArrayRequest(Request.Method.GET, categories_url, "", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    String data= response.toString();
                    Gson gson=new Gson();
                    final ArrayList < Category > list = gson.fromJson(data, new TypeToken<ArrayList<Category>>() {}.getType());
                    JSONObject x = (JSONObject) response.get(0);
                    final ArrayList<String> results = new ArrayList<>();
                    for(int i = 0;i<list.size();i++){
                        results.add(list.get(i).getName());
                    }
                    categoriesList.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, results));
                    categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                            Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                            intent.putExtra("category_id", list.get(position).getId());//(Post)results.get(position));
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
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<String, String>();
                map.put("Accept","application/json");
                return map;
            }
        };
        queue.add(categories_request);
        ///////////////////////

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }
//    private ArrayList getListData() {
//        ArrayList<Comment> results = new ArrayList<>();
//        Comment commentData = new Comment();
//        String[] timelinePosts={"This is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test post","This is also a test post","I don't like this app","Hello, is anyone there?",
//                "Lorem Ipsum","This is a test post","This is also a test post","I don't like this app","Hello, is anyone there?",
//                "Lorem Ipsum"};
//        String [] posters = {"Mohamed", "Ahmad", "Menna", "Amal", "Weam", "Soha", "Slim", "Hossam","Ayman","Wael"};
//        for(int i = 0;i<timelinePosts.length;i++){
//            commentData.setComment(timelinePosts[i]);
//            commentData.setCommenter(posters[i]);
//            commentData.setDate("May 26, 2013, 13:35");
//
//            results.add(PostActivity.cloneComment(commentData));
//        }
//
//
//
//        // Add some more dummy data for testing
//        return results;
//    }
}
