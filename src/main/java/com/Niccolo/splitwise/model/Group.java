package com.Niccolo.splitwise.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Group {
    @EqualsAndHashCode.Include
    private final String id;

    private String name;
    private String description;
    private final List<User> members = new ArrayList<>();

    public Group(String name, String description, User creator) {
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if(creator == null){
            throw new IllegalArgumentException("Creator cannot be null");
        }

        this.name = name;
        this.description = (description != null) ? description : "";
        this.members.add(creator);

        id = UUID.randomUUID().toString();
    }

    public Group(String name, User creator){
        this(name, null, creator);
    }

    // --- CORE ---
    public void addMember(User member){
        if(member == null){
            throw new IllegalArgumentException("Member cannot be null");
        }
        this.members.add(member);
    }

    public void addMembers(List<User> members){
        if(members == null){
            throw new IllegalArgumentException("Members cannot be null");
        }
        for(User member : members){
            addMember(member);
        }
    }

    public void removeMember(User member){
        if(member == null){
            throw new IllegalArgumentException("Member cannot be null");
        }
        this.members.remove(member);
    }


    // --- SETTERS ---
    public void setName(String name) {
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public void setDescription(String description) {
        if(description == null){
            throw new IllegalArgumentException("Description cannot be null");
        }
        this.description = description;
    }
}