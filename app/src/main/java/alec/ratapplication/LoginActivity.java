package alec.ratapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.regex.Pattern;

/**
 * A login screen that offers login with email/password.
 */
public class LoginActivity extends AppCompatActivity {

    //private RatSightingAccessor ratSightingAccessor = new RatSightingAccessor();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    /**
     * Id to identity READ_CONTACTS permission request.
     */

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        // Set up the login form ui.
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        /*mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });*/

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button cancelLogin = findViewById(R.id.cancelLoginButton);
        cancelLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Welcome.class);
                startActivity(intent);
            }
        });

        final Button resetPass = findViewById(R.id.resetpass);
        resetPass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPass();
            }
        });

        //mLoginFormView = findViewById(R.id.login_form);
        //mProgressView = findViewById(R.id.login_progress);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // There was no errors, and Firebase will now login the user
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("DEBUG", "signInWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w("DEBUG", "signInWithEmail:failed", task.getException());
                                Toast.makeText(LoginActivity.this, "Login Failed. Could Not Find Account",
                                        Toast.LENGTH_SHORT).show();
                            }else {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

            /*Log.d("USERS", ratSightingAccessor.getUsers().toString());
            for (User user : ratSightingAccessor.getUsers()) {
                Log.d("DEBUG", user.getContactInfo() + user.getPassword());
                Log.d("DEBUG", mEmailView.getText().toString() + mPasswordView.getText().toString());
                if (user.getContactInfo().equals(mEmailView.getText().toString())
                        && user.getPassword().equals(mPasswordView.getText().toString())) {
                    Log.d("DEBUG", "LAUNCHING MAIN ACTIVITY");

                }
            }*/
            //showProgress(true);
        }
    }

    /**
     * Private email validation method that checks the email string against a email-styled regex
     * @param email the email string that is being validated
     * @return true if the string is a valid email, false otherwise
     */
    static boolean isEmailValid(String email) {
        //Pattern namePiece = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        if(email == null) {
            throw new NullPointerException();
        }
        if (email.contains("@")) {
            Pattern namePiece = Pattern.compile("^[A-Z0-9._%+-]+", Pattern.CASE_INSENSITIVE);
            if(namePiece.matcher(email).find()) {
                Pattern typePiece = Pattern.compile("[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                return typePiece.matcher(email).find();
            }
        }
        return false;
    }

    /**
     * Private password validation method that checks if the password is long enough
     * @param password the password string that is being checked for length
     * @return true if the password is long enough, false otherwise
     */
     static boolean isPasswordValid(String password) {
         if (password == null)
         {
             throw new NullPointerException("Password input cannot be null");
         }
         else return password.length() > 4;
    }


    boolean resetPass() {
        String email = mEmailView.getText().toString();
        boolean cancel = false;
         View focusView = null;
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }

         Log.d("DEBUG", "Password reset");
         FirebaseAuth.getInstance().sendPasswordResetEmail(email);
         return true;
    }


}

