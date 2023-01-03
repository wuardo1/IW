package com.iw.application.data.entity.account;


import com.iw.application.data.entity.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "user_table")
public class User extends AbstractEntity {
    @NotNull
    public String mail; // serves as username

    @NotNull
    public String password;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate dateOfBirth;
    private String occupation;
    private boolean important;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }
}