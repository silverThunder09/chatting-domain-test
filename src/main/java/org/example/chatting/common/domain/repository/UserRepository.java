package org.example.chatting.common.domain.repository;

import org.example.chatting.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
