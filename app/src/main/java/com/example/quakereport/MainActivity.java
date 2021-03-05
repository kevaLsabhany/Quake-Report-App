package com.example.quakereport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private  static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2021-01-01&endtime=2021-01-21";
    private ListView mQuakeListView;
    private TextView mEmptyListView;
    private EarthquakeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuakeListView = (ListView) findViewById(R.id.list);
        mEmptyListView = (TextView) findViewById(R.id.empty_view);
        mQuakeListView.setEmptyView(mEmptyListView);
        mAdapter = new EarthquakeAdapter(MainActivity.this, new ArrayList<Earthquake>());
        mQuakeListView.setAdapter(mAdapter);

        mQuakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                Earthquake e = (Earthquake) parent.getItemAtPosition(position);
                i.setData(Uri.parse(e.getmUrl()));
                startActivity(i);
            }
        });

        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);
    }

    private void updateUI(ArrayList<Earthquake> earthquakes) {
        View loadingView = findViewById(R.id.loading_view);
        loadingView.setVisibility(View.GONE);
        if(earthquakes.isEmpty()) {
            mEmptyListView.setText(R.string.no_data);
        } else {
            mAdapter.addAll(earthquakes);
        }
    }

    private class EarthquakeAsyncTask extends AsyncTask<String, Void, ArrayList<Earthquake>> {

        @Override
        protected ArrayList<Earthquake> doInBackground(String... urls) {
            return QueryUtils.extractDataFromApi(urls[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> earthquakes) {
            if(earthquakes.isEmpty())
                return;
            updateUI(earthquakes);
        }
    }
}