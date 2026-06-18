package org.example.chatting.common.interceptor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.chatting.common.entity.User;
import java.security.Principal;

@Getter
public class AuthenticatedUser implements Principal {

    private final User user;
    private final String name;


    public AuthenticatedUser(User user) {
        this.user = user;
        this.name = user.getName();
    }

    public static User fromPrincipal(Principal principal) {
        return ((AuthenticatedUser) principal).getUser();
    }
}
