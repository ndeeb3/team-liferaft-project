package alec.ratapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private AutoCompleteTextView email;
    private EditText username;
    private EditText password;
    private CheckBox adminCheckBox;
    private RatSightingAccessor ratSightingAccessor = new RatSightingAccessor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adminCheckBox = (CheckBox) findViewById(R.id.adminChkBox);
        email = (AutoCompleteTextView) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("DEBUG", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("DEBUG", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        //register new user and log them in
        Button registrationButton = (Button) findViewById(R.id.register_button);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                Log.d("DEBUG", "CHECKING FORMAT");
                if(checkValidFormat()) {
                    Log.d("DEBUG", "Login Ok");
                    User newUser;
                    if(adminCheckBox.isChecked()) {
                        newUser = new Administrator(null,null,null);
                    }
                    else {
                        newUser = new User(null,null,null);
                    }
                    registerUser(newUser);
                }
                startActivity(intent);
            }
        });

        //Cancel the regristration screen and return to the welcome screen
        Button cancelRegistration = (Button) findViewById(R.id.cancelRegButton);
        cancelRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, Welcome.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public boolean checkValidFormat(){
        return true;
        /*if(email.getText().toString() == null) {
            return false;
        }
        if(username.getText().toString() != null){
            return false;
        }
        if(password.getText().toString() != null){
            return false;
        }
        return true;*/
    }

    public User registerUser(User u) {
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("DEBUG", "createUserWithEmail:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "REG FAILED",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if(adminCheckBox.isChecked()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user != null) {
                            }

                        }
                    }


                });
        u = new User(username.getText().toString(), password.getText().toString(), email.getText().toString());
        Log.d("", u.getContactInfo());
        ratSightingAccessor.createAccount(u);
        return u;
    }


}
