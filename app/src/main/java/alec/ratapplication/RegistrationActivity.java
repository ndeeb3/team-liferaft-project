package alec.ratapplication;

import android.content.Intent;
import android.os.Bundle;
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

public class RegistrationActivity extends AppCompatActivity {

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
        u = new User(username.getText().toString(), password.getText().toString(), email.getText().toString());
        Log.d("", u.getContactInfo());
        ratSightingAccessor.createAccount(u);
        return u;
    }


}
