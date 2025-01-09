package com.example.DvisWebShop.services;

import com.example.DvisWebShop.models.User;
import com.example.DvisWebShop.repositories.UserRepository;
import com.example.DvisWebShop.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User Not Found with username: " + username)
                );

        return UserDetailsImpl.build(user);
    }
}
