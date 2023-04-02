package com.example.ofsa;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText register_email_field;
    private EditText register_pass_field;
    private EditText register_confir_pass_field;
    private Button reg_btn;
    FirebaseAuth firebaseAuth;
    private Button reg_login_btn;
    private ProgressBar progressBar;
    private CheckBox checkBox;
    private EditText  register_name_field;
    private EditText register_Phoneno_field;
    private EditText register_address_field;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        checkBox=findViewById(R.id.register_checkbox);
        register_email_field=findViewById(R.id.register_email);
        register_pass_field=findViewById(R.id.register_password);
        register_confir_pass_field=findViewById(R.id.register_confirm_password);
        reg_btn=findViewById(R.id.register_button);
        reg_login_btn=findViewById(R.id.register_login_button);
        progressBar=findViewById(R.id.register_progressBar);
        register_name_field=findViewById(R.id.register_name);
        register_Phoneno_field=findViewById(R.id.register_Phoneno);
        register_address_field=findViewById(R.id.register_address);

        databaseReference=FirebaseDatabase.getInstance().getReference().child("Student");
        firebaseAuth=FirebaseAuth.getInstance();

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    register_pass_field.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    register_confir_pass_field.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    register_pass_field.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    register_confir_pass_field.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });



        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = register_name_field.getText().toString();
                String email = register_email_field.getText().toString();
                String Address = register_address_field.getText().toString();
                String phone = register_Phoneno_field.getText().toString();
                String pass = register_pass_field.getText().toString();
                String confirm_pass = register_confir_pass_field.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirm_pass) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(Address)) {
                    if (!register_name_field.getText().toString().matches("[a-z,A-Z, ]*")) {
                        register_name_field.setError("Enter character Only");
                    } else if (!register_Phoneno_field.getText().toString().matches("[0-9]{10}")) {
                        register_Phoneno_field.setError("Enter Only 10 digit Mobile Number");
                    } else if (register_email_field.length() == 0) {
                        Toast.makeText(getApplicationContext(), "Please Enter Email Address", Toast.LENGTH_LONG).show();
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(register_email_field.getText().toString()).matches()) {
                        register_email_field.setError("Please Enter Valid Mail");
                    } else if (pass.equals(confirm_pass)) {
                        progressBar.setVisibility(View.VISIBLE);
                        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    student information = new student(
                                            name,
                                            email,
                                            Address,
                                            phone,
                                            pass,
                                            confirm_pass
                                    );

                                    databaseReference = firebaseDatabase.getInstance().getReference().child("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                                    databaseReference.push().setValue(information);
                                            Toast.makeText(Register.this, "Registration Complete", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getApplicationContext(), Login.class));

                                }
                                else{
                                    Toast.makeText(Register.this, "Error !"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(getApplicationContext(), "Confirm password and password field doesn't match", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please fill Complete Details", Toast.LENGTH_LONG).show();
                }
            }

        });


        reg_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });


    }

}