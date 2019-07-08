package com.vpd.courseproject.forum.persistence.entity;

import com.vpd.courseproject.forum.utils.Formatter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Topic {

    @Id
    @GeneratedValue
    private long id;

    @Column(length = 40)
    private String name;

    @CreationTimestamp
    @Column(name = "date_of_creation")
    private Date dateOfCreation;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Section section;

    public Topic(String name, Section section) {
        this.name = name;
        this.section = section;
    }

    public Topic() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public String getDateOfCreation() {
        return Formatter.getInstance().formatDateWithTime(dateOfCreation);
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return id == topic.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
