package org.example.chatting.common.domain.repository;

import org.example.chatting.common.entity.ChatMessage;
import org.example.chatting.common.entity.ChatRoom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("""
    select m
    from ChatMessage m
    join fetch m.sender
    where m.chatRoom.id = :roomId
    order by m.id desc
    """)
    List<ChatMessage> findRecentByRoom(@Param("roomId") Long roomId, Pageable pageable);
}
