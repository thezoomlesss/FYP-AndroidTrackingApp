package com.example.thezo.fyp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> messages_AL = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        messages_AL.add("Test1");
        messages_AL.add("Test2");
        messages_AL.add("Test3");
        messages_AL.add("Test4");
        messages_AL.add("Test1");
        messages_AL.add("Test2");
        messages_AL.add("Test3");
        messages_AL.add("Test4");
        messages_AL.add("Test1");
        messages_AL.add("Test2");
        messages_AL.add("Test3");
        messages_AL.add("Test4");
        messages_AL.add("Test1");
        messages_AL.add("Test2");
        messages_AL.add("Test3");
        messages_AL.add("Test4");
        messages_AL.add("Test1");
        messages_AL.add("Test2");
        messages_AL.add("Test3");
        messages_AL.add("Test4");
        messages_AL.add("Test1");
        messages_AL.add("Test2");
        messages_AL.add("Test3");
        messages_AL.add("Test4");
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
//        recyclerView.setHasFixedSize(true);

        mAdapter = new MyRecyclerViewAdapter(this, messages_AL);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // use a linear layout manager
//        layoutManager = new LinearLayoutManager(this);

        // specify an adapter (see also next example)
    }

}
