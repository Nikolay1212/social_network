package com.example.socialnetwork.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String hashPassword;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<Message> messageList;

    @ManyToMany
    private List<Room> rooms;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    private State state;

    public enum Role {
        ADMIN,
        MODERATOR,
        USER
    }

    public enum State {
        CONFIRMED,
        NOT_CONFIRMED,
        BANNED
    }
}
