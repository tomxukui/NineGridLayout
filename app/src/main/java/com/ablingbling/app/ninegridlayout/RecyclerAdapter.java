package com.ablingbling.app.ninegridlayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ablingbling.library.ninegridlayout.NineGridLayout;

import java.util.List;

/**
 * Created by Clevo on 2016/8/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<List<String>> mList;

    public RecyclerAdapter(Context context, List<List<String>> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler, parent, false);
        return new RVMoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        RVMoreViewHolder vh = (RVMoreViewHolder) holder;

        vh.tv_name.setText("Row " + position);
        vh.grid_nine.setData(mList.get(position));
        vh.grid_nine.setOnItemClickListener(new NineGridLayout.OnItemClickListener() {

            @Override
            public void onItemClick(NineGridLayout view, View itemView, int position, String imgUrl) {
                Toast.makeText(mContext, "position: " + position, Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class RVMoreViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        DraweeNineGridLayout grid_nine;

        public RVMoreViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            grid_nine = itemView.findViewById(R.id.grid_nine);
        }

    }

}
