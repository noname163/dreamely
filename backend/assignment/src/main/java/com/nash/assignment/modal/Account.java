package com.nash.assignment.modal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nash.assignment.constant.AccountStatus;
import com.nash.assignment.constant.UserRole;

@Entity
@Table(indexes = @Index(columnList = "email, phone"))
public class Account {

    @Id
    @SequenceGenerator(name = "account_sequence", sequenceName = "account_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "account_sequence")

    private long id;
    @Column(name="phone" ,nullable = true, unique = true, length = 13)
    private String phoneNumber;
    @Column( name = "email" ,unique = true)
    private String email;
    @Column(nullable = true, unique = false, length = 100)
    private String fullName;
    @Column(nullable = true, unique = false, length = 100)
    private String username;
    @Column(nullable = true, unique = false, length = 100)
    private String password;
    @Column(nullable = true, unique = false, length = 200)
    private String description;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = CascadeType.REFRESH)
    private Set<Order> order = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = CascadeType.REFRESH)
    private Set<RateProduct> rate = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = CascadeType.REFRESH)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

    public Account() {
    }
    

    public Account(Account account) {
        this.phoneNumber = account.phoneNumber;
        this.fullName = account.fullName;
        this.password = account.password;
        this.role = account.role;
        this.status = account.status;
    }


    public Account(String phoneNumber, String fullName, String userName, String password, UserRole role,
            AccountStatus status, String email) {
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.username = userName;
        this.password = password;
        this.role = role;
        this.email = email;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Set<Order> getOrder() {
        return order;
    }

    public void setOrder(Set<Order> order) {
        this.order = order;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Account [fullName=" + fullName + ", id=" + id + ", password=" + password + ", phoneNumber="
                + phoneNumber + ", role=" + role + ", userName=" + username + "]";
    }

}
