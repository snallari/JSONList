package com.example.shrutinallari.myapplication;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class DynamicListViewActivity extends Activity implements ListView.OnItemClickListener {

    ListView mListView;

    private HashMap<String, Object> hm;
    private SimpleAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // URL to the JSON data
        String strUrl = "http://www.abercrombie.com/anf/nativeapp/Feeds/promotions.json";

        // Creating a new non-ui thread task to download json data
        DownloadTask downloadTask = new DownloadTask();

        // Starting the download process
        downloadTask.execute(strUrl);

        // Getting a reference to ListView of activity_main
        mListView = (ListView) findViewById(R.id.listcountries);
        mListView.setOnItemClickListener(this);

    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("", "");
        } finally {
            iStream.close();
        }

        return data;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String details = ((TextView) view.findViewById(R.id.tv_country_details)).getText().toString();

        Toast.makeText(this, "onclick", Toast.LENGTH_LONG);
        Intent abc = new Intent(this, Main2Activity.class);
        abc.putExtra(CountryJSONParser.TAG_DESCRIPTION, details);
        startActivity(abc);

    }

    /**
     * AsyncTask to download json data
     */
    private class DownloadTask extends AsyncTask<String, Integer, String> {
        String data = null;

        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {

            // The parsing of the xml data is done in a non-ui thread
            ListViewLoaderTask listViewLoaderTask = new ListViewLoaderTask();

            // Start parsing xml data
            listViewLoaderTask.execute(result);
        }
    }

    /**
     * AsyncTask to parse json data and load ListView
     */
    private class ListViewLoaderTask extends AsyncTask<String, Void, SimpleAdapter> {

        JSONObject jObject;

        // Doing the parsing of xml data in a non-ui thread
        @Override
        protected SimpleAdapter doInBackground(String... strJson) {
            try {
                jObject = new JSONObject(strJson[0]);
                CountryJSONParser countryJsonParser = new CountryJSONParser();
                countryJsonParser.parse(jObject);
            } catch (Exception e) {
                Log.d("JSON Exception1", e.toString());
            }

            // Instantiating json parser class
            CountryJSONParser countryJsonParser = new CountryJSONParser();

            // A list object to store the parsed countries list
            List<HashMap<String, Object>> countries = null;

            try {
                // Getting the parsed data as a List construct
                countries = countryJsonParser.parse(jObject);
            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }

            // Keys used in Hashmap
            String[] from = {"country", "image", "details"};

            // Ids of views in listview_layout
            int[] to = {R.id.tv_country, R.id.tv_flag, R.id.tv_country_details};

            // Instantiating an adapter to store each items
            // R.layout.listview_layout defines the layout of each item
            adapter = new SimpleAdapter(getBaseContext(), countries, R.layout.activity_cell, from, to);
            return adapter;
        }

        /**
         * Invoked by the Android on "doInBackground" is executed
         */
        @Override
        protected void onPostExecute(SimpleAdapter adapter) {
            // Setting adapter for the listview
            mListView.setAdapter(adapter);
            final ImageLoader mImageLoader = VolleySingleton.getInstance().getImageLoader();
            SimpleAdapter.ViewBinder viewBinder = new SimpleAdapter.ViewBinder() {

                @Override
                public boolean setViewValue(View view, Object data, String textRepresentation) {
                    if (view.getId() == R.id.tv_flag) {
                        ((NetworkImageView) view).setImageUrl(getHm().toString(), mImageLoader);
                        return true;
                    } else
                        return false;
                }
            };
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public HashMap<String, Object> getHm() {
        if(adapter!=null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                hm = (HashMap<String, Object>) adapter.getItem(i);
                String imgUrl = (String) hm.get("flag_path");

                HashMap<String, Object> hmDownload = new HashMap<String, Object>();
                hm.put("flag_path", imgUrl);
                hm.put("position", i);
            }
        }
        return hm;
    }

    public void setHm(HashMap<String, Object> hm) {
        this.hm = hm;
    }
}