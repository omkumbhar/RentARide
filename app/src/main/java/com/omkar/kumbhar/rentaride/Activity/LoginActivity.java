package com.omkar.kumbhar.rentaride.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.omkar.kumbhar.rentaride.R;
import com.omkar.kumbhar.rentaride.Method.UserData;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    List<AuthUI.IdpConfig> providers = Collections.singletonList(
            new AuthUI.IdpConfig.GoogleBuilder().build());

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser cUser;
    private CallbackManager mCallbackManager;
    private EditText edtUsername,edtPassword;
    private String Username,Password;
    Button btnLogin,btnCreateAccount,btnSkip;
    SignInButton google_sign_in_button;
    LoginButton btnFacebook;
    UserData userData;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login ");

        db = FirebaseFirestore.getInstance();
        userData = new UserData();
        cUser = mAuth.getCurrentUser();


        if(cUser != null){
            updateUI(cUser);
        }
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword =(EditText) findViewById(R.id.edtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        btnSkip = (Button) findViewById(R.id.btnSkip);
        google_sign_in_button = (SignInButton) findViewById(R.id.google_sign_in_button);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,CitySelection.class);
                startActivity(i);
            }
        });
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,CreateAccount.class);
                startActivity(i);
            }
        });
        mCallbackManager = CallbackManager.Factory.create();
        btnFacebook =(LoginButton) findViewById(R.id.btnFacebook);
        btnFacebook.setReadPermissions("email", "public_profile");
        btnFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LoginActivity.this, loginResult+" ", Toast.LENGTH_SHORT).show();
                facebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        google_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build();
                startActivityForResult(signInIntent,9999);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin.setEnabled(false);
                boolean status = InputValidation(edtUsername,edtPassword);
                if(status){
                    Username = edtUsername.getText().toString().trim();
                    Password = edtPassword.getText().toString().trim();
                    if(Username.length() > 0 && Password.length() > 0){
                        SignIn(Username,Password);
                    }
                }

            }
        });
    }

    private void facebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            cUser = mAuth.getCurrentUser();
                            updateUI(cUser);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser cUser) {
        Intent i = new Intent(LoginActivity.this,CitySelection.class);
        DocumentReference DocRef = db.collection("Users").document(cUser.getUid());




        if(cUser.isEmailVerified()){
            userData.AddEmailVerified(true,getApplicationContext());
        }




        DocRef .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(!task.getResult().exists()){
                            Map<String, Object> user = new HashMap<>();
                            user.put("Name",cUser.getDisplayName());
                            user.put("Email",cUser.getEmail());
                            user.put("UserID",cUser.getUid());
                            db.collection("Users").document(cUser.getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    boolean result = userData.AddData(cUser.getDisplayName(),cUser.getUid(),cUser.getEmail(),getApplicationContext());
                                    if(result){
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else {
                            boolean result = userData.AddData(cUser.getDisplayName(),cUser.getUid(),cUser.getEmail(),getApplicationContext());
                            if(result){
                                startActivity(i);
                                finish();
                            }

                        }
                    }
                });

    }

    private boolean InputValidation(EditText edtUsername, EditText edtPassword) {
        boolean result;
        if (edtUsername.getText().toString().trim().equalsIgnoreCase("")) {
            edtUsername.setError("This field can not be blank");
            result = false;
        }else {
            result = true;
        }
        if (edtPassword.getText().toString().trim().equalsIgnoreCase("")) {
            edtPassword.setError("This field can not be blank");
            result = false;
        }else {
            result = true;
        }
        return result;
    }

    private void SignIn(String username, String password) {
        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    cUser = mAuth.getCurrentUser();

                    if(cUser.isEmailVerified()){
                        userData.AddEmailVerified(true,getApplicationContext());
                    }

                    DocumentReference docRef = db.collection("Users").document(cUser.getUid());
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot document = task.getResult();
                                if(document.exists()){
                                    String name = (String) document.get("Name");
                                    String email = (String) document.get("Email");
                                    String uid = (String) document.get("UserID");
                                    boolean result = userData.AddData(name,uid,email,getApplicationContext());

                                    if(result){
                                        Intent i = new Intent(LoginActivity.this,CitySelection.class);
                                        startActivity(i);
                                    }
                                }
                            }else {
                                Toast.makeText(LoginActivity.this, "Can find database of user", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(), "Something went wrong"+ task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9999 ){
            if ( resultCode == RESULT_OK) {
                cUser = mAuth.getCurrentUser();
                updateUI(cUser);
            }else {
                Toast.makeText(this, "Something went Wrong" + resultCode, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
