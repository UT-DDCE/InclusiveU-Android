package com.ConnorWhite.inclusiveu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

public class Partners extends AppCompatActivity {

    private WebHandler webHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners);
        webHandler = new WebHandler(this);
    }

    public void launchHome(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void goToGSC(View view) {
        webHandler.goToURL("http://diversity.utexas.edu/genderandsexuality/");
    }

    public void goToDDCE(View view) {
        webHandler.goToURL("http://diversity.utexas.edu/");
    }

    public void goToLib(View view){
        webHandler.goToURL("http://www.lib.utexas.edu");
    }
}
