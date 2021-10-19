package Admin.datacollectionClass;

public class MessegesData {
    String massages;
    String senderId;
    long timeStamp;

    public MessegesData(String massages, String senderId, long timeStamp) {
        this.massages = massages;
        this.senderId = senderId;
        this.timeStamp = timeStamp;
    }

    public MessegesData() {
    }

    public String getMassages() {
        return massages;
    }

    public void setMassages(String massages) {
        this.massages = massages;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
