package com.vpd.courseproject.forum.persistence.entity;

import com.vpd.courseproject.forum.utils.Formatter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name = "Private_message")
public class PrivateMessage implements Comparable<PrivateMessage> {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String topic;

    @Lob
    @Column(length=20971520)
    private String text;

    @CreationTimestamp
    @Column(name = "date_of_sending")
    private Date dateOfSending;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User from;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User to;

    @Column
    private boolean read;

    @Column(name = "deleted_to")
    private boolean deletedTo;

    @Column(name = "deleted_from")
    private boolean deletedFrom;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getText() {
        return text.replace("\n", "<br>");
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDateOfSending() {
        return Formatter.getInstance().formatDateWithTime(dateOfSending);
    }

    public void setDateOfSending(Date dateOfSending) {
        this.dateOfSending = dateOfSending;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isDeletedTo() {
        return deletedTo;
    }

    public void setDeletedTo(boolean deletedTo) {
        this.deletedTo = deletedTo;
    }

    public boolean isDeletedFrom() {
        return deletedFrom;
    }

    public void setDeletedFrom(boolean deletedFrom) {
        this.deletedFrom = deletedFrom;
    }

    @Override
    public int compareTo(PrivateMessage o) {
        return o.dateOfSending.compareTo(dateOfSending);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivateMessage message = (PrivateMessage) o;
        return id == message.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
