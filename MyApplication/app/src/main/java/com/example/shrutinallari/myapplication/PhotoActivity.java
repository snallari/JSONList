package com.example.shrutinallari.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class PhotoActivity extends Activity {

    private ImageView v1;
    private WebView w1;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photodetail);
        Intent in = getIntent();
        String title = in.getStringExtra(CountryJSONParser.TAG_TITLE);
        String description = in.getStringExtra(CountryJSONParser.TAG_DESCRIPTION);
        String details = in.getStringExtra(CountryJSONParser.TAG_BUTTONTITLE);
        TextView tit = (TextView) findViewById(R.id.title);
        tit.setText(description);
        v1 = (ImageView) findViewById(R.id.description);
        w1 = (WebView) findViewById(R.id.webhtml);
        w1.setWebViewClient(new MyBrowser());
        bar = new ProgressBar(this);
        bar.setIndeterminate(true);

        if (description == "GET READY FOR SUMMER DAYS") {
            Picasso.with(getBaseContext()).load("http://anf.scene7.com/is/image/anf/anf-US-20150629-app-women-shorts").into(v1);
            w1.getSettings().setLoadsImagesAutomatically(true);
            w1.getSettings().setJavaScriptEnabled(true);
            w1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            w1.loadUrl("https://m.abercrombie.com");

        } else {
            Picasso.with(getBaseContext()).load("http://anf.scene7.com/is/image/anf/anf-US-20150629-app-women-brands").into(v1);
            w1.getSettings().setLoadsImagesAutomatically(true);
            w1.getSettings().setJavaScriptEnabled(true);
            w1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            w1.loadUrl("https://m.hollisterco.com");
        }
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}

