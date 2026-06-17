package org.example.chatting.common.domain.repository;

import org.example.chatting.common.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {



}



