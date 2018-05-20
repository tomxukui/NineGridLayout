package com.ablingbling.app.ninegridlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ablingbling.library.ninegridlayout.CreateNineGridLayout;

/**
 * Created by xukui on 2018/5/20.
 */
public class CreateActivity extends AppCompatActivity {

    private int mIndex = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        CreateDraweeNineGridLayout grid_create = findViewById(R.id.grid_create);
        grid_create.setOnAddClickListener(new CreateNineGridLayout.OnAddClickListener() {

            @Override
            public void onAddClick(CreateNineGridLayout view, ImageView itemView) {
                int totalCount = DataHelper.images.length;

                mIndex++;
                if (mIndex >= totalCount) {
                    mIndex = 0;
                }

                view.addData(DataHelper.images[mIndex]);
            }

        });
        grid_create.setOnItemClickListener(new CreateNineGridLayout.OnItemClickListener() {

            @Override
            public void onItemClick(CreateNineGridLayout view, View itemView, int position, String imgUrl) {
                Toast.makeText(CreateActivity.this, "position: " + position, Toast.LENGTH_SHORT).show();
            }

        });
    }

}
