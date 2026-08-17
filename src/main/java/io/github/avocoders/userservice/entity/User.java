package io.github.avocoders.userservice.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, unique = true, length = 255)
    private String email;
    @Column(nullable = false)
    private Integer age;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Long getId(){
        return id;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public Integer getAge(){
        return age;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setAge(Integer age){
        this.age = age;
    }

    public User(){}
    public User(String name, String email, Integer age){
        this.name = name;
        this.email = email;
        this.age = age;
    }
    @Override
    public String toString(){
        return "User{ id = " + this.id + ", name = " + this.name + ", email = "
                + this.email + ", age = " + this.age + ", createdAt = " + this.createdAt
                + "}";
    }
}
