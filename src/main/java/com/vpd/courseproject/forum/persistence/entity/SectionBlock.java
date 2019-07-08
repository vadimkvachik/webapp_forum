package com.vpd.courseproject.forum.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity(name = "Section_block")
public class SectionBlock implements Comparable<SectionBlock> {

    @Id
    @GeneratedValue
    private long id;

    @Column(length = 40)
    private String name;

    public SectionBlock(String name) {
        this.name = name;
    }

    public SectionBlock() {}

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

    @Override
    public int compareTo(SectionBlock o) {
        return (int) (id - o.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionBlock that = (SectionBlock) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
