package com.vpd.courseproject.forum.persistence.entity;

import com.vpd.courseproject.forum.utils.Formatter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Message implements Comparable<Message> {

    @Id
    @GeneratedValue
    private long id;

    @Lob
    @Column(length=20971520)
    private String text;

    @CreationTimestamp
    @Column(name = "date_of_publication")
    private Date dateOfPublication;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Topic topic;

    public Message(User user, Topic topic, String text) {
        this.text = text;
        this.user = user;
        this.topic = topic;
    }

    public Message(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDateOfPublication() {
        return Formatter.getInstance().formatDateWithTime(dateOfPublication);
    }

    public void setDateOfPublication(Date dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getText() {
       return text.replace("\n", "<br>");
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public int compareTo(Message o) {
        return (int)(id - o.id);
    }
}
