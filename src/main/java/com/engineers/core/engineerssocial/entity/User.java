package com.engineers.core.engineerssocial.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String firstName;
    private String lastName;
    private String typeOfEngineer;

    @OneToMany(targetEntity = Post.class)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private List<Post> savedPost = new ArrayList<>();

    @ElementCollection
    private List<Integer> followers = new ArrayList<>();

    @ElementCollection
    private List<Integer> following = new ArrayList<>();

    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
}
