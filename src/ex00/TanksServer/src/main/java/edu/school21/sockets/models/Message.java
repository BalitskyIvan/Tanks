package edu.school21.sockets.models;

import java.sql.Timestamp;
import java.util.Objects;

public class Message {
    private Long id;
    private String senderName;
    private String text;
    private Timestamp timestamp;

    public Message(Long id, String senderName, String text, Timestamp timestamp) {
        this.id = id;
        this.senderName = senderName;
        this.text = text;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getSenderName() {
        return senderName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return getId().equals(message.getId()) && senderName.equals(message.senderName) && getText().equals(message.getText()) && getTimestamp().equals(message.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), senderName, getText(), getTimestamp());
    }
}
