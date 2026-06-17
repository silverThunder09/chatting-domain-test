package org.example.chatting;


import org.example.chatting.common.domain.repository.UserRepository;
import org.example.chatting.common.entity.User;
import org.springframework.stereotype.Component;

@Component
public class TestUserInitializer {

    public TestUserInitializer(UserRepository userRepository) {
        userRepository.save(new User("홍길동"));
        userRepository.save(new User("이몽룡"));
    }
}
