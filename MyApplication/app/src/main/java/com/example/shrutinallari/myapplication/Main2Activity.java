package com.example.shrutinallari.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class Main2Activity extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent in=getIntent();
        String title=in.getStringExtra(CountryJSONParser.TAG_TITLE);
        String description=in.getStringExtra(CountryJSONParser.TAG_DESCRIPTION);
        String details=in.getStringExtra(CountryJSONParser.TAG_BUTTONTITLE);
        TextView tit=(TextView)findViewById(R.id.title);
        tit.setText(description);
        TextView det=(TextView)findViewById(R.id.details);
        det.setText(details);
        WebView webView2=(WebView)findViewById(R.id.webhtml);
        WebView webView=(WebView)findViewById(R.id.webvw);
        WebView webView3=(WebView)findViewById(R.id.webdata);
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        WebSettings webSettings1=webView2.getSettings();
        webSettings.setJavaScriptEnabled(true);
        WebSettings webSettings2=webView3.getSettings();
        webSettings.setJavaScriptEnabled(true);
        ImageView imageView=(ImageView)findViewById(R.id.description);
        ImageLoader mImageLoader = VolleySingleton.getInstance().getImageLoader();
        if(description=="GET READY FOR SUMMER DAYS") {
            webView.loadUrl("http://anf.scene7.com/is/image/anf/anf-US-20150629-app-women-shorts");
            String customHtml= "<a href=\"https://www.abercrombie.com/anf/media/legalText/viewDetailsText20150618_Shorts25_US.html\" class=\"legal promo-details\">See details</a>";
            webView2.loadData(customHtml, "text/html", "UTF-8");
            webView3.loadUrl("https://m.abercrombie.com");
        }else{
            webView.loadUrl("http://anf.scene7.com/is/image/anf/anf-US-20150629-app-women-brands");
            webView3.loadUrl("https://m.hollisterco.com");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
