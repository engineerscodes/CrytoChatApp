package com.example.crytochat;

import com.example.crytochat.AffineChiper.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.crytochat.Adapter.MessageAdapter;
import com.example.crytochat.Model.Chat;
import com.example.crytochat.Model.Users;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    TextView username;
    ImageView imageView;
    RecyclerView recyclerViewy; //==recyclerViewy
    EditText msg_edittext;
    ImageButton sendBtn;

    FirebaseUser fuser;
    DatabaseReference reference;
    Intent intent;

    MessageAdapter messageAdapter;
    List<Chat> mchat;

   RecyclerView recyclerView; //==recyclerView2

     String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        imageView=findViewById(R.id.imageView_profile);
        username=findViewById(R.id.username05);
         recyclerView =findViewById(R.id.recycler_view);
         msg_edittext=findViewById(R.id.send_text);
         sendBtn=findViewById(R.id.imageButton);



         recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);



        Toolbar toolbar= findViewById(R.id.toolbarprofile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        intent=getIntent();
         userid=intent.getStringExtra("userid");
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("MyUsers").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               Users user=dataSnapshot.getValue(Users.class);
               username.setText(user.getUsername());

               if(user.getImageURL().equals("default")){
                   imageView.setImageResource(R.mipmap.ic_launcher);
               }else {
                   Glide.with(MessageActivity.this)
                           .load(user.getImageURL())
                           .into(imageView);
               }
                readMessages(fuser.getUid(),userid,user.imageURL);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String msg=msg_edittext.getText().toString();
              if(!msg.equals(""))
              {
                  SendMessage(fuser.getUid(),userid,msg);
              }else{
                  Toast.makeText(MessageActivity.this,"Message Is NUll",Toast.LENGTH_SHORT).show();
              }
              msg_edittext.setText("");
            }
        });

    }

    public  void SendMessage(String uid, final String userid, String msg)
    {   System.out.println("------------------------------>"+msg);
        Affine_Ciphers ac=new Affine_Ciphers();
        msg=ac.encryption(msg);
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("sender",uid);
        hashMap.put("receiver",userid);
        hashMap.put("message",msg);
         reference.child("Chats").push().setValue(hashMap);

         final DatabaseReference chatRef=FirebaseDatabase.getInstance()
                 .getReference("ChatList")
                 .child(fuser.getUid())
                 .child(userid);
         chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 if(!dataSnapshot.exists()){
                     chatRef.child("id").setValue(userid);
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });




    }

    public void readMessages(final String myid, final String userid, final String imageurl) //reding decryption point
    {
       mchat=new ArrayList<>();
       reference=FirebaseDatabase.getInstance().getReference("Chats");
       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              mchat.clear();
              for(DataSnapshot snapshot:dataSnapshot.getChildren())
              {
                Chat chat=snapshot.getValue(Chat.class);
                if(chat.getReceiver().equals(myid) && chat.getSender().equals(userid)||
                        chat.getReceiver().equals(userid) && chat.getSender().equals(myid))
                {   String input=chat.getMessage();
                    Affine_Ciphers ac=new Affine_Ciphers();
                    input=ac.decode(input);
                    chat.setMessage(input);
                    mchat.add(chat); }
              }
              messageAdapter=new MessageAdapter(MessageActivity.this,mchat,imageurl);
              recyclerView.setAdapter(messageAdapter);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }
}