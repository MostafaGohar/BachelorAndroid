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


public class Following extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);
        final ListView listItems = (ListView)findViewById(R.id.TimeLine);
        final String[] following={"Ahmed Mohamed","Mohamed Ahmed","Amal Lasheen","Weam Mahmoud",
                "Soha Abuhamam","Ahmed Mohamed","Mohamed Ahmed","Amal Lasheen","Weam Mahmoud","Soha Abuhamam",
                "Ahmed Mohamed","Mohamed Ahmed","Amal Lasheen","Weam Mahmoud","Soha Abuhamam"};
        listItems.setAdapter(new ArrayAdapter<String>(Following.this, android.R.layout.simple_list_item_1, following));
        final Button followersButton = (Button) findViewById(R.id.followersButton);
        final Button timelineButton = (Button) findViewById(R.id.timelineButton);
        final Button categoriesButton = (Button) findViewById(R.id.categoriesButton);

        followersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent followingIntent = new Intent(getApplicationContext(), Following.class);
                startActivity(followingIntent);
            }
        });
        timelineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
            }
        });
        categoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getApplicationContext(), Categories.class);
                startActivity(mainIntent);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

}
