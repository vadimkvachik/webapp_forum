package com.vpd.courseproject.forum.persistence.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Section implements Comparable<Section>{

    @Id
    @GeneratedValue
    private long id;

    @Column(length = 40)
    private String name;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SectionBlock sectionBlock;

    public Section(String name, SectionBlock sectionBlock) {
        this.name = name;
        this.sectionBlock = sectionBlock;
    }

    public Section() {}

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

    public SectionBlock getSectionBlock() {
        return sectionBlock;
    }

    public void setSectionBlock(SectionBlock sectionBlock) {
        this.sectionBlock = sectionBlock;
    }

    @Override
    public int compareTo(Section o) {
        return name.compareTo(o.name);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return id == section.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
