package com.example.planingpoker;

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

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    Button login;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.edt_email);
        password=findViewById(R.id.edt_password);
        login=findViewById(R.id.btn_login);
        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),User.class));
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailtext=email.getText().toString().trim();
                String passwordtext=password.getText().toString().trim();
                if(TextUtils.isEmpty(emailtext))
                {
                    email.setError("Email is empty");
                    return;
                }
                if(TextUtils.isEmpty(passwordtext))
                {
                    password.setError("Password is empty");
                }
                if(passwordtext.length()<6)
                {
                    password.setError("Password must be more then 5 character");
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(emailtext,passwordtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, "User created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),User.class));
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Error" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

}
