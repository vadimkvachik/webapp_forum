package com.vpd.courseproject.forum.persistence.entity;

import com.vpd.courseproject.forum.utils.Formatter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Article {

    @Id
    @GeneratedValue
    private long id;

    @Column(length = 80)
    private String topic;

    @Lob
    @Column(length=20971520)
    private String text;

    @CreationTimestamp
    @Column(name = "date_of_creation")
    private Date dateOfCreation;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

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

    public String getDateOfCreation() {
        return Formatter.getInstance().formatDateWithTime(dateOfCreation);
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id == article.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
