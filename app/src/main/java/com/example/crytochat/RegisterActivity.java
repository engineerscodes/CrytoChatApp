package com.example.crytochat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText userID,passID,emailID;
    Button registerbtn;

    //firebase
    FirebaseAuth auth;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userID=findViewById(R.id.UserEditText);
        passID=findViewById(R.id.PasswordEditText2);
        emailID=findViewById(R.id.EmailEditText);
        registerbtn=findViewById(R.id.buttonRegister);

        auth=FirebaseAuth.getInstance();

        registerbtn.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String user_text=userID.getText().toString();
                String pass=passID.getText().toString();
                String email=emailID.getText().toString();
                if(TextUtils.isEmpty(user_text) ||TextUtils.isEmpty(pass) ||TextUtils.isEmpty(email))
                {
                    Toast.makeText(RegisterActivity.this,"Plz fill the fiels",Toast.LENGTH_SHORT).show();
                }else{ RegisterNow(user_text,pass,email);}
            }
        });
    }

    public void RegisterNow(final String username,String Password,String Email)
    {
        auth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            FirebaseUser firebaseUser=auth.getCurrentUser();
                            String userid=firebaseUser.getUid();
                            myref= FirebaseDatabase.getInstance()
                                    .getReference("MyUsers").child(userid);

                            //hashMap;
                            HashMap<String,String> hashMap=new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("username",username);
                            hashMap.put("imageURL","default");

                            myref.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                        if(task.isSuccessful())
                                        {
                                            Intent i=new Intent(RegisterActivity.this,MainActivity.class);
                                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(i);
                                            finish();
                                        }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this,"Invalid Username or email",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}