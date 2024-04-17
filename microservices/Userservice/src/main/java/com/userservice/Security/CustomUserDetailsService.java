package com.userservice.Security;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.userservice.Models.User;
import com.userservice.Repositories.UserRepo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Getter
@Setter
@JsonDeserialize(as = CustomUserDetailsService.class)
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepo.findByemail(username);
        if(userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User does not exist");
        }

        User user = userOptional.get();

        return new CustomUserDetail(user);
    }
}