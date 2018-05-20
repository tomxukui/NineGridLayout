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
        grid_create.setOnCreateNineGridLayoutListener(new CreateNineGridLayout.OnCreateNineGridLayoutListener() {

            @Override
            public void onItemClickListener(CreateNineGridLayout view, View itemView, int position, String imgUrl) {
                Toast.makeText(CreateActivity.this, "position: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAddClickListener(CreateNineGridLayout view, ImageView addView) {
                int totalCount = DataHelper.images.length;

                mIndex++;
                if (mIndex >= totalCount) {
                    mIndex = 0;
                }

                view.addData(DataHelper.images[mIndex]);
            }

        });
    }

}
