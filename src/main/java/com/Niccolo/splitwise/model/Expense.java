package com.Niccolo.splitwise.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public class Expense {
    @EqualsAndHashCode.Include
    private final String id;

    private Money amount;
    private User payer;
    private final List<User> involvedUsers = new ArrayList<>();
    private LocalDate date;

    private String description;
    @Setter
    private Group group;

    public Expense(Money amount, User payer, List<User> involvedUsers, LocalDate date, String description, Group group) {
        if(amount == null || payer == null || involvedUsers == null || involvedUsers.isEmpty()) {
            throw new IllegalArgumentException("Amount, Payer and Involved Users are necessarily");
        }
        this.amount = amount;
        this.payer = payer;
        this.involvedUsers.addAll(involvedUsers);

        this.date = (date != null) ? date : LocalDate.now();
        this.description = (description != null) ? description : "";
        this.group = group;

        this.id = UUID.randomUUID().toString();
    }

    public Expense(Money amount, User payer, List<User> involvedUsers) {
        this(amount, payer, involvedUsers, null, null, null);
    }


    // --- CORE ---
    public void addInvolvedUser(User u){
        if(u == null){
            throw new IllegalArgumentException("User cannot be null");
        }

        this.involvedUsers.add(u);
    }

    public void addInvolvedUsers(List<User> users){
        if(users == null){
            throw new IllegalArgumentException("Users cannot be null");
        }

        for(User u : users){
            addInvolvedUser(u);
        }
    }

    public void removeInvolvedUser(User u){
        if(u == null){
            throw new IllegalArgumentException("User cannot be null");
        }
        if(this.involvedUsers.size() == 1){
            throw new IllegalStateException("You cannot remove every User in the list of Involved Users");
        }

        this.involvedUsers.remove(u);
    }


    // --- GROUP LOGIC ---
    public boolean hasGroup(){
        return this.group != null;
    }

    public Optional<Group> getGroup(){
        return Optional.ofNullable(this.group);
    }


    // --- SETTER ---
    public void setAmount(Money amount) {
        if(amount == null){
            throw new IllegalArgumentException("Amount cannot be null");
        }
        this.amount = amount;
    }

    public void setPayer(User payer) {
        if(payer == null){
            throw new IllegalArgumentException("Payer cannot be null");
        }
        this.payer = payer;
    }

    public void setDate(LocalDate date) {
        if(date == null){
            throw new IllegalArgumentException("Date cannot be null");
        }
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = (description != null) ? description : "";
    }
}