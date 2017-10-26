package alec.ratapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

/**
 * A menu which lets a user login to their account
 * or create a new account
 */
public class Welcome extends AppCompatActivity {


    private List<User> temperaryUsersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button login = (Button) findViewById(R.id.loginButton);
        Button register = (Button) findViewById(R.id.registerButton);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("DEBUG", "Launching Login Activity");
                Intent intent = new Intent(Welcome.this, LoginActivity.class);
                Bundle bundle = new Bundle();
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("DEBUG", "Launching Registration Activity");
                Intent intent = new Intent(Welcome.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }

}
