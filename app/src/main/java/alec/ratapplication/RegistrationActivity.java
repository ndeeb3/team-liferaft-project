package alec.ratapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

/**
 * A menu allowing a new user to register
 * an account
 */
public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private AutoCompleteTextView mEmailView;
    private EditText mUsernameView;
    private EditText mPasswordView;
    private CheckBox adminCheckBox;
    private RatSightingAccessor ratSightingAccessor = new RatSightingAccessor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adminCheckBox = findViewById(R.id.adminChkBox);
        mEmailView = findViewById(R.id.email);
        mUsernameView = findViewById(R.id.username);
        mPasswordView = findViewById(R.id.password);

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
                // Do anything with user when it logs in or leaves
            }
        };

        //register new user and log them in
        Button registrationButton = findViewById(R.id.register_button);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                Log.d("DEBUG", "CHECKING FORMAT");
                attemptRegistration();
            }
        });

        //Cancel the registration screen and return to the welcome screen
        Button cancelRegistration = findViewById(R.id.cancelRegButton);
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

    /**
     * Validates the information entered
     * by the user
     */
    private void attemptRegistration() {

        mEmailView.setError(null);
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid mEmailView address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        // Check for a valid mUsername, if the user entered one.
        if (TextUtils.isEmpty(username)) {
            //Log.d("DEBUG", username + " Username Status:" + TextUtils.isEmpty(username));
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        // Check for a valid mPasswordView, if the user entered one.
        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            Log.d("DEBUG", "Login Validated");
            final String displayName = username;
            //Firebase will now register the user
            mAuth.createUserWithEmailAndPassword(mEmailView.getText().toString(), mPasswordView.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("DEBUG", "createUserWithEmail:onComplete:" + task.isSuccessful());

                    if (!task.isSuccessful()) {
                        Toast.makeText(RegistrationActivity.this, "Registration Failed",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        //Logic if the user is successfully registered
                        FirebaseUser user = mAuth.getCurrentUser();
                        DatabaseReference mRef = FirebaseDatabase.getInstance()
                                .getReference().child("users");
                        if (user != null) {
                            //set the username
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(displayName).build();

                            user.updateProfile(profileUpdates);

                            //add admin status and other user specific data if needed
                            if (adminCheckBox.isChecked()) {
                                mRef.child(user.getUid()).child("admin").setValue("true");
                            } else {
                                mRef.child(user.getUid()).child("admin").setValue("false");
                            }
                        }
                        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }



    /**
     * Private email validation method that checks the email string against a email-styled regex
     * @param email the email string that is being validated
     * @return true if the string is a valid email, false otherwise
     */
    private boolean isEmailValid(String email) {
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        return p.matcher(email).find();
    }

    /**
     * Private password validation method that checks if the password is long enough
     * @param password the password string that is being checked for length
     * @return true if the password is long enough, false otherwise
     */
    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /*public User registerUser(User u) {
        u = new User(mUsernameView.getText().toString(), mPasswordView.getText().toString(), mEmailView.getText().toString());
        Log.d("", u.getContactInfo());
        ratSightingAccessor.createAccount(u);
        return u;
    }*/


}
