package in.sayes.nestorapp.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import in.sayes.nestorapp.R;
import in.sayes.nestorapp.chat.helper.MessageEntity;
import in.sayes.nestorapp.chat.helper.UserType;

/**
 * Created by sourav on 27/04/16.
 * Project : NesterApp , Package Name : in.sayes.nestorapp.chat.adapter
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */


public class ChatListAdapter extends BaseAdapter {

    private ArrayList<MessageEntity> chatMessages;
    private Context context;
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("HH:mm");

    public ChatListAdapter(ArrayList<MessageEntity> chatMessages, Context context) {
        this.chatMessages = chatMessages;
        this.context = context;

    }


    @Override
    public int getCount() {
        return chatMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return chatMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;
        MessageEntity message = chatMessages.get(position);

        ViewHolder2 holder2;

        if (message.getUserType() == UserType.SELF) {
            if (convertView == null) {

                switch (message.getMessageType()) {
                    case TEXT:
                        v = showOnlyTextMessage(v,convertView,message);
                        break;
                    case COMBO:
                        v = showComboMessage(v, convertView, message);
                        break;
                    case IMAGE:
                        v = showImageMessage(v, convertView, message);
                        break;
                }

            }


        } else if (message.getUserType() == UserType.NESTOR) {

            if (convertView == null) {
                v = LayoutInflater.from(context).inflate(R.layout.chat_row_nestor, null, false);

                holder2 = new ViewHolder2();


                holder2.messageTextView = (TextView) v.findViewById(R.id.message_text);
                holder2.timeTextView = (TextView) v.findViewById(R.id.time_text);
                holder2.messageStatus = (ImageView) v.findViewById(R.id.user_reply_status);
                v.setTag(holder2);

            } else {
                v = convertView;
                holder2 = (ViewHolder2) v.getTag();

            }
             holder2.messageTextView.setText(message.getMsgText());
             holder2.timeTextView.setText(SIMPLE_DATE_FORMAT.format(message.getMessageTime()));

        }


        return v;
    }

    private View showImageMessage(View v, View convertView, MessageEntity message) {
        ViewHolderImage holder;
        if (convertView == null) {
            v = LayoutInflater.from(context).inflate(R.layout.chat_row_user_image, null, false);
            holder = new ViewHolderImage();


            holder.messageTextView = (TextView) v.findViewById(R.id.message_text);
            holder.imageView = (ImageView) v.findViewById(R.id.message_image);

            v.setTag(holder);
        } else {
            v = convertView;
            holder = (ViewHolderImage) v.getTag();
        }

        holder.messageTextView.setText(message.getMsgText());
        holder.imageView.setImageBitmap(message.getMsgImage());

        return v;
    }

    private View showComboMessage(View v, View convertView, MessageEntity message) {

        ViewHolderCombo holder;
        if (convertView == null) {
            v = LayoutInflater.from(context).inflate(R.layout.chat_row_user_combo, null, false);
            holder = new ViewHolderCombo();


            holder.messageTextView = (TextView) v.findViewById(R.id.message_text);
            holder.timeTextView = (TextView) v.findViewById(R.id.time_text);

            v.setTag(holder);
        } else {
            v = convertView;
            holder = (ViewHolderCombo) v.getTag();
        }

        holder.messageTextView.setText(message.getMsgText());
        holder.timeTextView.setText(SIMPLE_DATE_FORMAT.format(message.getMessageTime()));


        return v;
    }

    private View showOnlyTextMessage(View v, View convertView, MessageEntity message) {
        ViewHolderText holder1;
        if (convertView == null) {
        v = LayoutInflater.from(context).inflate(R.layout.chat_row_user_text, null, false);
        holder1 = new ViewHolderText();


        holder1.messageTextView = (TextView) v.findViewById(R.id.message_text);
        holder1.timeTextView = (TextView) v.findViewById(R.id.time_text);

        v.setTag(holder1);
    } else {
        v = convertView;
        holder1 = (ViewHolderText) v.getTag();
    }

    holder1.messageTextView.setText(message.getMsgText());
    holder1.timeTextView.setText(SIMPLE_DATE_FORMAT.format(message.getMessageTime()));

return v;
}

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        MessageEntity message = chatMessages.get(position);
        return message.getUserType().ordinal();
    }

    private class ViewHolderText {
        public TextView messageTextView;
        public TextView timeTextView;


    }

    private class ViewHolder2 {
        public ImageView messageStatus;
        public TextView messageTextView;
        public TextView timeTextView;

    }
    private class ViewHolderCombo {
        public TextView messageTextView;
        public TextView timeTextView;


    }

    private class ViewHolderImage {
        public TextView messageTextView;
        public ImageView imageView;


    }




}
