package com.example.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

public class Credentials {
    private Long id;
    private String email;
    private String password;
    private Set<Role> roles = new HashSet<>();
    private Customer customer;

}
