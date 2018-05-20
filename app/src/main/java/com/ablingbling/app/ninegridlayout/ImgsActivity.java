package com.ablingbling.app.ninegridlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xukui on 2018/5/20.
 */
public class ImgsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgs);
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<String> l = new ArrayList<>();

            for (int j = 0; j < i; j++) {
                l.add(DataHelper.images[j]);
            }

            list.add(l);
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerAdapter(this, list));
    }

}
