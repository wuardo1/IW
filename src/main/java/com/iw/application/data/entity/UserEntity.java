package com.iw.application.data.entity;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    @Type(type = "uuid-char")
    private UUID userId;

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

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<BankAccountEntity> bankAccountEntities;

    @ElementCollection(targetClass= UserGroupEntity.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="user_groups", joinColumns = @JoinColumn(name = "user_id")) // Table name
    @Column(name="user_group") // Column name in user_user_group
    public Collection<UserGroupEntity> userGroupEntities;

    public UserEntity() {}

    public UserEntity(String mail, String password, Collection<UserGroupEntity> userGroupEntities) {
        this.mail = mail;
        this.password = password;
        this.userGroupEntities = userGroupEntities;
    }

    public Collection<UserGroupEntity> getUserGroups() {
        return userGroupEntities;
    }

    public List<String> getUserGroupsAsString() {
        return userGroupEntities.stream().map(Enum::toString).collect(Collectors.toList());
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

    public void addBankAccount(BankAccountEntity bankAccountEntity) {
        this.bankAccountEntities.add(bankAccountEntity);
    }

    public void removeBankAccount(BankAccountEntity bankAccountEntity) {
        this.bankAccountEntities.remove(bankAccountEntity);

    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Set<BankAccountEntity> getBankAccounts() {
        return bankAccountEntities;
    }

    public void setBankAccounts(Set<BankAccountEntity> bankAccountEntities) {
        this.bankAccountEntities = bankAccountEntities;
    }
}