package com.vpd.courseproject.forum.persistence.entity;

import com.vpd.courseproject.forum.utils.Formatter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name = "Author")
public class User implements Comparable<User> {

    @Id
    @Column(length = 18)
    private String login;

    @Column(length = 20)
    private String password;

    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 20)
    private String name;

    @Column(length = 20)
    private String phone;

    @Lob
    @Column(length=20971520)
    private String description;

    @Column
    private boolean deleted;

    @Column(length = 100, name = "reason_for_blocking")
    private String reasonForBlocking;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_registration")
    private Date dateOfRegistration;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description.replace("\n", "<br>");
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getDateOfRegistration() {
        return Formatter.getInstance().formatDateWithoutTime(dateOfRegistration);
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public String getReasonForBlocking() {
        return reasonForBlocking;
    }

    public void setReasonForBlocking(String reasonForBlocking) {
        this.reasonForBlocking = reasonForBlocking;
    }

    @Override
    public int compareTo(User o) {
        return login.toLowerCase().compareTo(o.login.toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    public enum Role {
        ADMIN, MODER, USER
    }
}
