package xyz.aetherapps1.a8;

/**
 * Created by James Casia on 20/12/2017.
 */

public class Message {
    String message;
    String sender;
    String receiver;
    double time;

    public Message(String message, String sender, String receiver, double time)
    {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
    }
    public Message(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
