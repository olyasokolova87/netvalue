package nz.netvalue.domain.service.user.impl;

import lombok.RequiredArgsConstructor;
import nz.netvalue.domain.model.UserDetailsImpl;
import nz.netvalue.persistence.model.User;
import nz.netvalue.persistence.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findByUsername(username);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException(format("User with [%s] not found", username));
        }
        User user = optional.get();

        return new UserDetailsImpl(user);
    }
}
