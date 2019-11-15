package com.tovo.eat.utilities.scroll;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class MyScroll extends RecyclerView.OnScrollListener {



    public static String TAG = MyScroll.class.getSimpleName();

    // use your LayoutManager instead
    private LinearLayoutManager llm;

    public MyScroll(LinearLayoutManager llm) {
        this.llm = llm;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (!recyclerView.canScrollVertically(1)) {
            onScrolledToEnd();
        }
    }

    public abstract void onScrolledToEnd();

}
