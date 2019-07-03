package com.tovo.eat.ui.signup.faqs;

import android.databinding.ObservableField;

public class FaqsItemViewModel {

    public final ObservableField<String> type = new ObservableField<>();

    public final ObservableField<String> question = new ObservableField<>();

    public final ObservableField<String> answer = new ObservableField<>();

    public final ObservableField<String> faqid = new ObservableField<>();

    public final ObservableField<String> created_at = new ObservableField<>();

    public final FaqItemViewModelListener mListener;

    private final FaqResponse.ProductList mBlog;


    public FaqsItemViewModel(FaqResponse.ProductList menuProducts, FaqItemViewModelListener listener) {

        this.type.set(menuProducts.getType());
        this.question.set(menuProducts.getQuestion());
        this.answer.set(menuProducts.getAnswer());
        this.faqid.set(menuProducts.getFaqid());
        this.created_at.set(menuProducts.getCreated_at());

        this.mListener = listener;
        this.mBlog = menuProducts;
    }

    public void onItemClick() {
        mListener.onItemClick(mBlog.getType());
    }


    public interface FaqItemViewModelListener {

        void onItemClick(String blogUrl);
    }

}
