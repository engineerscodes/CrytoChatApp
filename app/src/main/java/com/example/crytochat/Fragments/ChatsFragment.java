package com.example.crytochat.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crytochat.Adapter.UserAdapter;
import com.example.crytochat.Model.ChatList;
import com.example.crytochat.Model.Users;
import com.example.crytochat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChatsFragment extends Fragment {

    public ChatsFragment() {
        // Required empty public constructor
    }
    public UserAdapter userAdapter;
    public List<Users> mUsers;

    FirebaseUser fuser;
    DatabaseReference reference;

    public List<ChatList> userList;

    public RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // return inflater.inflate(R.layout.fragment_chats, container, false);
        View view=inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerView=view.findViewById(R.id.recycler_view2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser= FirebaseAuth.getInstance().getCurrentUser();
        userList =new ArrayList<>();

        reference= FirebaseDatabase.getInstance().getReference("ChatList")
                .child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  userList.clear();
                  //loop
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    ChatList chatList=snapshot.getValue(ChatList.class);
                    userList.add(chatList);
                }
                chatLists();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
          return view;
    }

    public  void chatLists()
    {
        mUsers=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("MyUsers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             mUsers.clear();
             for(DataSnapshot snapshot:dataSnapshot.getChildren())
             {
                 Users user=snapshot.getValue(Users.class);
                 for(ChatList chatlist:userList)
                 {
                     if(user.getId().equals(chatlist.getId())){
                         mUsers.add(user);
                     }
                 }
             }

             userAdapter=new UserAdapter(getContext(),mUsers);
             recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}