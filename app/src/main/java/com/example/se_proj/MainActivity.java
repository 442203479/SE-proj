//Profile Page
package com.example.se_proj;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {
    Button add_btn;

    Button profile;

    TextView myUsername;
    String username;



    ListView lv_myPostsList;
    ArrayAdapter myPostsArrayAdapter;
    DatabaseHelper dataBaseHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_myPostsList = findViewById(R.id.lv_myPosts);
        dataBaseHelper = new DatabaseHelper(MainActivity.this);
        ShowMyPosts(dataBaseHelper);

        add_btn = findViewById(R.id.addbtn);

        profile = findViewById(R.id.mypostbtn);
        myUsername = findViewById(R.id.myUsername);

        username=myUsername.getText().toString();

        add_btn.setOnClickListener(v ->

        {


            Intent intent = new Intent(getApplicationContext(), AddPost.class);
            startActivity(intent);

        });


        profile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v){
                dataBaseHelper = new DatabaseHelper(MainActivity.this);
                ShowMyPosts(dataBaseHelper);

            }
        });


    }


    private void ShowMyPosts(DatabaseHelper dataBaseHelper) {
        myPostsArrayAdapter = new
                ArrayAdapter<userPosts>(MainActivity.this,
                android.R.layout.simple_list_item_1, dataBaseHelper.getAllMyPosts(username));
        lv_myPostsList.setAdapter(myPostsArrayAdapter);
    }
}

class userPosts
{
    String userID;
    String img_url;

    String desc;

    String timestamp;


    public userPosts(String userID, String img_url, String desc, String timestamp) {
        this.userID = userID;
        this.img_url = img_url;
        this.desc = desc;
        this.timestamp = timestamp;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {


        return img_url + '\n' +
                "Description: " + desc + '\n' +
                "Timestamp: " + timestamp + '\n';
    }
}

