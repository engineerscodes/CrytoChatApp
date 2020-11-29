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

public class Login_Activity extends AppCompatActivity {
    EditText userIDlogin,passLogin;
    Button loginBtn,RegBtn;
    //FireBase
    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null)
        {
            Intent i=new Intent(Login_Activity.this,MainActivity.class);
            startActivity(i);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        userIDlogin=findViewById(R.id.LoginEmail);
        passLogin=findViewById(R.id.LoginPassText);
        loginBtn=findViewById(R.id.buttonLogin);
        RegBtn=findViewById(R.id.Reg);


        auth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null)
        {
            Intent i=new Intent(Login_Activity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        //Login Button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Useremail=userIDlogin.getText().toString();
                String password=passLogin.getText().toString();

                if(TextUtils.isEmpty(Useremail) || TextUtils.isEmpty(password))
                {
                    Toast.makeText(Login_Activity.this,"Fill TextFildes ",Toast.LENGTH_SHORT).show();
                }else{

                    auth.signInWithEmailAndPassword(Useremail,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                Intent i=new Intent(Login_Activity.this,MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                                finish();
                            }else{ Toast.makeText(Login_Activity.this,"Login Failed!!!",Toast.LENGTH_SHORT).show();}
                        }
                    });


                }
            }
        });

        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login_Activity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}