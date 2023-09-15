package com.gym.service;

import com.gym.entities.User;
import com.gym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // load user from database
        User user =this.userRepository.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("no user found");
        }
            return user;
    }
}
