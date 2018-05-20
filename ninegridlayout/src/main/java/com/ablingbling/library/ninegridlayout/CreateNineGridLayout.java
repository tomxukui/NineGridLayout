package com.ablingbling.library.ninegridlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public abstract class CreateNineGridLayout<T extends View> extends ViewGroup {

    private static final String TAG_ADD = "TAG_ADD";

    private Context mContext;

    private int mSpace;
    private int mMaxColumn;
    private int mMaxCount;
    private int mAddResId;
    private int mItemW;
    private int mItemH;
    private int mRow;
    private List<String> mImgs;

    public CreateNineGridLayout(Context context) {
        super(context);
        initData(context, null, 0);
        initView();
    }

    public CreateNineGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context, attrs, 0);
        initView();
    }

    public CreateNineGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs, defStyleAttr);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CreateNineGridLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initData(context, attrs, defStyleAttr);
        initView();
    }

    private void initData(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        mSpace = DensityUtil.dp2px(10);
        mMaxColumn = 4;
        mAddResId = R.drawable.ic_add;
        mMaxCount = 9;
        mImgs = new ArrayList<>();

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CreateNineGridLayout, defStyleAttr, 0);

            mSpace = ta.getDimensionPixelSize(R.styleable.CreateNineGridLayout_cngl_space, mSpace);
            mMaxColumn = ta.getInteger(R.styleable.CreateNineGridLayout_cngl_maxColumn, mMaxColumn);
            mAddResId = ta.getResourceId(R.styleable.CreateNineGridLayout_cngl_add, mAddResId);
            mMaxCount = ta.getInteger(R.styleable.CreateNineGridLayout_cngl_maxCount, mMaxCount);

            ta.recycle();
        }
    }

    private void initView() {
        notifyDataSetChanged();
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
            View view = getChildAt(i);

            if (TAG_ADD.equals(view.getTag()) && view instanceof ImageView) {
                ((ImageView) view).setImageResource(mAddResId);

            } else {
                setItemView(((T) view), mItemW, mItemH, mImgs.get(i));
            }

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

    public void setNewData(List<String> list) {
        mImgs.clear();

        if (list != null) {
            mImgs.addAll(list);
        }

        notifyDataSetChanged();
    }

    public void addData(List<String> list) {
        if (list != null) {
            mImgs.addAll(list);
        }

        notifyDataSetChanged();
    }

    private void notifyDataSetChanged() {
        removeAllViews();

        for (int i = 0; i < mImgs.size(); i++) {
            addView(createItemView());
        }
        if (mImgs.size() < mMaxCount) {
            ImageView iv = new ImageView(mContext);
            iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            iv.setTag(TAG_ADD);
            addView(iv);
        }
    }

    public abstract T createItemView();

    public abstract T setItemView(T view, int viewWidth, int viewHeight, String imgUrl);

}
