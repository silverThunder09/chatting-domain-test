package org.example.chatting.common.domain.controller;

import lombok.RequiredArgsConstructor;
import org.example.chatting.common.domain.repository.ChatRoomRepository;
import org.example.chatting.common.entity.ChatRoom;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat/rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    @PostMapping
    public ChatRoom create(@RequestParam String name) {
        ChatRoom room = new ChatRoom(name);
        return chatRoomRepository.save(room);
    }

    @GetMapping
    public List<ChatRoom> getAll() {
        return chatRoomRepository.findAll();
    }
}
