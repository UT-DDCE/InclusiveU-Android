package com.ConnorWhite.inclusiveu;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Home extends AppCompatActivity {

    private WebHandler webHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        webHandler = new WebHandler(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    public void launchAGRMap(View view) {
        Intent intent = new Intent(this, GIRMap.class);
        startActivity(intent);
    }

    public void launchLibMap(View view) {
        Intent intent = new Intent(this, LibMap.class);
        startActivity(intent);
    }

    public void goToLibSpec(View view) {
        webHandler.goToURL("https://www.lib.utexas.edu/subject/index.php");
    }

    public void launchPartners(View view) {
        Intent intent = new Intent(this, Partners.class);
        startActivity(intent);
    }

    public void launchAbout(View view) {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    public void launchDevelopment(View view) {
        Intent intent = new Intent(this, Development.class);
        startActivity(intent);
    }

    public void goToIGWY(View view) {
        webHandler.goToURL("http://www.illgowithyou.org/");
    }

    public void goToPlayStore(View view){
        webHandler.goToURL("https://play.google.com/store/apps/developer?id=Connor%20White&hl=en");
    }
}
