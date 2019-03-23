package com.example.thezo.fyp;

import android.app.ProgressDialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> messages_AL = new ArrayList<>();
    private ArrayList<SingleMessage> singleMessageAL = new ArrayList<>();
    private String number_plate, numberPlateFormatted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Getting the identity from the previous activity
        number_plate = getIntent().getExtras().getString("numberPlate");
        numberPlateFormatted = number_plate.substring(0,2) + '-' + number_plate.substring(2,4) + '-' + number_plate.substring(4);
        Log.d("ChatActivity", "onCreate: "+numberPlateFormatted);



        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        String formattedDate = df.format(c);
        formattedDate = formattedDate.replace('p','P');
        formattedDate = formattedDate.replace('m','M');
        formattedDate = formattedDate.replace('a','A');
        formattedDate = formattedDate.replace(".","");

        Log.d("Date", "DateTime: "+ formattedDate );







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

        mAdapter = new MyRecyclerViewAdapter(this, singleMessageAL);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // use a linear layout manager
//        layoutManager = new LinearLayoutManager(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("messages");
//        SingleMessage msg = new SingleMessage("02-MH-2472", "server", "First message");
//        //myRef.push().setValue(msg);
//        SingleMessage msg2 = new SingleMessage("02-MH-2472", "server", "Second message");
//        //myRef.push().setValue(msg2);

        // Read from the database
        final ProgressDialog Dialog = new ProgressDialog(this);
        Dialog.setMessage("Retrieving messages...");
        Dialog.show();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d("Messages ", "Value is: " + value);
                singleMessageAL.clear();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    String receiver = (String) messageSnapshot.child("reciever").getValue();
                    String sender = (String) messageSnapshot.child("sender").getValue();
                    String messageToSend = (String) messageSnapshot.child("message").getValue();
                    String conversation = (String) messageSnapshot.child("conversation").getValue();
                    String timestamp = (String) messageSnapshot.child("timestamp").getValue();

                    if(conversation.equals("server "+numberPlateFormatted)){
                        SingleMessage sm = new SingleMessage(sender, receiver, messageToSend, conversation, timestamp);
                        singleMessageAL.add(sm);
                    }

                }
//                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                mAdapter.notifyDataSetChanged();
                Dialog.hide();
//                Log.d("DateTime ", "Value is: " + map);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error: ", "Failed to read value.", error.toException());
            }
        });

        // specify an adapter (see also next example)
    }

}
