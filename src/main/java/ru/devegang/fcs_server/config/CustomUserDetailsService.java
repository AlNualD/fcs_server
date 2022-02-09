package ru.devegang.fcs_server.config;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.devegang.fcs_server.entities.User;
import ru.devegang.fcs_server.services.UserService;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userService.getUser(username);
        if(!optionalUser.isPresent()) throw new UsernameNotFoundException("Not found");
        return CustomUserDetails.fromUserEntityToCustomUserDetails(optionalUser.get());
    }
}
