package com.Niccolo.splitwise.model;

import java.util.UUID;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class User {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private final String id;
    private final String name;
    private final String email;

    public User(String name, String email) {
        if(name == null || email == null){
            throw new IllegalArgumentException("Name and Email cannot be null");
        }
        if(name.isBlank() || email.isBlank()){
            throw new IllegalArgumentException("Name and Email cannot be blank strings");
        }

        if(!EMAIL_PATTERN.matcher(email).matches()){
            throw new IllegalArgumentException("Email must have a valid format [text + \"@\" + text \".\" + text");
        }
        this.email = email;
        this.name = name;

        id = UUID.randomUUID().toString();
    }

}
