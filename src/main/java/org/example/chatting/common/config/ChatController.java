package org.example.chatting.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat.send")
    public void send(
            ChatMessage message,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        String sessionId = headerAccessor.getSessionId();

        System.out.println("서버 수신 메시지 ");
        System.out.println("sessionId = " + sessionId);
        System.out.println("content = " + message.getContent());

        simpMessagingTemplate.convertAndSend("/sub/chat", message);



    }


}
