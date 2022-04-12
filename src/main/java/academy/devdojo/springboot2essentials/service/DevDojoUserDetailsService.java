package academy.devdojo.springboot2essentials.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import academy.devdojo.springboot2essentials.repository.DevDojoUserRepository;

@Service
public class DevDojoUserDetailsService implements UserDetailsService {

    private final DevDojoUserRepository devDojoUserRepository;

    public DevDojoUserDetailsService(DevDojoUserRepository devDojoUserRepository) {
        this.devDojoUserRepository = devDojoUserRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        return Optional.ofNullable(devDojoUserRepository.findByUsername(username))
        .orElseThrow(() -> new UsernameNotFoundException("DevDojo User not found"));
    }
    
}
