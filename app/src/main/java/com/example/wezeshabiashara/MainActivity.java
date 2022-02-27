package com.example.wezeshabiashara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wezeshabiashara.Model.Users;
import com.example.wezeshabiashara.Prevalent.Prevalent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity
{

    //private Button registerNowButton,LoginNowButton;
    //private ProgressDialog loadingBar;
    private EditText InputNumber, InputPassword;
    private Button LoginButton;
   // private ProgressDialog loadingBar;

    private String parentDbName = "Users";
    private CheckBox chkboxRememberMe;
    private TextView AdminLink, notAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginButton = findViewById(R.id.login_btn);
        InputNumber = findViewById(R.id.login_phone_number_input);
        InputPassword = findViewById(R.id.login_passsword_input);
        AdminLink = findViewById(R.id.admin_panel_link);
        notAdmin = findViewById(R.id.not_admin_panel_link);
        // loadingBar = new ProgressDialog(this);
        chkboxRememberMe = findViewById(R.id.remember_me);
        Paper.init(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });
        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                notAdmin.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }
        });
        notAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login User");
                AdminLink.setVisibility(View.VISIBLE);
                notAdmin.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
            }
        });
    }

    private void LoginUser() {
        String phone = InputNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "please input your phone number", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty (password)) {
            Toast.makeText(this, "please input your password", Toast.LENGTH_LONG).show();
        } else {
           // loadingBar.setTitle("Login Account");
            //loadingBar.setMessage("Login in......");
           // loadingBar.setCanceledOnTouchOutside(true);
            //loadingBar.show();

            AllowAccessToAccount(phone, password);
        }

    }

    private void AllowAccessToAccount ( final String phone, final String password)
    {
        if (chkboxRememberMe.isChecked()) {
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists()) {
                    Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone)) {
                        if (usersData.getPassword().equals(password)) {
                            if (parentDbName.equals("Admins")) {
                                Toast.makeText(MainActivity.this, "welcome Admin", Toast.LENGTH_SHORT).show();
                                //loadingBar.dismiss();

                                Intent intent = new Intent(MainActivity.this, new_admini_dashi.class);
                                startActivity(intent);

                            } else if (parentDbName.equals("Users")) {
                                Toast.makeText(MainActivity.this, "logged successfully", Toast.LENGTH_SHORT).show();
                                //loadingBar.dismiss();

                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                Prevalent.currentUserSession = usersData;
                                startActivity(intent);
                            }
                        }
                    } else {
                       // loadingBar.dismiss();
                        Toast.makeText(MainActivity.this, "Account with this" + phone + "do not exist", Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(LoginActivity.this, AdminsDashBoard.class);
                        //startActivity(intent);

                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });




///////===========Previous code==============////////
        /*
       LoginNowButton= findViewById(R.id.main_join_login_now_btn);
       registerNowButton = findViewById(R.id.main_register_now_btn);
        loadingBar=new ProgressDialog(this);

        Paper.init(this);


        LoginNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        registerNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
               startActivity(intent);
            }
        });

        String userPhoneKey=Paper.book().read(Prevalent.UserPhoneKey);
        String userPasswordKey=Paper.book().read(Prevalent.UserPasswordKey);

        if(userPhoneKey !="" && userPasswordKey !="")
          {
              if(!TextUtils.isEmpty(userPhoneKey) && !TextUtils.isEmpty(userPasswordKey))
              {
                  AllowAccess(userPhoneKey,userPasswordKey);
                  loadingBar.setTitle("Already logged in");
                  loadingBar.setMessage("wait as we are doing it for you");
                  loadingBar.setCanceledOnTouchOutside(true);
                  loadingBar.show();
              }
          }
    }



    private void AllowAccess(final String  phone, final String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child("Users").child(phone).exists())
                {
                    Users usersData = dataSnapshot.child("Users").child(phone).getValue(Users.class);

                    if(usersData.getPhone().equals(phone))
                    {
                        if(usersData.getPassword().equals(password))
                        {
                            Toast.makeText(MainActivity.this,"logged successfully",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                            Prevalent.currentUserSession = usersData;
                            startActivity(intent);
                        }

                    }

                }
                else
                {
                    Toast.makeText(MainActivity.this,"Account with this"+phone+"do not exist",Toast.LENGTH_SHORT).show();

                    loadingBar.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */
    }


}

