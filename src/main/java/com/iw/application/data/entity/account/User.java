package com.iw.application.data.entity.account;


import com.iw.application.data.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "user_table")
public class User extends AbstractEntity {

    public String mail; // serves as username
    public String password;

    @ElementCollection(targetClass=UserGroup.class, fetch=FetchType.EAGER) // redo
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="user_user_group")
    @Column(name="user_group") // Column name in user_user_group
    public Collection<UserGroup> userGroups;

    public User() {}

    public User(String mail, String password, Collection<UserGroup> userGroups) {
        this.mail = mail;
        this.password = password;
        this.userGroups = userGroups;

    }

    public Collection<UserGroup> getUserGroups() {
        return userGroups;
    }

    public List<String> getUserGroupsAsString() {
        return userGroups.stream().map(Enum::toString).collect(Collectors.toList());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}