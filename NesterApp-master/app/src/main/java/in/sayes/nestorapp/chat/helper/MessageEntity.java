package in.sayes.nestorapp.chat.helper;

import android.graphics.Bitmap;

import in.sayes.nestorapp.chat.helper.model.InputFromType;

/**
 * Created by sourav on 27/04/16.
 * Project : NesterApp , Package Name : in.sayes.nestorapp.chat.helper
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */
public class MessageEntity {
    private int msgID;
    private String msgText;
    private UserType userType;
    private MessageType messageType;
    private Status msgStatus;
    private long messageTime;
    private Bitmap msgImage;


    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public Status getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(Status msgStatus) {
        this.msgStatus = msgStatus;
    }

    public int getMsgID() {
       return msgID;
    }

    public void setMsgID(int msgID) {
        this.msgID = msgID;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Bitmap getMsgImage() {
        return msgImage;
    }

    public void setMsgImage(Bitmap msgImage) {
        this.msgImage = msgImage;
    }

}
