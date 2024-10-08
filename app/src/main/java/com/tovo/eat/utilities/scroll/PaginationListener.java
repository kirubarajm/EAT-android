package com.tovo.eat.utilities.scroll;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import static kotlin.text.Typography.amp;

public abstract class PaginationListener extends RecyclerView.OnScrollListener {
 
  public static final int PAGE_START = 1;
 
  @NonNull
  private LinearLayoutManager layoutManager;
 
  /**
   * Set scrolling threshold here (for now i'm assuming 10 item in one page)
   */
  private static final int PAGE_SIZE = 6;
 
  /**
   * Supporting only LinearLayoutManager for now.
   */
  public PaginationListener(@NonNull LinearLayoutManager layoutManager) {
    this.layoutManager = layoutManager;
  }
 
  @Override
  public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);
 
    int visibleItemCount = layoutManager.getChildCount();
    int totalItemCount = layoutManager.getItemCount();
    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
 
    if (!isLoading() && !isLastPage()) {
      if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
         && firstVisibleItemPosition >= 0
        &&totalItemCount >= PAGE_SIZE) {
        loadMoreItems();
      }
    }
  }
 
  protected abstract void loadMoreItems();
 
  public abstract boolean isLastPage();
 
  public abstract boolean isLoading();
}