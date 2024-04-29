package com.akash.dailyshoplist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import com.akash.dailyshoplist.Adapters.TitleAdapter;
import com.akash.dailyshoplist.Database.DataBaseHandler;
import com.akash.dailyshoplist.Model.TitleListModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TitleAdapter adapter;
    private ArrayList<TitleListModel> titleListModels;
    private DataBaseHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView_title);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        handler = new DataBaseHandler(this);
        titleListModels = handler.getAllTitles();
        if(titleListModels == null)
        {
            Log.d("onCreate" , "titleList is null");
        }
        adapter = new TitleAdapter(titleListModels, MainActivity.this);
        recyclerView.setAdapter(adapter);


    }

    public void OpenView(View view) {
        startActivity(new Intent(MainActivity.this , ItemActivity.class
        ));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        titleListModels.clear();
        Log.d("Size" , "titleList Size before init : " + titleListModels.size());
        titleListModels = handler.getAllTitles();
        adapter.setTitles(titleListModels);
        Log.d("Size" , "titleList : " + titleListModels.size());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.close();
    }
}
