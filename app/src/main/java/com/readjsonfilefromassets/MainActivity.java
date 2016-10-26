package com.readjsonfilefromassets;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<DataModel.GeneralNewsBean> dataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DataAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadJSONFromAsset();

        try {

            JSONObject obj = new JSONObject(loadJSONFromAsset());
            Gson gson = new Gson();
            DataModel dataModel = gson.fromJson(obj.toString(),DataModel.class);
            dataList = dataModel.getGeneral_news();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        if(dataList.size()>0) {
            mAdapter = new DataAdapter(dataList,MainActivity.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

            Snackbar.make(((RelativeLayout)findViewById(R.id.activity_main)),"Click news to see details.",Snackbar.LENGTH_SHORT).show();
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("news.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


}
