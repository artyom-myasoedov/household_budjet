package ru.vsu.hb.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.vsu.hb.persistence.entity.User;
import ru.vsu.hb.persistence.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository applicationUserRepository;

    public UserDetailsServiceImpl(UserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.springframework.security.core.userdetails.User.UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(username);
        User applicationUser = applicationUserRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        builder.password(applicationUser.getPassword());
        builder.authorities(applicationUser.getAuthorities());
        return builder.build();
    }

}