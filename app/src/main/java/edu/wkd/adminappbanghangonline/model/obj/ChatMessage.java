package edu.wkd.adminappbanghangonline.model.obj;

import java.util.Date;

public class ChatMessage {
    public String sendId;
    public String receivedid;
    public String mess;
    public String datatime;
    public String avatar;
    public Date dataObj;

    public ChatMessage() {

    }

    public ChatMessage(String sendId, String receivedid, String mess, String avatar, String datatime, Date dataObj) {
        this.sendId = sendId;
        this.receivedid = receivedid;
        this.mess = mess;
        this.avatar = avatar;
        this.datatime = datatime;
        this.dataObj = dataObj;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "sendId='" + sendId + '\'' +
                ", receivedid='" + receivedid + '\'' +
                ", mess='" + mess + '\'' +
                ", datatime='" + datatime + '\'' +
                ", dataObj=" + dataObj +
                '}';
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getReceivedid() {
        return receivedid;
    }

    public void setReceivedid(String receivedid) {
        this.receivedid = receivedid;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getDatatime() {
        return datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }

    public Date getDataObj() {
        return dataObj;
    }

    public void setDataObj(Date dataObj) {
        this.dataObj = dataObj;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
