package alec.ratapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Welcome extends AppCompatActivity {


    private List<User> temperaryUsersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("DEBUG", "Launching Login Activity");
        Log.d("DEBUG", "FDSFSDKJFHKDSJFHKJSDFHKJSFH");
        Context context = this;
        try {
            Log.d("ERROR", "INSIDE TRYY");
            if (FakeDatabase.getInstance().reportList.size() == 0) {
                FakeDatabase.getInstance().populateReportList(context);
            }
        } catch (java.io.IOException e) {
            Log.d("ERROR", "The file could not be read: " + e.getMessage());
        }
        Button login = (Button) findViewById(R.id.loginButton);
        Button register = (Button) findViewById(R.id.registerButton);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

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
