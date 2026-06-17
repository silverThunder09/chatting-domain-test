package org.example.chatting.common.domain.controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.chatting.common.domain.model.ChatMessageDto;
import org.example.chatting.common.domain.repository.ChatMessageRepository;
import org.example.chatting.common.domain.repository.ChatRoomRepository;
import org.example.chatting.common.domain.repository.UserRepository;
import org.example.chatting.common.entity.ChatMessage;
import org.example.chatting.common.entity.ChatRoom;
import org.example.chatting.common.entity.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    // websocketconfig 메시지 보낼 때 url prefix . /pub
    // /pub/chat.send

    @MessageMapping("/chat.send")
    public void send(ChatMessageDto dto) {

        User sender = userRepository
                .findById(dto.getSenderId())
                .orElseThrow();

        ChatRoom room = chatRoomRepository
                .findById(dto.getRoomId())
                .orElseThrow();

        ChatMessage message = new ChatMessage(sender,room, dto.getContent());
        chatMessageRepository.save(message);

        // 받은 메시지를 채팅방에 발송 시켜줘야 한다
        // /sub/chat 이라는 채팅방 이름에다가
        // 외부에서 받아온 dto를 전달 시켜줄 예정이다
        messagingTemplate.convertAndSend("/sub/chat"+dto.getRoomId() ,dto);
    }
}
