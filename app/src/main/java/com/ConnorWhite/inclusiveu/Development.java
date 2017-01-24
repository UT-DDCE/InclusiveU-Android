package com.ConnorWhite.inclusiveu;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Development extends AppCompatActivity {

    private WebHandler webHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_development);
        webHandler = new WebHandler(this);

        Linkify linkify = new Linkify();
        TextView creditText = (TextView) findViewById(R.id.credit_text);
        Pattern pattern = Pattern.compile("Jeremy Thompson");
        String linkJ = "http://www.mrjeremyt.com";
        linkify.addLinks(creditText, pattern, linkJ, null, clear());
        Pattern pattern2 = Pattern.compile("Connor White");
        String linkC = "https://play.google.com/store/apps/developer?id=Connor%20White&hl=en";
        linkify.addLinks(creditText, pattern, linkC, null, clear());
    }

    private Linkify.TransformFilter clear() {
        Linkify.TransformFilter filter = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return "";
            }
        };
        return filter;
    }

    public void launchHome(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void goToContact(View view) {
        webHandler.goToURL("mailto:gsc@austin.utexas.edu/");
    }

    public void goToFeedback(View view) {
        webHandler.goToURL("https://utexas.qualtrics.com/jfe/form/SV_b1aDEH4P4vFPUEZ");
    }

    public void goToPlayStore(View view){
        webHandler.goToURL("https://play.google.com/store/apps/developer?id=Connor%20White&hl=en");
    }
}
