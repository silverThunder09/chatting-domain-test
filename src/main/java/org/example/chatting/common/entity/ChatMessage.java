package org.example.chatting.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    private String content;

    private LocalDateTime createdAt;

    public ChatMessage(User sender, ChatRoom chatRoom, String content) {
        this.sender = sender;
        this.content = content;
        this.chatRoom = chatRoom;
        this.createdAt = LocalDateTime.now();
    }

}
