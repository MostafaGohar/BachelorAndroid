package com.example.mostafagohar.bachelor;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
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
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class PostActivity extends BaseActivity {
    static int post_id;
    static Application app;
    static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        app = getApplication();
        context = getApplicationContext();
        Intent intent= getIntent();
        //Bundle b = intent.getExtras();
        final TextView title = (TextView) findViewById(R.id.PostTitle);
        final TextView text = (TextView) findViewById(R.id.PostText);
        final TextView poster = (TextView) findViewById(R.id.poster);
        final Button shareButton = (Button)findViewById(R.id.shareButton);
        final String[] titles={"This is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test postThis is a test post","This is also a test post","I don't like this app","Hello, is anyone there?",
                "Lorem Ipsum","This is a test post","This is also a test post","I don't like this app","Hello, is anyone there?",
                "Lorem Ipsum"};

//        ArrayList image_details = getListData();
//        final ListView lv1 = (ListView) findViewById(R.id.comment_list);
//        lv1.setAdapter(new CustomListAdapter(this, image_details));
//        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
//                Object o = lv1.getItemAtPosition(position);
//                Comment comment = (Comment) o;
//                Toast.makeText(PostActivity.this, "Selected :" + " " + comment, Toast.LENGTH_LONG).show();
//            }
//        });
        Bundle b = intent.getExtras();
        //final Post j = (Post) b.get("post");
        post_id = (Integer) b.get("post_id");
        final ListView lv1 = (ListView) findViewById(R.id.comment_list);
        RequestQueue queue = Volley.newRequestQueue(PostActivity.this);

        final Button commentButton = (Button)findViewById(R.id.commentButton);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText c = (EditText) findViewById(R.id.commentText);
                new HttpPostComment().execute(new String[]{c.getText().toString()});

            }
        });
//            title.setText(j.getTitle());
//            text.setText(j.getContent());
//            poster.setText("by " + j.getUser().getFname());
//            setTitle(j.getTitle());
//            String[] titleTitle = j.getContent().split(" ");
//            switch (titleTitle.length) {
//                case 1:
//                    setTitle(j.getTitle());
//                    break;
//                case 2:
//                    setTitle(j.getTitle());
//                    break;
//                default:
//                    setTitle(titleTitle[0]+" "+titleTitle[1]+" "+titleTitle[2]+"...");
//                    break;
//            }

        final String url="https://bachelor-sohaghareb.c9users.io/api/posts/"+post_id;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Gson gson=new Gson();
                    String data2= response.getJSONObject("post").toString();
                    Post thepost = gson.fromJson(data2,Post.class);
                    //Toast.makeText(PostActivity.this, url, Toast.LENGTH_LONG).show();
                    String data= response.getJSONObject("post").getJSONArray("comments").toString();
                    title.setText(thepost.getTitle());
                    text.setText(thepost.getContent());
                    poster.setText("by " + thepost.getUser().getFname());
                    setTitle(thepost.getTitle());
//                    if(thepost.getDesttype() == 1){
//
//                    }else{
//                        if(thepost.getDesttype() == 2){
//
//                        }
//                    }
                    //String json = gson.toJson(response, new TypeToken<ArrayList<Post>>() {}.getType());
                    ArrayList < Comment > list = gson.fromJson(data, new TypeToken<ArrayList<Comment>>() {}.getType());
                    ArrayList<Object> results = new ArrayList<>();
                    Comment commentData = new Comment();
                    for(int i = 0;i<list.size();i++){
                        commentData.setContent(list.get(i).getContent());
                        //commentData.setCommenter(posters[i]);
                        commentData.setCreated_at(list.get(i).getCreated_at());
                        commentData.setUser(list.get(i).getUser());

                        results.add(PostActivity.cloneComment(commentData));
                    }

                    //ArrayList image_details = getListData();


                    lv1.setAdapter(new CustomListAdapter(getApplicationContext(), results));
//                    ArrayList <Object> comments2 = new ArrayList<>();
//                    for(int i = 0;i<j.getComments().size();i++){
//                        comments2.add((Object)(j.getComments().get(i)));
//                    }
//                    //JSONObject x = (JSONObject)response.get(0);
//                    lv1.setAdapter(new CustomListAdapter(getApplicationContext(), comments2));
                    setListViewHeightBasedOnChildren(lv1);
                    //Toast.makeText(PostActivity.this,x.getInt("id")+"", Toast.LENGTH_LONG).show();


                } catch (Exception e) {
                    e.printStackTrace();
                }




                //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(PostActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

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

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareTextUrl("https://bachelor-sohaghareb.c9users.io/posts/"+post_id);

            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
            if(i == listAdapter.getCount()-1)
                totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
//    private ArrayList getListData() {
//        ArrayList<Comment> results = new ArrayList<>();
//        Comment commentData = new Comment();
//       String[] comments ={"This is a stupid post","Have you tried using google?", "I don't this we can answer that", "Ask Slim"
//                ,"You've chosen then wrong project","This is a very interesting question, but I refuse to answer because you misspelled 'your'",
//                "No one knows whether time travel is possible or not", "Who's to say whether what you said is true or not?",
//                "This is not for me","I doubt it","Repost","Repost"};
//        String[] commenters ={"Mohamed","Ahmad", "Amal", "Menna","Soha","Mostafa", "Wael", "Youssef","Slim","Weam","Ayman","Hossam"};
//        for(int i = 0;i<comments.length;i++){
//            commentData.setComment(comments[i]);
//            commentData.setCommenter(commenters[i]);
//            commentData.setDate("May 26, 2013, 13:35");
//
//            results.add(cloneComment(commentData));
//        }
//
//
//
//        // Add some more dummy data for testing
//        return results;
//    }
    public static Comment cloneComment(Comment comment) {
        Comment new_comment = new Comment();
        new_comment.setUser(comment.getUser());
        new_comment.setContent(comment.getContent());
        new_comment.setCreated_at(comment.getCreated_at());
        return new_comment;
    }
    public static Post clonePost(Post post) {
        Post new_post = new Post();
        new_post.setUser(post.getUser());
        new_post.setContent(post.getContent());
        new_post.setCreated_at(post.getCreated_at());
        new_post.setTitle(post.getTitle());
        return new_post;
    }
    public static Application getApp(){return PostActivity.app;}
    public static Context getContext(){return PostActivity.context;}
    public static int getPost_id(){return PostActivity.post_id;}


    private void shareTextUrl(String WebUrl) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, WebUrl);

        startActivity(Intent.createChooser(share, "Share link!"));
    }
}
class HttpPostComment extends AsyncTask<String, Void, Integer> {


    @Override
    protected Integer doInBackground(String... params) {
        Comment comment = null;
        Post postC = new Post();
        int commentid = 0;
        try {
            String createPostUrl = "https://bachelor-sohaghareb.c9users.io/api/comments";
            RestTemplate restTemplatePost = new RestTemplate();

            restTemplatePost.getMessageConverters().add(new StringHttpMessageConverter());
            StringBuilder builder = new StringBuilder();
            for (String s : params) {
                builder.append(s);
            }
            List<String> wordList = Arrays.asList(params);

            comment = new Comment();
            comment.setContent(wordList.get(0));
            User poster = new User();
            poster.setId(((MyApplication) PostActivity.getApp()).getCurrent_user());
            comment.setUser(poster);
            postC = new Post();
            postC.setId(PostActivity.getPost_id());
            comment.setPost(postC);


            Gson gson = new Gson();
            String request = gson.toJson(comment);
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


        } catch (RestClientException e) {
            Log.e("POST", e.getLocalizedMessage());
        }
        return postC.getId();
    }
    protected void onPostExecute(Integer result) {
        Intent intent = new Intent();
        intent.setClass(PostActivity.getContext(), PostActivity.class);
        intent.putExtra("post_id", result);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PostActivity.getContext().startActivity(intent);

    }

}
