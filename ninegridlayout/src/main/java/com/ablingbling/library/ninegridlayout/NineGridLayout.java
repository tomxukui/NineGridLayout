package com.ablingbling.library.ninegridlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class NineGridLayout<T extends View> extends ViewGroup {

    private int mSpace;
    private int mMaxColumn;
    private int mItemW;
    private int mItemH;
    private int mRow;
    private int mOldNum;
    private List mList;

    private OnItemClickListener mOnItemClickListener;

    public NineGridLayout(Context context) {
        super(context);
        initData(context, null, 0);
    }

    public NineGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context, attrs, 0);
    }

    public NineGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NineGridLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initData(context, attrs, defStyleAttr);
    }

    private void initData(Context context, AttributeSet attrs, int defStyleAttr) {
        mSpace = DensityUtil.dp2px(10);
        mMaxColumn = 3;
        mOldNum = 0;

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NineGridLayout, defStyleAttr, 0);

            mSpace = ta.getDimensionPixelSize(R.styleable.NineGridLayout_ngl_space, mSpace);
            mMaxColumn = ta.getInteger(R.styleable.NineGridLayout_ngl_maxColumn, mMaxColumn);

            ta.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int count = getChildCount();
        if (count == 0) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY);

        } else {
            mRow = count / mMaxColumn + (count % mMaxColumn == 0 ? 0 : 1);
            int maxColumn = (count == 4 ? 2 : Math.min(count, mMaxColumn));
            mItemW = (getMeasuredWidth() - mSpace * (maxColumn - 1)) / maxColumn;
            if (mList.size() == 1) {
                int w = getItemWidth(mList.get(0));
                int h = getItemHeight(mList.get(0));

                if (w > 0 && h > 0) {
                    mItemH = mItemW * h / w;

                } else {
                    mItemH = mItemW;
                }

            } else {
                mItemH = mItemW;
            }

            widthMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mItemH * mRow + mSpace * (mRow - 1), MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int left = 0;
        int top = 0;
        int column = (count == 4 ? 2 : Math.min(count, mMaxColumn));

        for (int i = 0; i < count; i++) {
            final T view = (T) getChildAt(i);
            final int position = i;
            final String imgUrl = getUrl(mList.get(i));
            setItemView(view, mItemW, mItemH, imgUrl);
            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(NineGridLayout.this, view, position, imgUrl);
                    }
                }

            });

            if (i % column > 0) {
                left += mSpace;

            } else {
                left = 0;

                if ((i / column) % mRow > 0) {
                    top += mSpace + mItemH;
                }
            }

            view.layout(left, top, left + mItemW, top + mItemH);
            left += mItemW;
        }
    }

    public void setData(List list) {
        mList = (list == null ? new ArrayList<>() : list);

        if (mList.size() == 0) {
            removeAllViews();
            mOldNum = 0;

        } else {
            if (mOldNum == 0) {
                for (int i = 0; i < list.size(); i++) {
                    addView(createItemView());
                }

            } else {
                if (mOldNum > list.size()) {//新创建的比之前的要少，则减去多余的部分
                    removeViews(list.size() - 1, mOldNum - list.size());

                } else if (mOldNum < list.size()) {//新创建的比之前的要少，则添加缺少的部分
                    for (int i = 0; i < list.size() - mOldNum; i++) {
                        addView(createItemView());
                    }
                }
            }

            mOldNum = list.size();
        }
    }

    protected abstract int getItemWidth(Object o);

    protected abstract int getItemHeight(Object o);

    protected abstract String getUrl(Object o);

    protected abstract T createItemView();

    protected abstract T setItemView(T view, int viewWidth, int viewHeight, String imgUrl);

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {

        void onItemClick(NineGridLayout view, View itemView, int position, String imgUrl);

    }

}
