package com.example.springbootbackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "post_management_commentaries")
public class Commentary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String body;

    public Commentary(String email, String body) {
        this.email = email;
        this.body = body;
    }

    public Commentary() {
    }
}
