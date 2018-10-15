package com.example.luisbb.firebaseauthdatabase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.luisbb.firebaseauthdatabase.Models.SongDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;

    private EditText mEmailField;
    private EditText mPasswordField;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmailField=findViewById(R.id.field_email);
        mPasswordField=findViewById(R.id.field_password);

        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.email_create_account_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.load_show_playlist_button).setOnClickListener(this);

        mAuth= FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
    }

    private void signIn(String email, String password){
        if(!validateForm())
            return;

        showProgressDialog();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser myUser=mAuth.getCurrentUser();
                            updateUI(myUser);
                        } else{
                            Toast.makeText(MainActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        hideProgressDialog();
                    }
                });
    }

    private void createAccount(String email, String password){
        if(!validateForm())
            return;

        showProgressDialog();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser myUser=mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                            updateUI(myUser);
                        } else{
                            Toast.makeText(MainActivity.this, "Ooopps!", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        hideProgressDialog();
                    }
                });
    }

    private boolean validateForm(){
        boolean isValid=true;

        String myEmail=mEmailField.getText().toString();

        if(TextUtils.isEmpty(myEmail)){
            mEmailField.setError("Email required!");
            isValid=false;
        } else{
            mEmailField.setError(null);
        }

        String myPassword=mPasswordField.getText().toString();

        if(TextUtils.isEmpty(myPassword)){
            mPasswordField.setError("Password required!");
            isValid=false;
        } else{
            mPasswordField.setError(null);
        }

        return isValid;
    }

    private void signOut(){
        mAuth.signOut();
        Toast.makeText(MainActivity.this, "Bye!", Toast.LENGTH_SHORT).show();
        updateUI(null);
    }

    private void updateUI(FirebaseUser myUser){
        hideProgressDialog();

        if(myUser!=null){
            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.title_text).setVisibility(View.GONE);

        } else{
            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.title_text).setVisibility(View.VISIBLE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
        }
    }

    public void showProgressDialog(){
        if(mProgressDialog==null){
            mProgressDialog=new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading_warning));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    @Override
    public void onClick(View v) {
        int i=v.getId();

        switch(i){
            case R.id.email_create_account_button:
                createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
                break;
            case R.id.email_sign_in_button:
                signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
                break;
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.load_show_playlist_button:
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference mDatabaseRef=database.getReference();

                mDatabaseRef.child("songs").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot child: dataSnapshot.getChildren()){
                            SongDetails mSongData=child.getValue(SongDetails.class);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//                Intent myIntent= new Intent(this, SongsDataBaseActivity.class);
//                startActivity(myIntent);
                break;
            default:
                return;
        }
    }
}
