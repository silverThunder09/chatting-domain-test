package org.example.chatting.common.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.chatting.common.domain.model.ChatMessageResponse;
import org.example.chatting.common.domain.repository.ChatMessageRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatQueryService {

    private final ChatMessageRepository repository;

    public List<ChatMessageResponse> getRecentMessages(int size) {

        Pageable pageable = PageRequest.of(0, size);

        return repository.findRecentMessages(pageable)
                .stream()
                .map(ChatMessageResponse::new)
                .toList();
    }

    public List<ChatMessageResponse> getMessagesBefore(Long lastMessageId, int size) {
        Pageable pageable = PageRequest.of(0, size);

        return repository.findMessagesBefore(lastMessageId, pageable)
                .stream()
                .map(ChatMessageResponse::new)
                .toList();
    }
}
