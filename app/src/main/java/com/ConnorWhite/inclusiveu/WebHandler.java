package com.ConnorWhite.inclusiveu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class WebHandler extends Activity {

    private final String HTTP = "http://";
    private final String HTTPS = "https://";
    private final String MAILTO = "mailto:";
    private Context context;

    public WebHandler(Context c){
        context = c;
    }

    public void goToURL(String url){
        url = addHTTP(url);
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        context.startActivity(launchBrowser);
    }

    private String addHTTP(String url) {
        if(!(url.startsWith(HTTP) || url.startsWith(HTTPS) || url.startsWith(MAILTO)))
            url = HTTP + url;
        return url;
    }
}
