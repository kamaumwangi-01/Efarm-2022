package com.example.wezeshabiashara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAcccountButton;
    private EditText InputName,InputPhoneNumber,InputPassord;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CreateAcccountButton =findViewById(R.id.login_btn_registering_a_new_account);
        InputName =findViewById(R.id.register_name_number_input);
        InputPhoneNumber = findViewById(R.id.register_phone_number_input);
        InputPassord = findViewById(R.id.register_password_input);
        loadingBar = new ProgressDialog(this);

        CreateAcccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               CreateAccount();
            }
        });
    }

    private void CreateAccount()
    {
        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassord.getText().toString();

        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this,"please input your phone number",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this,"please input your phone number",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"please input your phone number",Toast.LENGTH_SHORT).show();
        }
        else
            {
              loadingBar.setTitle("Create Account");
              loadingBar.setMessage("please wait while we are checking credentials");
              loadingBar.setCanceledOnTouchOutside(true);
              loadingBar.show();

              ValidatePhoneNumber(name,phone,password);
            }
    }

    private void ValidatePhoneNumber(final String name,final String phone, final String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(phone).exists()))
                {
                    HashMap<String, Object>userdatamap= new HashMap<>();
                    userdatamap.put("phone",phone);
                    userdatamap.put("password",password);
                    userdatamap.put("name",name);

                    RootRef.child("Users").child(phone).updateChildren(userdatamap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this,"Your Account has been created successfully",Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                        {
                                            loadingBar.dismiss();
                                            Toast.makeText(RegisterActivity.this,"Netork Problema: Please try again", Toast.LENGTH_SHORT).show();
                                        }

                                }
                            });

                }
                else
                    {
                        Toast.makeText(RegisterActivity.this,"This"+phone+ "is arleady in use please use",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        Toast.makeText(RegisterActivity.this,"please use another number",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                        startActivity(intent);

                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
