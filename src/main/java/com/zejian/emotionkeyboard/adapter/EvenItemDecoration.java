package com.zejian.emotionkeyboard.adapter;

import android.graphics.Rect;
import android.view.View;

import com.zejian.emotionkeyboard.utils.DisplayUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EvenItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int column;

    public EvenItemDecoration(int space, int column) {
        this.space = space;
        this.column = column;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
//        int pos = parent.getChildAdapterPosition(view);
//        //每个spanspace 分割大小
//        int spanSpace = space * (column + 1) / column;
//        //列索引
//        int colIndex = pos % column;
//        outRect.left = space * (colIndex + 1) - spanSpace * colIndex;
////        outRect.right = spanSpace * (colIndex + 1) - space * (colIndex + 1);
//        // 行间距
//        outRect.top = space;

        if (parent.getChildAdapterPosition(view) %4 == 0) {
            outRect.left = 0; //第一列左边贴边
        } else {
            if (parent.getChildAdapterPosition(view) %4 == 1) {
                outRect.left = space;//第二列移动一个位移间距
            } else {
                outRect.left = space * 2;//由于第二列已经移动了一个间距，所以第三列要移动两个位移间距就能右边贴边，且item间距相等
            }
        }

        if (parent.getChildAdapterPosition(view) >= 0) {
//            outRect.top = DisplayUtils.dp2px(parent.getContext(), space);
            outRect.top = space*3;
        } else {
            outRect.top = 0;
        }
    }
}