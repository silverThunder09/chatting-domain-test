package org.example.chatting.common.domain.controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.chatting.common.config.redis.ChatRedisPublisher;
import org.example.chatting.common.config.redis.RedisChatMessage;
import org.example.chatting.common.domain.model.ChatMessageDto;
import org.example.chatting.common.domain.model.TypingIndicatorDto;
import org.example.chatting.common.domain.repository.ChatMessageRepository;
import org.example.chatting.common.domain.repository.ChatRoomRepository;
import org.example.chatting.common.domain.repository.UserRepository;
import org.example.chatting.common.entity.ChatMessage;
import org.example.chatting.common.entity.ChatRoom;
import org.example.chatting.common.entity.User;
import org.example.chatting.common.interceptor.AuthenticatedUser;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRedisPublisher chatRedisPublisher;
    private final SimpMessagingTemplate messagingTemplate;

    // websocketconfig 메시지 보낼 때 url prefix . /pub
    // /pub/chat.send

    @MessageMapping("/chat.send")
    public void send(ChatMessageDto dto, Principal principal) {

        User sender = AuthenticatedUser.fromPrincipal(principal);

          ChatRoom room = chatRoomRepository.findById(dto.getRoomId()).orElseThrow();

        ChatMessage message = new ChatMessage(sender, room, dto.getContent());
        chatMessageRepository.save(message);

        // 받은 메시지를 채팅방에 발송 시켜줘야 한다
        // /sub/chat 이라는 채팅방 이름에다가
        // 외부에서 받아온 dto를 전달 시켜줄 예정이다
        RedisChatMessage redisMessage = new RedisChatMessage(
                message.getChatRoom().getId(),
                message.getSender().getId(),
                message.getSender().getName(),
                message.getContent()
        );

        chatRedisPublisher.publish(room.getId(), redisMessage);

    }

    @MessageMapping("/chat.typing")
    public void typing(TypingIndicatorDto dto, Principal principal) {

        User user = AuthenticatedUser.fromPrincipal(principal);

        dto.setUserId(user.getId());
        dto.setUserName(user.getName());

        messagingTemplate.convertAndSend("/sub/chat/" + dto.getRoomId() + "/typing", dto);

        // 사용자가 타이핑 중이라면 이를 프론트에서 감지해서 서버에게 알려주는것
        // 서버는 해당 내용을 채팅방에 참여하고 있는 다른 사람에게 알려주면 된다.
    }


}
