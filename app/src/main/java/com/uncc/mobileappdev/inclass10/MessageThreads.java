package com.uncc.mobileappdev.inclass10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;
import java.util.ArrayList;

import static com.uncc.mobileappdev.inclass10.ChatHttpService.TOKEN_THREADS_BUNDLE_KEY;

public class MessageThreads extends AppCompatActivity  {

    TextView userName;
    ImageButton logout, imageButtonAdd;
    EditText InputTopic;
    TokenResponse tokenResponse;
    ThreadList threadList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Topics> data = new ArrayList<>();
    ChatHttpService chatHttpService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_threads);
        setTitle("Message Threads");

        userName = findViewById(R.id.UsernameMessage);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(ChatHttpService.TOKEN_RESPONSE_INTENT_KEY);
        tokenResponse = (TokenResponse) bundle.getSerializable(ChatHttpService.TOKEN_RESPONSE_BUNDLE_KEY);
        threadList =(ThreadList) bundle.getSerializable(ChatHttpService.TOKEN_THREADS_BUNDLE_KEY);

        userName.setText(tokenResponse.getUser_fname());

        imageButtonAdd = (ImageButton) findViewById(R.id.imageButtonAdd);
        InputTopic = (EditText) findViewById(R.id.InputTopic);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        chatHttpService = new ChatHttpService(this);

        ArrayList<String> truncatedThreadList = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            truncatedThreadList.add(threadList.getThreads().get(i).getTitle());
            Topics topic = new Topics(threadList.getThreads().get(i).getTitle());
            data.add(topic);
        }

        mLayoutManager = new LinearLayoutManager(MessageThreads.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MsgThreadAdapter(data);
        mRecyclerView.setAdapter(mAdapter);


        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String st = InputTopic.getText().toString();
               Thread thread = getNewlyCreatedThread();

                data.add(new Topics(st));
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                mRecyclerView.setHasFixedSize(true);

                // use a linear layout manager
//                mLayoutManager = new LinearLayoutManager(MessageThreads.this);
//                mRecyclerView.setLayoutManager(mLayoutManager);

                // specify an adapter (see also next example)
                mAdapter = new MsgThreadAdapter(data);
                mRecyclerView.setAdapter(mAdapter);
                chatHttpService.addThead(thread, tokenResponse.getToken());



            }
        });

        logout = findViewById(R.id.imageButtonLogOff);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteToken();
                Intent intent = new Intent(MessageThreads.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void deleteToken() {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(getString(R.string.preference_file_key));
        editor.commit();
    }

    protected Thread getNewlyCreatedThread(){
        Thread thread = new Thread();
        thread.setTitle(InputTopic.getText().toString());
        thread.setUser_fname(tokenResponse.getUser_fname());
        thread.setUser_lname(tokenResponse.getUser_lname());
        thread.setUser_id(tokenResponse.getUser_id());
        thread.setCreated_at("Today");
        thread.setId("1235436");

        return thread;
    }
}
