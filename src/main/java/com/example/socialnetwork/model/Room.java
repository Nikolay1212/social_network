package com.example.socialnetwork.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY  )
    private Long id;
    private String title;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"))
    private List<Account> accounts;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "message_id", referencedColumnName = "id"))
    private List<Message> messageList;

    @Enumerated(value = EnumType.STRING)
    private RoomType roomType;

    private enum RoomType {
        DEFAULT,
        ADMIN,
        PRIVATE
    }
}
