package com.tovo.eat.ui.account.feedbackandsupport.support.replies;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RepliesItemViewModel {

    public final ObservableField<String> qid = new ObservableField<>();
    public final ObservableField<String> question = new ObservableField<>();
    public final ObservableField<String> type = new ObservableField<>();
    public final ObservableField<String> userid = new ObservableField<>();
    public final ObservableField<String> admin_read = new ObservableField<>();
    public final ObservableField<String> created_at = new ObservableField<>();
    public final ObservableField<String> updated_at = new ObservableField<>();
    public final ObservableField<String> createdAtFinal = new ObservableField<>();

    public final ObservableBoolean item_color=new ObservableBoolean();
    public final RepliesItemViewModelListener mListener;
    private final RepliesResponse.Result repliesList;

    public RepliesItemViewModel(RepliesResponse.Result replies, RepliesItemViewModelListener listener) {
        this.qid.set(String.valueOf(replies.getQid()));
        this.question.set(String.valueOf(replies.getQuestion())+"?");
        this.type.set(String.valueOf(replies.getType()));
        this.userid.set(String.valueOf(replies.getUserid()));
        this.admin_read.set(String.valueOf(replies.getAdminRead()));
        this.created_at.set(String.valueOf(replies.getCreatedAt()));
        this.updated_at.set(String.valueOf(replies.getUpdatedAt()));

        this.mListener = listener;
        this.repliesList = replies;

        try {
            String strDate = replies.getCreatedAt();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat currentFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
            String outputDateStr = "";
            //Date  date1 = new Date(strDate);
            Date date = currentFormat.parse(strDate);
            outputDateStr = dateFormat.format(date);
            createdAtFinal.set("Query made "+outputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void onItemClick() {
        mListener.onItemClick(repliesList);
    }

    public interface RepliesItemViewModelListener {

        void onItemClick(RepliesResponse.Result replies);
    }

}
