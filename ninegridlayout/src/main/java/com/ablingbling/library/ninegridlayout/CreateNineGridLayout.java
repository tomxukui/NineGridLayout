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

public abstract class CreateNineGridLayout<T extends View> extends ViewGroup {

    private int mSpace;
    private int mMaxColumn;
    private int mItemW;
    private int mItemH;
    private int mRow;
    private int mOldNum;
    private List<String> mImgs;

    public CreateNineGridLayout(Context context) {
        super(context);
        initData(context, null, 0);
    }

    public CreateNineGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context, attrs, 0);
    }

    public CreateNineGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CreateNineGridLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initData(context, attrs, defStyleAttr);
    }

    private void initData(Context context, AttributeSet attrs, int defStyleAttr) {
        mSpace = DensityUtil.dp2px(10);
        mMaxColumn = 4;
        mOldNum = 0;

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CreateNineGridLayout, defStyleAttr, 0);

            mSpace = ta.getDimensionPixelSize(R.styleable.CreateNineGridLayout_cngl_space, mSpace);
            mMaxColumn = ta.getInteger(R.styleable.CreateNineGridLayout_cngl_maxColumn, mMaxColumn);

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
            mItemW = (getMeasuredWidth() - mSpace * (mMaxColumn - 1)) / mMaxColumn;
            mItemH = mItemW;

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
        int column = mMaxColumn;

        for (int i = 0; i < count; i++) {
            T view = (T) getChildAt(i);
            setItemView(view, mItemW, mItemH, mImgs.get(i));

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

    public void setData(List<String> list) {
        mImgs = (list == null ? new ArrayList<String>() : list);

        if (mImgs.size() == 0) {
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

    public abstract T createItemView();

    public abstract T setItemView(T view, int viewWidth, int viewHeight, String imgUrl);

}
