package com.ablingbling.app.ninegridlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String[] images = new String[]{
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/portugal-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/usa-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/spain-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/chile-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/brazil-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/Japan-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/england-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/germany-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/argentina-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/korea-republic-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/italy-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/belgium-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/netherlands-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/france-flag-icon.png"};

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            List<String> l = new ArrayList<>();

            for (int j = 0; j < i; j++) {
                l.add(images[j]);
            }

            list.add(l);
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerAdapter(this, list));
    }

}
