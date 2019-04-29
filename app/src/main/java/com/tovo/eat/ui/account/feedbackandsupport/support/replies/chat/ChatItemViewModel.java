package com.tovo.eat.ui.account.feedbackandsupport.support.replies.chat;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ChatItemViewModel {

    public final ObservableField<String> aid = new ObservableField<>();
    public final ObservableField<String> qid = new ObservableField<>();
    public final ObservableField<String> answer = new ObservableField<>();
    public final ObservableField<String> type = new ObservableField<>();
    public final ObservableField<String> adminid = new ObservableField<>();
    public final ObservableField<String> userid = new ObservableField<>();
    public final ObservableField<String> user_read = new ObservableField<>();
    public final ObservableField<String> admin_read = new ObservableField<>();
    public final ObservableField<String> created_at = new ObservableField<>();
    public final ObservableField<String> updated_at = new ObservableField<>();
    public final ObservableField<String> typeString = new ObservableField<>();
    public final ObservableField<String> createdAtFinal = new ObservableField<>();

    public final ObservableBoolean flagAdminOrUser = new ObservableBoolean();

    public final OrdersItemViewModelListener mListener;
    private final ChatResponse.Result mOrderList;

    public ChatItemViewModel(ChatResponse.Result mBlog, OrdersItemViewModelListener listener) {
        aid.set(String.valueOf(mBlog.getAid()));
        qid.set(String.valueOf(mBlog.getQid()));
        answer.set(String.valueOf(mBlog.getAnswer()));
        type.set(String.valueOf(mBlog.getType()));
        adminid.set(String.valueOf(mBlog.getAdminid()));
        userid.set(String.valueOf(mBlog.getUserid()));
        user_read.set(String.valueOf(mBlog.getUserRead()));
        admin_read.set(String.valueOf(mBlog.getAdminRead()));
        created_at.set(String.valueOf(mBlog.getCreatedAt()));
        updated_at.set(String.valueOf(mBlog.getUpdatedAt()));

        this.mListener = listener;
        this.mOrderList = mBlog;

        if (mBlog.getType() == 0) {
            typeString.set(String.valueOf(mBlog.getType()));
            flagAdminOrUser.set(true);
        } else {
            flagAdminOrUser.set(false);
            typeString.set(String.valueOf(mBlog.getType()));
        }


        try {
            String strDate = mBlog.getCreatedAt();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            DateFormat currentFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String outputDateStr = "";
            //Date  date1 = new Date(strDate);
            Date date = currentFormat.parse(strDate);
            outputDateStr = dateFormat.format(date);
            createdAtFinal.set(outputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }catch (Exception e)
        {
            e.printStackTrace();
        }



    }

    public void onItemClick() {
        mListener.onItemClick(mOrderList);
    }

    public interface OrdersItemViewModelListener {

        void onItemClick(ChatResponse.Result orders);
    }

}
