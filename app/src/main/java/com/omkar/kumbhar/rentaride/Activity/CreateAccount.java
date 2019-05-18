package com.omkar.kumbhar.rentaride.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.omkar.kumbhar.rentaride.Method.Validation;
import com.omkar.kumbhar.rentaride.R;

import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    EditText edtName,edtEmail,edtPassword,edtPassConfirm;
    Button btnRegister,btnSkip;
    TextView txtAlreadyReg;
    Validation validation;
    String Uid;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        validation = new Validation();
        db = FirebaseFirestore.getInstance();
        edtName = (EditText) findViewById(R.id.edtName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPassConfirm = (EditText) findViewById(R.id.edtPassConfirm);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnSkip = (Button) findViewById(R.id.btnSkip);
        txtAlreadyReg = (TextView) findViewById(R.id.txtAlreadyReg);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRegister.setEnabled(false);
                boolean result =  validation.InputValidation(edtName);
                boolean result2 =  validation.InputValidation(edtPassword);
                boolean result3 =  validation.InputValidation(edtPassConfirm);
                boolean result4 =  validation.EmailValidation(edtEmail);
                boolean result5 = validation.PasswordVerification(edtPassConfirm,edtPassword);
                if(result && result2 && result3 && result4 && result5 ) {




                    createAccount(edtEmail.getText().toString().trim(), edtPassword.getText().toString().trim());
                }
            }
        });

        txtAlreadyReg.setOnClickListener(v -> finish());

        btnSkip.setOnClickListener(v -> {
            Intent i = new Intent(CreateAccount.this,HomeActivity.class);
            startActivity(i);
            finish();
        });

    }
    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Uid = user.getUid();
                            addData();
                            if(!user.isEmailVerified()){
                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(CreateAccount.this, "Verification email has been send to your  registered account", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }




                        }else {

                            Toast.makeText(CreateAccount.this, "Something went Wrong "+task.getException().hashCode() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void addData() {
        Map<String, Object> user = new HashMap<>();
        user.put("Name",edtName.getText().toString().trim());
        user.put("Email",edtEmail.getText().toString().trim());
        user.put("UserID",Uid);
        db.collection("Users").document(Uid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateAccount.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
